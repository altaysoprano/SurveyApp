package com.example.surveyapp.data.repository

import android.util.Log
import com.example.surveyapp.common.Constants.TITLE
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.models.Survey
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseRepository(
    private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider,
    private val surveysRef: CollectionReference
) {

    fun getSurveysFromFirestore() = callbackFlow {
        val snapshotListener = surveysRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val surveys = snapshot.toObjects(Survey::class.java)
                Response.Success(surveys)
            } else {
                Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    suspend fun addSurveyToFirestore(title: String, description: String) = flow {
        try {
            emit(Response.Loading)
            val id = surveysRef.document().id
            val survey = Survey(
                id = id,
                title = title,
                description = description
            )
            val addition = surveysRef.document(id).set(survey).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    fun getSurveyById(id: String) = callbackFlow {

        trySend(Response.Loading)
        try {
            surveysRef.document(id).get()
                .addOnSuccessListener { document ->
                    if(document != null) {
                        val survey = document.toObject(Survey::class.java)
                        trySend(Response.Success(survey))
                    }
                    else {
                        trySend(Response.Error("Document couldn't found"))
                    }
                }
                .addOnFailureListener {
                    trySend(Response.Error("Document couldn't fetch"))
                }
        } catch(e: Exception) {
            trySend(Response.Error(e.message.toString()))
        }

        awaitClose {cancel()}

/*
        try {
            Response.Loading
            Log.d("Mesaj: ", "document yükleniyo")
            surveysRef.document(id).get()
                .addOnSuccessListener { document ->
                    val response = if (document != null) {
                        val survey = document.toObject(Survey::class.java)
                        Response.Success(survey)
                    } else {
                        Response.Error("document bulunamadı")
                    }
                    trySend(response).isSuccess
                }
                .addOnFailureListener {
                    Response.Error("failed")
                    Log.d("Mesaj: ", "failed")
                }
        } catch (e: Exception) {
            Error(e.message ?: e.toString())
            Log.d("Mesaj: ", e.message.toString())
        }
        awaitClose {

        }
*/
    }

    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}

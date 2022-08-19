package com.example.surveyapp.data.repository

import android.util.Log
import com.example.surveyapp.common.Constants.TITLE
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.models.Option
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
                Response.Success(surveys) //Burada try-send yapman gerekiyor olabilir
            } else {
                Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    suspend fun addSurveyToFirestore(title: String, options: List<Option>) = flow {
        try {
            emit(Response.Loading)
            val id = surveysRef.document().id.take(6)
            val survey = Survey(
                id = id,
                title = title,
                options = options
            )
            surveysRef.document(id).set(survey).await()
            emit(Response.Success(survey))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    fun getSurveyById(id: String) = callbackFlow {
        trySend(Response.Loading)
        try {
            surveysRef.document(id).get()
                .addOnSuccessListener { document ->
                    if(document.exists()) {
                        val survey = document.toObject(Survey::class.java)
                        trySend(Response.Success(survey))
                    }
                    else {
                        trySend(Response.Error("Survey not found"))
                    }
                }
                .addOnFailureListener {
                    trySend(Response.Error("A problem has occured, please try again"))
                }
        } catch(e: Exception) {
            trySend(Response.Error(e.message.toString()))
        }

        awaitClose {cancel()}
    }

    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}

package com.example.surveyapp.data.repository

import android.util.Log
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.models.Email
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.data.models.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Named

class FirebaseRepository(
    private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider,
    private val surveysRef: CollectionReference,
    private val usersRef: CollectionReference
) {

/*
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
*/

    suspend fun getUser(email: String) = callbackFlow {
        try {
            trySend(Response.Loading)
            usersRef.document(email).get()
                .addOnSuccessListener { document ->
                    if(document.exists()) {
                        Log.d("Mesaj: ", "user var")
                        val user = document.toObject(User::class.java)
                        trySend(Response.Success(user))
                    }
                    else {
                        Log.d("Mesaj: ", "user yok")
                        val user = User(
                            email = email
                        )
                        usersRef.document(email).set(user)
                        Log.d("Mesaj: ", "yeni user oluşturuldu")
                        trySend(Response.Success(user))
                    }
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: e.toString()))
        }
        awaitClose { cancel() }
    }

    suspend fun addSurveyToFirestore(
        isOwnVoteChecked: Boolean,
        emailName: String,
        title: String,
        options: List<Option>
    ) = flow {
        try {
            emit(Response.Loading)
            val id = surveysRef.document().id.take(6)
            val survey = Survey(
                id = id,
                title = title,
                options = options
            )
            usersRef.document(emailName).collection("ownedSurveys").document(id).set(survey).await()
            Log.d("Mesaj: ", "Anket usera eklendi")
            surveysRef.document(id).set(survey).await()
            Log.d("Mesaj: ", "Anket surveyse eklendi")
            if (!isOwnVoteChecked) {
                val email = Email(
                    name = emailName
                )
                surveysRef.document(id).collection("emails").document(email.name).set(email).await()
            }
            emit(Response.Success(survey))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    suspend fun voteSurvey(emailName: String, id: String, optionId: Int, options: List<Option>, surveyTitle: String) =
        flow {
            try {
                emit(Response.Loading)
                val email = Email(
                    name = emailName,
                    votedOptionId = optionId
                )
                val survey = Survey(
                    id = id,
                    title = surveyTitle
                )
                usersRef.document(emailName).collection("votedSurveys").document(id).set(survey).await()
                Log.d("Mesaj: ", "Anket votedSurveysa eklendi")
                surveysRef.document(id).collection("emails").document(email.name).set(email).await()
                Log.d("Mesaj: ", "Anket surveyse eklendi")
                val updatedOptions = options
                updatedOptions[optionId].numberOfVotes += 1
                surveysRef.document(id).update(mapOf("options" to updatedOptions)).await()
                emit(Response.Success(email))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }

    fun getEmailById(email: String, id: String) = callbackFlow {
        trySend(Response.Loading)
        try {
            surveysRef.document(id).collection("emails").document(email).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        if (result.exists()) {
                            val email = result.toObject(Email::class.java)
                            trySend(Response.Success(email)) //BURADA DAHA SONRA VERİ GÖNDEREBİLİRSİN
                        } else {
                            trySend(Response.Error("Email not found"))
                        }
                    }
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.message.toString()))
        }
        awaitClose { cancel() }
    }

    fun getSurveyById(id: String) = callbackFlow {
        trySend(Response.Loading)
        try {
            surveysRef.document(id).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val survey = document.toObject(Survey::class.java)
                        trySend(Response.Success(survey))
                    } else {
                        trySend(Response.Error("Survey not found"))
                    }
                }
                .addOnFailureListener {
                    trySend(Response.Error("A problem has occured, please try again"))
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.message.toString()))
        }

        awaitClose { cancel() }
    }

    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}

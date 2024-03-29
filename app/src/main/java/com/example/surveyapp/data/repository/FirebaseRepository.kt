package com.example.surveyapp.data.repository

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.util.Log
import com.example.surveyapp.common.Constants.ADDED_DATE
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.models.Email
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.data.models.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import io.grpc.Deadline
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Named
import kotlin.collections.ArrayList

class FirebaseRepository(
    private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider,
    private val surveysRef: CollectionReference,
    private val usersRef: CollectionReference
) {

    suspend fun getSurveys(email: String, collectionName: String) = callbackFlow {
        try {
            trySend(Response.Loading)
            usersRef.document(email).collection(collectionName).orderBy(ADDED_DATE, Query.Direction.DESCENDING).get()
                .addOnSuccessListener { result ->
                    val surveys = ArrayList<Survey>()
                    for(document in result) {
                        val survey = document.toObject(Survey::class.java)
                        surveys.add(survey)
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    trySend(Response.Success(surveys))
                }
                .addOnFailureListener {
                    trySend(Response.Error("Surveys could not be retrieved"))
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: e.toString()))
        }
        awaitClose { cancel() }
    }

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
        options: List<Option>,
        deadline: Date
    ) = flow {
        try {
            emit(Response.Loading)
            val id = surveysRef.document().id.take(6)
            val survey = Survey(
                id = id,
                title = title,
                owner = emailName,
                options = options,
                deadline = deadline
            )
            usersRef.document(emailName).collection("ownedSurveys").document(id).set(survey)
            surveysRef.document(id).set(survey).await()
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

    suspend fun voteSurvey(emailName: String, id: String, optionId: Int,
                           options: List<Option>, surveyTitle: String, surveyDeadline: Date) =
        flow {
            try {
                emit(Response.Loading)
                val email = Email(
                    name = emailName,
                    votedOptionId = optionId
                )
                val survey = Survey(
                    id = id,
                    title = surveyTitle,
                    deadline = surveyDeadline
                )
                usersRef.document(emailName).collection("votedSurveys").document(id).set(survey)
                surveysRef.document(id).collection("emails").document(email.name).set(email).await()
                val updatedOptions = options
                updatedOptions[optionId].numberOfVotes += 1
                surveysRef.document(id).update(mapOf("options" to updatedOptions)).await()
                emit(Response.Success(email))
            }
            catch (e: Exception) {
                emit(Response.Error("An error occurred. Survey may have been deleted"))
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
                        trySend(Response.Error("Survey not found. It may have been deleted"))
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

    fun deleteSurvey(id: String, email: String) = callbackFlow {
        try {
            trySend(Response.Loading)
            usersRef.document(email).collection("ownedSurveys").document(id).delete()
                .addOnSuccessListener {
                    Log.d("Mesaj: ", "owned'tan silme başarılı")
                    trySend(Response.Success(it))
                }
                .addOnFailureListener {
                    Log.d("Mesaj: ", "owned'tan silme başarısız")
                    trySend(Response.Error("Survey could not be deleted"))
                }
            usersRef.document(email).collection("votedSurveys").document(id).delete()
                .addOnSuccessListener {
                    Log.d("Mesaj: ", "voted'tan silme başarılı")
                    trySend(Response.Success(it))
                }
                .addOnFailureListener {
                    Log.d("Mesaj: ", "voted'tan silme başarısız")
                    trySend(Response.Error("Survey could not be deleted"))
                }
            surveysRef.document(id).delete()
                .addOnSuccessListener {
                    trySend(Response.Success(it))
                }
                .addOnFailureListener {
                    trySend(Response.Error("Survey could not be deleted"))
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.message.toString()))
        }
        awaitClose { cancel() }
    }

/*
    suspend fun getSurveys(email: String, collectionName: String) = callbackFlow {
        try {
            trySend(Response.Loading)
            usersRef.document(email).collection(collectionName).orderBy(ADDED_DATE, Query.Direction.DESCENDING).get()
                .addOnSuccessListener { result ->
                    val surveys = ArrayList<Survey>()
                    for(document in result) {
                        val survey = document.toObject(Survey::class.java)
                        surveys.add(survey)
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    trySend(Response.Success(surveys))
                }
                .addOnFailureListener {
                    trySend(Response.Error("Surveys could not be retrieved"))
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: e.toString()))
        }
        awaitClose { cancel() }
    }
*/


    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)



}

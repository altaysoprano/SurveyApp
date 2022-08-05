package com.example.surveyapp.data.repository

import com.example.surveyapp.common.Constants.TITLE
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.models.Survey
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository(
    private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider,
    private val surveysRef: CollectionReference
) {

    fun getBooksFromFirestore() = callbackFlow {
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

    suspend fun addBookToFirestore(title: String, description: String) = flow {
        try {
            emit(Response.Loading)
            val id = surveysRef.document().id
            val book = Survey(
                id = id,
                title = title,
                description = description
            )
            val addition = surveysRef.document(id).set(book).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}

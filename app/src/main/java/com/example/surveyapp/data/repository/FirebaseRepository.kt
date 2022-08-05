package com.example.surveyapp.data.repository

import com.example.surveyapp.common.Constants.TITLE
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.models.Survey
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider,
    private val booksRef: CollectionReference
) {

    fun getBooksFromFirestore() = callbackFlow {
        val snapshotListener = booksRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
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


    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}

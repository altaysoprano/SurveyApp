package com.example.surveyapp.data.models

import com.example.surveyapp.common.Response
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

data class User(
    val email: String = "",
)

/*
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
*/

package com.example.surveyapp.data.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthLoginSource {

    suspend fun loginWithCredential(authCredential: AuthCredential) : FirebaseUser?

}

package com.example.surveyapp.data.repository

import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseSocialLoginSourceProvider: FirebaseAuthLoginSourceProvider) {

    suspend fun loginWithCredential(authCredential: AuthCredential) =
        firebaseSocialLoginSourceProvider.loginWithCredential(authCredential)

}

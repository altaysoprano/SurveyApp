package com.example.surveyapp.domain

import com.example.surveyapp.common.AuthenticationResource
import com.example.surveyapp.common.FirebaseAuthenticationResult
import com.example.surveyapp.data.repository.FirebaseRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseAuthUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(authCredential: AuthCredential)= flow{
        emit(FirebaseAuthenticationResult.InProgress(AuthenticationResource.IN_PROGRESS))
        try {
            firebaseRepository.loginWithCredential(authCredential)?.let {
                emit(FirebaseAuthenticationResult.Success(AuthenticationResource.AUTHENTICATED))
            } ?: run {
                emit(FirebaseAuthenticationResult.Failure(AuthenticationResource.UNAUTHENTICATED,null))
            }
        } catch (e: FirebaseAuthException){
            emit(FirebaseAuthenticationResult.Failure(AuthenticationResource.UNAUTHENTICATED,e))
        }
    }
}

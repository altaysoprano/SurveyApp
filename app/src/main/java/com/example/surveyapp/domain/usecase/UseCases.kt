package com.example.surveyapp.domain.usecase

import javax.inject.Inject

data class UseCases @Inject constructor(
    val firebaseAuthUseCase: FirebaseAuthUseCase
)

package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class GetSurveys(
    private val repo: FirebaseRepository
) {

    suspend operator fun invoke(
        email: String,
        collectionName: String
    ) = repo.getSurveys(email, collectionName)
}

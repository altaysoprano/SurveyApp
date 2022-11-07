package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class DeleteSurvey(
    private val repo: FirebaseRepository
) {
    operator fun invoke(
        id: String,
        email: String
    ) = repo.deleteSurvey(id = id, email = email)
}
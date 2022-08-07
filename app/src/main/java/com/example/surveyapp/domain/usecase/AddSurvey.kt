package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class AddSurvey(
    private val repo: FirebaseRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String
    ) = repo.addSurveyToFirestore(title, description)
}
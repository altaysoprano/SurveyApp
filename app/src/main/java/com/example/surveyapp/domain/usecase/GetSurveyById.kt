package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class GetSurveyById(
    private val repo: FirebaseRepository
) {
    operator fun invoke(id: String) = repo.getSurveyById(id)
}
package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class GetSurveys(
    private val repo: FirebaseRepository
) {
    operator fun invoke() = repo.getSurveysFromFirestore()
}

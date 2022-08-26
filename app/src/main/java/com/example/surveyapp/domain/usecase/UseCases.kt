package com.example.surveyapp.domain.usecase

import javax.inject.Inject

data class UseCases @Inject constructor(
    val firebaseAuthUseCase: FirebaseAuthUseCase,
    val getSurveys: GetSurveys,
    val addSurvey: AddSurvey,
    val getSurveyById: GetSurveyById,
    val voteSurvey: VoteSurvey
)

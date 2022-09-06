package com.example.surveyapp.presentation.main

import com.example.surveyapp.data.models.Survey

data class SurveyListState(
    val isOwnedSurveysLoading: Boolean = false,
    val isVotedSurveysLoading: Boolean = false,
    val ownedSurveysData: List<Survey> = listOf(),
    val votedSurveysData: List<Survey> = listOf(),
    val ownedSurveysError: String = "",
    val votedSurveysError: String = ""
)

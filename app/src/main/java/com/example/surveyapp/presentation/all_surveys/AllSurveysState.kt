package com.example.surveyapp.presentation.all_surveys

import com.example.surveyapp.data.models.Survey

data class AllSurveysState(
    val isLoading: Boolean = false,
    val data: List<Survey> = listOf(),
    val error: String = "",
    val limit: Long = 20,
    val isPaginating: Boolean = false
)


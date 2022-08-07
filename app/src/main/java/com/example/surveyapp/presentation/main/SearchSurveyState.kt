package com.example.surveyapp.presentation.main

import com.example.surveyapp.data.models.Survey
import com.squareup.okhttp.Response

data class SearchSurveyState(
    val isLoading: Boolean = false,
    val data: Survey? = null,
    val error: String = "",
    val isTextBlank: Boolean = false
)

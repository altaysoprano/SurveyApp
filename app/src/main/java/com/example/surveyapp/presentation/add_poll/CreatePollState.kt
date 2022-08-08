package com.example.surveyapp.presentation.add_poll

data class CreatePollState(
    val title: String = "",
    val description: String = "",
    val isTitleBlank: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)

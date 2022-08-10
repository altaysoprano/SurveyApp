package com.example.surveyapp.presentation.add_poll

import com.example.surveyapp.data.models.Option

data class CreatePollState(
    val title: String = "",
    val options: List<Option> = listOf(Option("", 0), Option("", 0)),
    val isTitleBlank: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)

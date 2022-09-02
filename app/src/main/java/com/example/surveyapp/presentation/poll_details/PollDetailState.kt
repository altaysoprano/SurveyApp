package com.example.surveyapp.presentation.poll_details

import com.example.surveyapp.data.models.Email

data class PollDetailState(
    val isVoted: Boolean = false,
    val isLoading: Boolean = false,
    val loadingText: String = "",
    val email: Email? = null
)

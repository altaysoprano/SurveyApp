package com.example.surveyapp.presentation.poll_details

data class PollDetailState(
    val isVoted: Boolean = false,
    val isLoading: Boolean = false,
    val loadingText: String = ""
)

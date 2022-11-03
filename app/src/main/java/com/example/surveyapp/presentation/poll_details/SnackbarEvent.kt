package com.example.surveyapp.presentation.poll_details

sealed class SnackbarEvent {
    data class VotedSurveySnackbar(val message: String) : SnackbarEvent()
    data class ShowPermanentlyDeniedSnackbar(val message: String) : SnackbarEvent()
}


package com.example.surveyapp.presentation.poll_details

sealed class SnackbarEvent {
    data class VotedSurveySnackbar(val message: String) : SnackbarEvent()
    data class CantVoteDeletedSurveySnackbar(val message: String) : SnackbarEvent()
    data class SurveyNotFoundSnackbar(val message: String) : SnackbarEvent()
}


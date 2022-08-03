package com.example.surveyapp.presentation.login

data class AuthState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)

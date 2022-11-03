package com.example.surveyapp.presentation.add_poll

data class PermissionsCheckState(
    val isPermissionsGranted: Boolean = false,
    val message: String = ""
)
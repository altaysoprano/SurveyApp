package com.example.surveyapp.data.models

data class Survey(
    var id: String? = null,
    val title: String = "",
    val options: List<Option> = listOf()
)

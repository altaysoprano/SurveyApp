package com.example.surveyapp.presentation.main.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.surveyapp.data.models.Survey

@Composable
fun SurveysContent(
    padding: PaddingValues,
    surveys: List<Survey>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding)
    ) {
        items(
            items = surveys
        ) { survey ->
            BookCard(
                survey = survey,
            )
        }
    }
}

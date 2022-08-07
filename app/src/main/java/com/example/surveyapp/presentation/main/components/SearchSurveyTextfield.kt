package com.example.surveyapp.presentation.main.components

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.presentation.main.HomeViewModel

@Composable
fun SearchSurvey(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val text = viewModel.surveySearchText

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.75f),
        value = text.value,
        label = { Text(text = "Enter the Survey Code") },
        onValueChange = {
            text.value = it
        },
        singleLine = true
    )
    Spacer(modifier = Modifier.height(8.dp))
    Log.d("Mesaj: ", "butondaki text.value: ${text.value}")
    SearchSurveyButton {viewModel.onSearchSurvey(text.value)}

}
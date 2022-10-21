package com.example.surveyapp.presentation.main.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.surveyapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.presentation.main.HomeViewModel
import com.example.surveyapp.ui.theme.option3Color

@Composable
fun SearchSurveyTextfield(
    viewModel: HomeViewModel = hiltViewModel(),
    isTextBlank: Boolean,
    error: String
) {

    val text = viewModel.surveySearchText
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextFieldBackground(color = MaterialTheme.colors.background) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.70f),
                        value = text.value,
                        label = {
                            Text(
                                text = "Enter the Survey Code",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.background(
                                    MaterialTheme.colors.background
                                )
                            )
                        },
                        onValueChange = {
                            text.value = it
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedLabelColor = Color.Black,
                        )
                    )
                }
                SearchSurveyButton {
                    viewModel.onSearchSurvey(text.value)
                }
            }
            if (isTextBlank) {
                Text(
                    "Please enter a code", color = MaterialTheme.colors.error,
                    modifier = Modifier.background(MaterialTheme.colors.background).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            if (error.isNotBlank()) {
                Text(
                    error, color = MaterialTheme.colors.error,
                    modifier = Modifier.background(MaterialTheme.colors.background).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    // This box just wraps the background and the OutlinedTextField
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .height(72.dp)
                .padding(top = 8.dp) // adding some space to the label
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(4.dp)
                )
        )
        // OutlineTextField will be the content...
        content()
    }
}
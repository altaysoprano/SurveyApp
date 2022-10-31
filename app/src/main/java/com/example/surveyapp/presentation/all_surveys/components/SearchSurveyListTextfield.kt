package com.example.surveyapp.presentation.all_surveys.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.presentation.all_surveys.AllSurveysViewModel
import com.example.surveyapp.presentation.main.components.OutlinedTextFieldBackground
import com.example.surveyapp.presentation.main.components.SearchSurveyButton

@Composable
fun SearchSurveyListTextfield(
    viewModel: AllSurveysViewModel = hiltViewModel()
) {

    val text = viewModel.allSurveysState.value.text

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.11f),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    value = text,
                    label = {
                        Text(
                            text = "Search Survey",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.background(
                                MaterialTheme.colors.background
                            )
                        )
                    },
                    onValueChange = {
                        viewModel.onSearch(it)
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.Black,
                    ),
                    trailingIcon = {
                        if (text.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Text",
                                modifier = Modifier.clickable { viewModel.onSearch("") }
                            )
                        }
                    }
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
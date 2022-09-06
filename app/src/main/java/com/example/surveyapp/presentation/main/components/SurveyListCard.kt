package com.example.surveyapp.presentation.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.R

@Composable
fun SurveyListCard(isSurveyListEmpty: Boolean, isLoading: Boolean, title: String, size: Float) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth()
            .fillMaxSize(size),
        elevation = 4.dp,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (isSurveyListEmpty && !isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(id = R.drawable.ic_no_survey_found_24),
                        contentDescription = "No survey found",
                        modifier = Modifier.size(32.dp)
                        )
                    Text("No survey found")
                }
            }
        }
    }
    //LazyColumn vs. burada yap, üstte parametre olarak anketleri al
}
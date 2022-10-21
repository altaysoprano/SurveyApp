package com.example.surveyapp.presentation.main.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.R
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.ui.theme.Blue300

@Composable
fun SurveyListCard(
    isLoading: Boolean,
    title: String,
    size: Float,
    surveyList: List<Survey>,
    onSeeAllClick: () -> Unit,
    onItemClick: (String) -> Unit,
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    modifier = Modifier.padding(8.dp).clickable {onSeeAllClick()},
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    text = "See All >",
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colors.primary
                )
            }
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (surveyList.isEmpty() && !isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_no_survey_found_24),
                            contentDescription = "No survey found",
                            modifier = Modifier.size(32.dp)
                        )
                        Text("No survey found")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    items(
                        items = surveyList
                    ) { survey ->
                        SurveyCard(
                            survey = survey
                        ) { id ->
                            onItemClick(id)
                        }
                    }
                }
            }
        }
    }
}
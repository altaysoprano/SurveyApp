package com.example.surveyapp.presentation.all_surveys.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.R
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.presentation.main.components.SurveyCard
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color


@Composable
fun AllSurveysCard(
    isLoading: Boolean,
    title: String,
    size: Float,
    surveyList: List<Survey>,
    listSize: Int,
    searchText: String,
    onItemClick: (String) -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .fillMaxSize(size),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchSurveyListTextfield()
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (surveyList.isEmpty() && !isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f),
                    contentAlignment = Alignment.Center
                ) {
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
                val listState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    state = listState
                ) {
                    items(
                        items = surveyList.take(listSize)
                    ) { survey ->
                        AllSurveysCardItem(
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
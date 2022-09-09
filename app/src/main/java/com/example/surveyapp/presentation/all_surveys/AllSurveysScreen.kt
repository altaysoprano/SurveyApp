package com.example.surveyapp.presentation.all_surveys

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.presentation.all_surveys.components.AllSurveysCard
import com.example.surveyapp.presentation.main.HomeViewModel
import com.example.surveyapp.presentation.main.components.SurveyListCard
import com.google.gson.Gson

@Composable
fun AllSurveysScreen(
    allSurveysViewModel: AllSurveysViewModel = hiltViewModel(),
    navController: NavController
) {

    val allSurveysState = allSurveysViewModel.allSurveysState
    val searchSurveyState = allSurveysViewModel.searchSurveyState
    val context = LocalContext.current

    LaunchedEffect(key1 = searchSurveyState.value.data) {
        var survey = searchSurveyState.value.data
        if (survey != null) {
            val json = Uri.encode(Gson().toJson(survey))
            navController.navigate("${context.getString(R.string.poll_detail_screen)}/$json")
            allSurveysViewModel.onNavigatedToPollDetail()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        AllSurveysCard(
            isLoading = allSurveysState.value.isLoading,
            title = "My Surveys",
            size = 1f,
            surveyList = allSurveysState.value.data,
            listSize = allSurveysState.value.data.size
        ) { id ->
            allSurveysViewModel.getSurveyById(id)
        }
        if (searchSurveyState.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
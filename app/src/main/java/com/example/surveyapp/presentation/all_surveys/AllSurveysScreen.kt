package com.example.surveyapp.presentation.all_surveys

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.presentation.all_surveys.components.AllSurveysCard
import com.example.surveyapp.presentation.all_surveys.components.SearchSurveyListTextfield
import com.example.surveyapp.presentation.main.HomeViewModel
import com.example.surveyapp.presentation.main.components.SearchSurveyTextfield
import com.example.surveyapp.presentation.main.components.SurveyListCard
import com.google.gson.Gson

@Composable
fun AllSurveysScreen(
    allSurveysViewModel: AllSurveysViewModel = hiltViewModel(),
    navController: NavController,
    collectionName: String
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
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "Surveys",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon =
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AllSurveysCard(
                isLoading = allSurveysState.value.isLoading,
                title = "Surveys",
                size = 1f,
                surveyList = allSurveysState.value.data,
                listSize = allSurveysState.value.data.size,
                searchText = allSurveysState.value.text,
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

            if (allSurveysState.value.isPaginating) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .alpha(0.5f)
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
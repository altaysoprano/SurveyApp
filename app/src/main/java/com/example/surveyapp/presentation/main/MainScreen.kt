package com.example.surveyapp.presentation.main

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.common.Constants.OWNED_SURVEYS
import com.example.surveyapp.common.Constants.VOTED_SURVEYS
import com.example.surveyapp.presentation.login.LoginViewModel
import com.example.surveyapp.presentation.main.components.SearchSurveyTextfield
import com.example.surveyapp.presentation.main.components.SurveyCard
import com.example.surveyapp.presentation.main.components.SurveyListCard
import com.example.surveyapp.ui.theme.Blue300
import com.google.gson.Gson

@Composable
fun MainScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val authState = loginViewModel.authenticationState
    val searchSurveyState = homeViewModel.searchSurveyState
    val surveyListState = homeViewModel.surveyListState

    LaunchedEffect(key1 = authState.value.isSignedIn) {
        if (!authState.value.isSignedIn) {
            navController.navigate(context.getString(R.string.login_screen)) {
                popUpTo(context.getString(R.string.main_screen)) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(key1 = searchSurveyState.value.data) {
        var survey = searchSurveyState.value.data
        if (survey != null) {
            val json = Uri.encode(Gson().toJson(survey))
            navController.navigate("${context.getString(R.string.poll_detail_screen)}/$json")
            homeViewModel.onNavigatedToPollDetail()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(context.getString(R.string.create_poll_screen))
                },
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Create A Poll"
                    )
                },
                text = { Text("Create A Survey") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        topBar = {
            SearchSurveyTextfield(
                isTextBlank = searchSurveyState.value.isTextBlank,
                error = searchSurveyState.value.error
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column() {
                SurveyListCard(
                    isLoading = surveyListState.value.isOwnedSurveysLoading,
                    surveyList = surveyListState.value.ownedSurveysData.take(3),
                    title = "My Latest Surveys",
                    size = 0.47f,
                    onSeeAllClick = {
                        navController.navigate("${context.getString(R.string.all_surveys_screen)}/$OWNED_SURVEYS")
                    }
                ) { id ->
                    homeViewModel.getSurveyById(id)
                }
                SurveyListCard(
                    isLoading = surveyListState.value.isVotedSurveysLoading,
                    surveyList = surveyListState.value.votedSurveysData.take(3),
                    title = "I Voted Last",
                    size = 1f,
                    onSeeAllClick = {
                        navController.navigate("${context.getString(R.string.all_surveys_screen)}/$VOTED_SURVEYS")
                    }
                ) { id ->
                    homeViewModel.getSurveyById(id)
                }
            }
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


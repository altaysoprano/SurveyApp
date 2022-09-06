package com.example.surveyapp.presentation.main

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.surveyapp.presentation.login.LoginViewModel
import com.example.surveyapp.presentation.main.components.SearchSurveyTextfield
import com.example.surveyapp.presentation.main.components.SurveyCard
import com.example.surveyapp.presentation.main.components.SurveyListCard
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
                text = { Text("Create A Survey") }
            )
        },
        backgroundColor = MaterialTheme.colors.primary
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SearchSurveyTextfield()
            if (searchSurveyState.value.isTextBlank) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Please enter a code", color = MaterialTheme.colors.error)
            }
            if (searchSurveyState.value.error.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(searchSurveyState.value.error, color = MaterialTheme.colors.error)
            }
            Spacer(modifier = Modifier.height(24.dp))
            SurveyListCard(
                isSurveyListEmpty = surveyListState.value.ownedSurveysData.isEmpty(),
                isLoading = surveyListState.value.isOwnedSurveysLoading,
                title = "My Surveys",
                size = 0.35f
            )
            SurveyListCard(
                isSurveyListEmpty = surveyListState.value.votedSurveysData.isEmpty(),
                isLoading = surveyListState.value.isVotedSurveysLoading,
                title = "Surveys I've Voted",
                size = 0.7f
            )
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
        //AŞAĞIDAKİ KISMI CARDIN İÇİNE YAZACAKSIN
/*
        if(surveyListState.value.data.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Liste boş")
            }
        }
*/
    }
}

//AŞAĞIDAKİLERİ DAHA SONRA UYGUN YERLERE KOY
/*
        when (val surveysReference = homeViewModel.surveysReference) {
            is Response.Loading -> Log.d("Mesaj: ", "Yükleniyor")
            is Response.Success -> {
                SurveysContent(
                    padding = padding,
                    surveys = surveysReference.data,
                )
            }
            is Error -> Log.d("Mesaj: ", surveysReference.message.toString())
        }
*/

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
            Card(shape = MaterialTheme.shapes.large, elevation = 12.dp,
                backgroundColor = MaterialTheme.colors.background, modifier = Modifier.padding(vertical = 20.dp)) {
                Column() {
                    SurveyListCard(
                        isLoading = surveyListState.value.isOwnedSurveysLoading,
                        surveyList = surveyListState.value.ownedSurveysData,
                        title = "My Surveys",
                        size = 0.47f,
                        listSize = 3,
                        onSeeAllClick = {
                            navController.navigate("${context.getString(R.string.all_surveys_screen)}/$OWNED_SURVEYS")
                        }
                    ) { id ->
                        homeViewModel.getSurveyById(id)
                    }
                    SurveyListCard(
                        isLoading = surveyListState.value.isVotedSurveysLoading,
                        surveyList = surveyListState.value.votedSurveysData,
                        title = "Surveys I've Voted",
                        size = 0.68f,
                        listSize = 2,
                        onSeeAllClick = {
                            navController.navigate("${context.getString(R.string.all_surveys_screen)}/$VOTED_SURVEYS")
                        }
                    ) { id ->
                        homeViewModel.getSurveyById(id)
                    }
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

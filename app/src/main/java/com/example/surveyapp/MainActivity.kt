package com.example.surveyapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.surveyapp.common.SurveyType
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.presentation.CreatePollScreen
import com.example.surveyapp.presentation.all_surveys.AllSurveysScreen
import com.example.surveyapp.presentation.login.LoginScreen
import com.example.surveyapp.presentation.main.MainScreen
import com.example.surveyapp.presentation.poll_details.PollDetailScreen
import com.example.surveyapp.ui.theme.SurveyAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPermissionsApi
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurveyAppTheme {
                Navigation()
            }
        }
    }
}

@ExperimentalPermissionsApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = context.getString(R.string.main_screen)
    ) {
        composable(context.getString(R.string.login_screen)) {
            LoginScreen(navController = navController)
        }
        composable(context.getString(R.string.main_screen)) {
            MainScreen(navController = navController)
        }
        composable(context.getString(R.string.create_poll_screen)) {
            CreatePollScreen(navController = navController)
        }
        // NavHost(startDestination = "profile/{userId}") {
        composable(
            route = "${context.getString(R.string.poll_detail_screen)}/{survey}",
            arguments = listOf(
                navArgument("survey") {
                    type = SurveyType()
                }
            )
        ) { entry ->
            PollDetailScreen(survey = entry.arguments?.getParcelable<Survey>("survey"), navController = navController)
        }
        composable(
            route = "${context.getString(R.string.all_surveys_screen)}/{collectionName}",
            arguments = listOf(
                navArgument("collectionName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            AllSurveysScreen(
                navController = navController,
                collectionName = entry.arguments?.getString("collectionName") ?: ""
            )
        }
    }
}

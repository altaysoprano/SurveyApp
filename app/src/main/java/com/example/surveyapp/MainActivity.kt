package com.example.surveyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.surveyapp.presentation.CreatePollScreen
import com.example.surveyapp.presentation.login.LoginScreen
import com.example.surveyapp.presentation.main.MainScreen
import com.example.surveyapp.presentation.poll_details.PollDetailScreen
import com.example.surveyapp.ui.theme.SurveyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(){
    val context= LocalContext.current
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = context.getString(R.string.main_screen)){
        composable(context.getString(R.string.login_screen)) {
            LoginScreen(navController = navController)
        }
        composable(context.getString(R.string.main_screen)){
            MainScreen(navController = navController)
        }
        composable(context.getString(R.string.create_poll_screen)){
            CreatePollScreen()
        }
        composable(context.getString(R.string.poll_detail_screen)) {
            PollDetailScreen()
        }
    }
}

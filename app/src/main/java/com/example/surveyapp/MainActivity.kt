package com.example.surveyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.surveyapp.presentation.login.LoginScreen
import com.example.surveyapp.presentation.main.MainScreen
import com.example.surveyapp.ui.theme.SurveyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurveyAppTheme {
                Navigation()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Navigation(){
    val context= LocalContext.current
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = context.getString(R.string.main_screen)){
        composable(context.getString(R.string.login_screen)) {
            LoginScreen(navController = navController)
            Log.d("Mesaj: ", "Logine geçti")
        }
        composable(context.getString(R.string.main_screen)){
            MainScreen(navController = navController)
            Log.d("Mesaj: ", "Home'a geçti")
        }
    }
}

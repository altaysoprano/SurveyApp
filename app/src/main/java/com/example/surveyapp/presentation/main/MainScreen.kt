package com.example.surveyapp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.presentation.login.LoginViewModel

@Composable
fun MainScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val isSignedIn = viewModel.isSignedIn

    LaunchedEffect(key1 = isSignedIn.value) {
        if(!isSignedIn.value) {
            navController.navigate(context.getString(R.string.login_screen)) {
                popUpTo(context.getString(R.string.main_screen)) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Şıhım hoşgelmişsen")

        }
    }
}

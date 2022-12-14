package com.example.surveyapp.presentation.login

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.surveyapp.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.surveyapp.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun GoogleSignInButton(
    text : String = "",
    loadingText: String = "" ,
    isLoading: Boolean,
    onClicked:() -> Unit
){
    Surface(
        onClick = { onClicked() },
        shape = Shapes.medium,
        border = BorderStroke(width = 1.dp,color = Color.LightGray),
        color = MaterialTheme.colors.surface
    ) {
        Row (modifier = Modifier
            .padding(
                start = 12.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
            .animateContentSize(
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            ),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center){
            Icon(painter = painterResource(id = R.drawable.ic_google_icon), contentDescription = "Google sign button", tint = Color.Unspecified)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (isLoading) loadingText else text)

            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }



}

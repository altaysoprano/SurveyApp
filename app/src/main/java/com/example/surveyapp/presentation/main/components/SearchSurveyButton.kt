package com.example.surveyapp.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.surveyapp.R

@Composable
fun SearchSurveyButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxHeight().offset((-4).dp),
        onClick = {
            onClick()
        }, elevation = ButtonDefaults.elevation(
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Search Survey")
    }

}
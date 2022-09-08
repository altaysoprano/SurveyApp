package com.example.surveyapp.presentation.add_poll.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CreatePollButton(
    modifier: Modifier,
    isButtonEnabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = Color.LightGray,
            backgroundColor = MaterialTheme.colors.primary
        ),
        enabled = isButtonEnabled
    ) {
        Text(text = "Create Survey", modifier = Modifier.padding(4.dp))
    }

}
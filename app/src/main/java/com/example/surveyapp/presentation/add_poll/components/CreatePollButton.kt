package com.example.surveyapp.presentation.add_poll.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreatePollButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        onClick = {
            onClick()
        }, elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(text = "Create Poll", modifier = Modifier.padding(4.dp))
    }

}
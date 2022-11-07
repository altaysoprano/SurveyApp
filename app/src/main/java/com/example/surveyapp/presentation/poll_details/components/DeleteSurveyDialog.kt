package com.example.surveyapp.presentation.poll_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeleteSurveyDialog(
    deleteDialogState: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    if (deleteDialogState) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            backgroundColor = MaterialTheme.colors.background,
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 15.dp,
                        disabledElevation = 0.dp
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        "Cancel",
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        color = MaterialTheme.colors.primary
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 15.dp,
                        disabledElevation = 0.dp
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        "DELETE",
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        color = Color.White
                    )
                }
            },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Delete Survey", fontWeight = FontWeight.Bold,
                            fontSize = 24.sp, color = MaterialTheme.colors.primary
                        )
                    }
                }
            },
            text = {
                Text(
                    text = "Do you really want to delete this survey?",
                    fontWeight = FontWeight.Bold,
                )
            }
        )
    }
}
package com.example.surveyapp.presentation.add_poll.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun SetTimeDialog(dialogState: Boolean, onConfirm: (Int, Int, Int) -> Unit, onDismiss: () -> Unit) {

    var dayValue by remember { mutableStateOf(0) }
    var hourValue by remember { mutableStateOf(0) }
    var minuteValue by remember { mutableStateOf(1) }

    Log.d("Mesaj: ", "Main: $dayValue, $hourValue, $minuteValue")

    if (dialogState) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Set Duration", fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            },
            text = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Day", fontWeight = FontWeight.Bold)
                        NumberPicker(
                            value = dayValue, onValueChange = {
                                dayValue = it
                                if (dayValue == 0 && hourValue == 0 && minuteValue == 0) minuteValue = 1
                            },
                            range = 0..7, textStyle = TextStyle(fontSize = 20.sp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Hour", fontWeight = FontWeight.Bold)
                        NumberPicker(
                            value = hourValue, onValueChange = {
                                hourValue = it
                                if (dayValue == 0 && hourValue == 0 && minuteValue == 0) minuteValue = 1
                            },
                            range = 0..23, textStyle = TextStyle(fontSize = 20.sp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Minute", fontWeight = FontWeight.Bold)
                        NumberPicker(
                            value = minuteValue, onValueChange = {
                                minuteValue = it
                            },
                            range =
                            if (dayValue == 0 && hourValue == 0) 1..59 else 0..59,
                            textStyle = TextStyle(fontSize = 20.sp)
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            Log.d("Mesaj: ", "$dayValue, $hourValue, $minuteValue")
                            onConfirm(dayValue, hourValue, minuteValue)
                        },
                        border = BorderStroke(1.dp, color = MaterialTheme.colors.onPrimary),
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        )

    }
}
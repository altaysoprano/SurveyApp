package com.example.surveyapp.presentation.add_poll.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.R
import com.example.surveyapp.presentation.add_poll.SurveyTime

@Composable
fun SetTimeButton(modifier: Modifier, surveyTime: SurveyTime, onClick: () -> Unit) {

    Button(
        modifier = modifier,
        onClick = { onClick() },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = Color.LightGray,
            backgroundColor = MaterialTheme.colors.background
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time_24),
                    contentDescription = "Duration Icon",
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(horizontal = 4.dp)
                        .size(16.dp)
                )
                Text(text = "Survey Duration")
            }
            Text(
                text = "${surveyTime.day}d ${surveyTime.hour}h ${surveyTime.minute}m",
                fontWeight = Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
}
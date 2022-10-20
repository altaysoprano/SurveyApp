package com.example.surveyapp.presentation.poll_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.surveyapp.R

@Composable
fun SurveyStateCard(backgroundColor: Color, text: String, icon: Int) {
    Card(backgroundColor = backgroundColor, elevation = 4.dp) {
        Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Check",
                tint = Color.White
            )
        }
    }
}
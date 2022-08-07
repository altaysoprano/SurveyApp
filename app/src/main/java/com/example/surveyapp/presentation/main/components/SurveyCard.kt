package com.example.surveyapp.presentation.main.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.data.models.Survey

@Composable
fun SurveyCard(
    survey: Survey,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
            ){
                survey.title?.let { title ->
                    Text(
                        text = title,
                        color = Color.DarkGray,
                        fontSize = 25.sp
                    )
                }
                survey.description?.let { description ->
                    Text(
                        text = description,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Log.d("Mesaj: ", "Anketin id'si: ${survey.id}")
            }
        }
    }
}

package com.example.surveyapp.presentation.main.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.R
import com.example.surveyapp.ui.theme.Blue300
import com.example.surveyapp.ui.theme.option6Color
import java.util.*

@Composable
fun SurveyCard(
    survey: Survey,
    onClick: (String) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var isSurveyOver by remember { mutableStateOf(false) }
    val currentDate = Calendar.getInstance().time
    var remainingTime: Long by remember { mutableStateOf(1000) }

    LaunchedEffect(key1 = true) {
        isSurveyOver = survey.deadline ?: currentDate < currentDate
        remainingTime = ((survey.deadline?.time ?: currentDate.time) - currentDate.time) / 1000
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick(survey.id ?: "") },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.70f)) {
                Text(
                    survey.title,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(survey.id ?: "", fontWeight = FontWeight.Bold, color = Color.LightGray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_copy_24),
                        contentDescription = "print",
                        modifier = Modifier
                            .clickable {
                                clipboardManager.setText(AnnotatedString((survey.id ?: "")))
                            }
                            .size(20.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(CenterVertically)
                    .fillMaxWidth(),
                contentAlignment = CenterEnd
            ) {
                Row() {
                    if (isSurveyOver) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_24),
                            contentDescription = "Check",
                            tint = Color(0xFF088700)
                        )
                    } else {
                        Spacer(modifier = Modifier.width(8.dp))
                        if (remainingTime <= 300 && remainingTime > 60)
                            Row(verticalAlignment = CenterVertically) {
                                Text(
                                    "${(remainingTime / 60)}m",
                                    color = MaterialTheme.colors.error,
                                    fontSize = 14.sp
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_hourglass),
                                    contentDescription = "Timer",
                                    tint = MaterialTheme.colors.error,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        else if (remainingTime <= 60) {
                            Row() {
                                Text(
                                    "${remainingTime}s",
                                    color = MaterialTheme.colors.error
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_hourglass_empty),
                                    contentDescription = "Timer",
                                    tint = MaterialTheme.colors.error
                                )
                            }
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right_24),
                        contentDescription = "Right Arrow"
                    )
                }
            }
        }
    }
/*
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
                Log.d("Mesaj: ", "Anketin id'si: ${survey.id}")
            }
        }
    }
*/
}

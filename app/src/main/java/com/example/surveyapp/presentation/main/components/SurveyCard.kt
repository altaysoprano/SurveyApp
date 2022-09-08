package com.example.surveyapp.presentation.main.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun SurveyCard(
    survey: Survey,
    onClick: (String) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

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
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                Text(
                    survey.title,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row() {
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
                Icon(
                    painter = painterResource(id = R.drawable.ic_right_24),
                    contentDescription = "Right Arrow"
                )
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

package com.example.surveyapp.presentation.poll_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.utils.parseIndexToColor

@Composable
fun UnvotedPollScreen(
    options: List<Option>?,
    onVoted: () -> Unit
) {

    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(vertical = 12.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(options ?: listOf()) { index, option ->
                Card(
                    elevation = 4.dp,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .clickable {
                                onVoted()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Text(
                            text = option.name,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }

    }
}














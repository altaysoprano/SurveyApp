package com.example.surveyapp.presentation.poll_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.utils.parseIndexToColor

@ExperimentalFoundationApi
@Composable
fun PollDetailScreen(
    survey: Survey?
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Center),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var totalVotes = 0

                    Text(text = "${survey?.title}", fontWeight = FontWeight.Bold)
                    survey?.options.let { options ->
                        options?.forEach { totalVotes += it.numberOfVotes }

                        Surface(
                            elevation = 8.dp,
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .padding(vertical = 12.dp)
                        ) {
                            LazyColumn(
                                horizontalAlignment = CenterHorizontally,
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
                                        Column(
                                            modifier = Modifier.fillMaxSize()
                                        ) {

                                            val ratio =
                                                if (totalVotes != 0) {
                                                    option.numberOfVotes.toFloat() / totalVotes.toFloat()
                                                } else {
                                                    0.00f
                                                }

                                            val curPercent = animateFloatAsState(
                                                targetValue = if (animationPlayed) {
                                                    ratio
                                                } else {
                                                    0f
                                                },
                                                animationSpec = tween(
                                                    1000,
                                                    0
                                                )
                                            )

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        horizontal = 4.dp,
                                                        vertical = 4.dp
                                                    ),
                                                verticalAlignment = CenterVertically,
                                                horizontalArrangement = SpaceBetween
                                            ) {
                                                Text(
                                                    text = option.name,
                                                    textAlign = TextAlign.Start
                                                )
                                                Text(
                                                    text = "${option.numberOfVotes} votes (%${
                                                        "%.2f".format(
                                                            ratio * 100
                                                        )
                                                    })",
                                                    textAlign = TextAlign.End,
                                                    fontSize = 14.sp
                                                )

                                            }
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(4.dp)
                                            ) {

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(MaterialTheme.colors.surface),
                                                    verticalAlignment = CenterVertically
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth(
                                                                curPercent.value
                                                            )
                                                            .padding()
                                                            .background(parseIndexToColor(index = index))
                                                    ) {
                                                        Text("")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                stickyHeader {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 4.dp), verticalArrangement = Arrangement.Center) {
                                        Text(
                                            text = "Total Votes: $totalVotes",
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .align(
                                                    End
                                                ),
                                            letterSpacing = (-0.7).sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
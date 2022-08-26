package com.example.surveyapp.presentation.poll_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.utils.parseIndexToColor

@ExperimentalFoundationApi
@Composable
fun PollScreen(
    totalVotes: Int, options: List<Option>?, isVoted: Boolean,
    onVote: (optionId: Int) -> Unit
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable {
                            if (!isVoted) {
                                onVote(index)
                            }
                        },
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
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
                                1500,
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
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            Text(
                                text = option.name,
                                textAlign = TextAlign.Start
                            )
                            if (isVoted) {
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
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            if (isVoted) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colors.surface),
                                    verticalAlignment = Alignment.CenterVertically
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
            }
            stickyHeader {
                if (isVoted) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Total Votes: ",
                            letterSpacing = (-0.7).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Card(
                            backgroundColor = Color.Black
                        ) {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "$totalVotes",
                                letterSpacing = (-0.7).sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }


}
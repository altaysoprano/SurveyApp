package com.example.surveyapp.presentation.poll_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.R
import com.example.surveyapp.data.models.Email
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.utils.parseIndexToColor

@ExperimentalFoundationApi
@Composable
fun PollScreen(
    totalVotes: Int,
    options: List<Option>?,
    isVoted: Boolean,
    isLoading: Boolean,
    isOver: Boolean,
    email: Email?,
    onVote: (optionId: Int) -> Unit
) {

    var barMovementAnimationPlayed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isVoted) {
        barMovementAnimationPlayed = true
    }

    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(vertical = 12.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            itemsIndexed(options ?: listOf()) { index, option ->
                Card(
                    modifier = if (isVoted || isLoading || isOver) {
                        Modifier
                            .fillMaxWidth()
                            .alpha(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {}
                            .padding(8.dp)
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onVote(index)
                            }
                            .padding(8.dp)
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 4.dp
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
                            targetValue = if (barMovementAnimationPlayed) {
                                ratio
                            } else {
                                0f
                            },
                            animationSpec = tween(
                                1250,
                                0
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(0.75f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    text = option.name,
                                    textAlign = TextAlign.Start
                                )
                                if (index == email?.votedOptionId) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_check_24),
                                        contentDescription = "Check Icon", tint = Color(0XFF6ACB49)
                                    )
                                }
                            }
                            if (isVoted || isOver) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${option.numberOfVotes} votes",
                                        textAlign = TextAlign.End,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colors.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "(%${
                                            "%.2f".format(
                                                ratio * 100
                                            )
                                        })",
                                        textAlign = TextAlign.End,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            if (isVoted || isOver) {
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
                if (isVoted || isOver) {
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
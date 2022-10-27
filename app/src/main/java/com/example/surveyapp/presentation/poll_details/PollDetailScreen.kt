package com.example.surveyapp.presentation.poll_details

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.R
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.presentation.poll_details.components.SurveyStateCard
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun PollDetailScreen(
    survey: Survey?,
    viewModel: PollDetailViewModel = hiltViewModel()
) {

    val pollDetailState = viewModel.pollDetailState
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.snackbarFlow.collectLatest { event ->
            when(event) {
                is SnackbarEvent.VotedSurveySnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primary,
        scaffoldState = scaffoldState
        ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .align(Center),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    var totalVotes = 0
                    val isOver = pollDetailState.value.isOver

                    if (isOver) {
                        SurveyStateCard(
                            backgroundColor = Color(0xFF088700),
                            text = "Survey Concluded",
                            icon = R.drawable.ic_check_24
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    } else {
                        Row() {
                            Text(
                                text = "It will expire on: ",
                                modifier = Modifier
                                    .alpha(0.5f)
                                    .background(Color.White),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                            Text(
                                text = DateTimeUtil.formatSurveyDate(survey?.deadline), modifier = Modifier
                                    .alpha(0.6f)
                                    .background(Color.White),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(text = "${survey?.title}", fontWeight = FontWeight.Bold)
                    survey?.let { survey ->
                        survey.options.forEach { totalVotes += it.numberOfVotes }

                        PollScreen(
                            totalVotes = totalVotes, options = survey.options,
                            isVoted = pollDetailState.value.isVoted,
                            isLoading = pollDetailState.value.isLoading,
                            isOver = isOver,
                            email = pollDetailState.value.email
                        ) { optionId ->
                            survey.id?.let { id ->
                                viewModel.onVote(
                                    options = survey.options,
                                    optionId = optionId,
                                    id = id,
                                    surveyTitle = survey.title,
                                    deadline = survey.deadline ?: Date()
                                )
                            }
                        }
                    }
                }
            }
        }
        if (pollDetailState.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(pollDetailState.value.loadingText)
                }
            }
        }
    }
}

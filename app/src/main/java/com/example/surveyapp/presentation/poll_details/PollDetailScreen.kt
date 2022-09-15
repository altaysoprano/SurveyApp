package com.example.surveyapp.presentation.poll_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.data.models.Survey
import java.util.*

@ExperimentalFoundationApi
@Composable
fun PollDetailScreen(
    survey: Survey?,
    viewModel: PollDetailViewModel = hiltViewModel()
) {

    val pollDetailState = viewModel.pollDetailState

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primary
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
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var totalVotes = 0

                    Text(text = "${survey?.title}", fontWeight = FontWeight.Bold)
                    survey?.let { survey ->
                        survey.options.forEach { totalVotes += it.numberOfVotes }

                        PollScreen(
                            totalVotes = totalVotes, options = survey.options,
                            isVoted = pollDetailState.value.isVoted,
                            isLoading = pollDetailState.value.isLoading,
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

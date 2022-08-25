package com.example.surveyapp.presentation.poll_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.data.models.Survey

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
                    survey?.options.let { options ->
                        options?.forEach { totalVotes += it.numberOfVotes }

                        PollScreen(totalVotes = totalVotes, options = options,
                            isVoted = pollDetailState.value.isVoted) {
                            viewModel.onVoted()
                        }
                    }
                }
            }
        }
    }
}
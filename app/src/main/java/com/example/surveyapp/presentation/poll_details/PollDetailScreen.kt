package com.example.surveyapp.presentation.poll_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.surveyapp.data.models.Survey

@Composable
fun PollDetailScreen(
    survey: Survey?
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 32.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "${survey?.title}", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    survey?.options.let { options ->
                        var totalVotes = 0
                        options?.forEach { totalVotes += it.numberOfVotes }

                        LazyColumn(
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(options ?: listOf()) { option ->
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
                                        Text(
                                            text = option.name,
                                            modifier = Modifier.padding(
                                                horizontal = 4.dp,
                                                vertical = 12.dp
                                            )
                                        )
                                        Row(modifier = Modifier.fillMaxWidth().padding(4.dp).background(MaterialTheme.colors.surface)) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(
                                                        when {
                                                            option.numberOfVotes.toFloat() / totalVotes.toFloat() > 0.8f -> {
                                                                0.8f
                                                            }
                                                            option.numberOfVotes.toFloat() / totalVotes.toFloat() < 0.004f || totalVotes == 0 -> {
                                                                0.004f
                                                            }
                                                            else -> {
                                                                option.numberOfVotes.toFloat() / totalVotes.toFloat()
                                                            }
                                                        }
                                                    )
                                                    .padding()
                                                    .background(Color.Red)
                                            ) {
                                                Text("")
                                            }
                                            Text(
                                                text = option.numberOfVotes.toString(),
                                                modifier = Modifier.padding(horizontal = 2.dp),
                                                textAlign = TextAlign.End
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
}
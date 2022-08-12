package com.example.surveyapp.presentation

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.presentation.add_poll.CreatePollViewModel
import com.example.surveyapp.presentation.add_poll.components.AddOptionButton
import com.example.surveyapp.presentation.add_poll.components.CreatePollButton

@ExperimentalFoundationApi
@Composable
fun CreatePollScreen(
    viewModel: CreatePollViewModel = hiltViewModel()
) {

    val createPollState = viewModel.createPollState

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(Color.White),
                    value = createPollState.value.title,
                    label = { Text(text = "Ask something...") },
                    onValueChange = {
                        viewModel.onTitleChanged(it)
                    },
                    trailingIcon = {
                        if (createPollState.value.title.isNotBlank()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear Text",
                                modifier = Modifier.clickable { viewModel.onTitleChanged("") }
                            )
                        } else {
                        }
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(0.8f)
                ) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(createPollState.value.options) { index, option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                OutlinedTextField(
                                    modifier = if (option.isNewOption) {
                                        Modifier.fillMaxWidth(0.9f)
                                    } else {
                                        Modifier.fillMaxWidth()
                                    },
                                    value = option.name,
                                    label = { Text(text = "Option ${index + 1}") },
                                    onValueChange = {
                                        viewModel.onOptionChanged(it, index)
                                    },
                                    singleLine = true,
                                    trailingIcon = {
                                        if (createPollState.value.options[index].name.isNotBlank()) {
                                            Icon(
                                                Icons.Default.Clear,
                                                contentDescription = "Clear Text",
                                                modifier = Modifier.clickable {
                                                    viewModel.onOptionChanged(
                                                        "",
                                                        index
                                                    )
                                                }
                                            )
                                        } else {
                                        }
                                    }
                                )
                                if (option.isNewOption) {
                                    Icon(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .fillMaxWidth()
                                            .padding(2.dp)
                                            .clickable { viewModel.deleteOption(option) },
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Option"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        stickyHeader {
                            if(createPollState.value.options.size < 10) {
                                AddOptionButton {
                                    viewModel.addOption()
                                }
                            } else {
                                Text("You can add up to 10 options.", color = MaterialTheme.colors.error)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                if (createPollState.value.error.isNotBlank()) {
                    Text(
                        "An error has occured. Please try again.",
                        color = MaterialTheme.colors.error
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            if (createPollState.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            CreatePollButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                isButtonEnabled = createPollState.value.title.isNotBlank() && createPollState.value.options.all { it.name.isNotBlank() }) {
                // viewModel.addSurvey(createPollState.value.title, createPollState.value.description)
            }
        }
    }
}
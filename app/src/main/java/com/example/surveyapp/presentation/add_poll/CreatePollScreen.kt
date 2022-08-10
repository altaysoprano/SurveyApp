package com.example.surveyapp.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.surveyapp.presentation.add_poll.CreatePollViewModel
import com.example.surveyapp.presentation.add_poll.components.AddOptionButton
import com.example.surveyapp.presentation.add_poll.components.CreatePollButton

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
                    .fillMaxHeight(0.1f),
                value = createPollState.value.title,
                label = { Text(text = "Ask something...") },
                onValueChange = {
                    viewModel.onTitleChanged(it)
                }
            )
            if(createPollState.value.isTitleBlank) {
                Log.d("Mesaj: ", "title blank")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Title field cannot be left blank", color = MaterialTheme.colors.error)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                itemsIndexed(createPollState.value.options) {  index, option ->
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = option.name,
                        label = { Text(text = "Option ${index+1}") },
                        onValueChange = {
                            viewModel.onOptionChanged(it, index)
                        },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            if(createPollState.value.error.isNotBlank()) {
                Text("An error has occured. Please try again.", color = MaterialTheme.colors.error)
            }
            AddOptionButton {
                viewModel.addOption()
            }
            Spacer(modifier = Modifier.height(8.dp))
            CreatePollButton {
                // viewModel.addSurvey(createPollState.value.title, createPollState.value.description)
            }
        }
        if(createPollState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
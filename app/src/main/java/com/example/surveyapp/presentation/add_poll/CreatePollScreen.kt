package com.example.surveyapp.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.presentation.add_poll.CreatePollViewModel
import com.example.surveyapp.presentation.add_poll.components.*
import com.example.surveyapp.presentation.main.components.OutlinedTextFieldBackground

@ExperimentalFoundationApi
@Composable
fun CreatePollScreen(
    viewModel: CreatePollViewModel = hiltViewModel(),
    navController: NavController
) {

    val createPollState = viewModel.createPollState
    val context = LocalContext.current

    LaunchedEffect(key1 = createPollState.value.isAdded) {
        if (createPollState.value.isAdded) {
            navController.navigate(context.getString(R.string.main_screen)) {
                popUpTo(context.getString(R.string.create_poll_screen)) {
                    inclusive = true
                }
                popUpTo(context.getString(R.string.main_screen)) {
                    inclusive = true
                }
            }
            viewModel.onPollAdded()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        CreatePollAlertDialog(
            createPollState.value.dialogState,
            createPollState.value.data?.id ?: ""
        ) {
            viewModel.onAlertDialogDismiss()
        }
        SetTimeDialog(dialogState = createPollState.value.setTimeDialogState,
            onConfirm = { day, hour, minute ->
                viewModel.onSetTimeDialogConfirm(day, hour, minute)
            }) {
            viewModel.onSetTimeDialogDismiss()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextFieldBackground(color = MaterialTheme.colors.background) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(108.dp),
                        value = createPollState.value.title,
                        label = {
                            Text(
                                text = "Ask something...",
                                modifier = Modifier.background(MaterialTheme.colors.background)
                            )
                        },
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
                }
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(0.75f)
                ) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(createPollState.value.options) { index, option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                OutlinedTextFieldBackground(color = MaterialTheme.colors.background) {
                                    OutlinedTextField(
                                        modifier = if (option.isNewOption) {
                                            Modifier.fillMaxWidth(0.9f)
                                        } else {
                                            Modifier.fillMaxWidth()
                                        },
                                        value = option.name,
                                        label = {
                                            Text(
                                                text = "Option ${index + 1}",
                                                modifier = Modifier.background(MaterialTheme.colors.background)
                                            )
                                        },
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

                                }
                                if (option.isNewOption) {
                                    Icon(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .fillMaxWidth()
                                            .padding(2.dp)
                                            .clickable { viewModel.deleteOption(option) },
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Option",
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        stickyHeader {
                            if (createPollState.value.options.size < 10) {
                                AddOptionButton {
                                    viewModel.addOption()
                                }
                            } else {
                                Text(
                                    "You can add up to 10 options.",
                                    color = MaterialTheme.colors.error
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            SetTimeButton(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(vertical = 4.dp),
                                surveyTime = createPollState.value.surveyTime
                            ) {
                                viewModel.onSetTimeButtonPressed()
                            }
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                val isChecked = createPollState.value.isCheckBoxChecked
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (isChecked) 1f else 0.5f)
                        .clickable { viewModel.onCheckBoxChanged(!isChecked) },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("I want to vote in this survey", fontWeight = FontWeight.Bold)
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { viewModel.onCheckBoxChanged(it) },
                        enabled = true,
                        colors = CheckboxDefaults.colors(checkmarkColor = Color.Green)
                    )
                }
                CreatePollButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(bottom = 8.dp, end = 8.dp, start = 8.dp),
                    isButtonEnabled = createPollState.value.title.isNotBlank() && createPollState.value.options.all { it.name.isNotBlank() }) {
                    viewModel.addSurvey(createPollState.value.title, createPollState.value.options)
                }
            }
        }
    }
}
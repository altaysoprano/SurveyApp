package com.example.surveyapp.presentation.poll_details

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.presentation.poll_details.components.DeleteSurveyDialog
import com.example.surveyapp.presentation.poll_details.components.PollDetailDropDownMenu
import com.example.surveyapp.presentation.poll_details.components.SurveyStateCard
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalPermissionsApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun PollDetailScreen(
    survey: Survey?,
    navController: NavController,
    viewModel: PollDetailViewModel = hiltViewModel()
) {

    val pollDetailState = viewModel.pollDetailState
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.snackbarFlow.collectLatest { event ->
            when (event) {
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
        backgroundColor = MaterialTheme.colors.surface,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "Survey Detail",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon =
                {
                    Box() {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    PollDetailDropDownMenu(
                        onDeleteItemClick = { viewModel.onDeleteItemClick() },
                        onShareItemClick = {
                            viewModel.viewModelScope.launch {
                                viewModel.onShare(
                                    context = context
                                )
                            }
                        },
                        isOwner = false
                    )
                }
            )
        }
    ) {
        DeleteSurveyDialog(deleteDialogState = pollDetailState.value.deleteDialogState,
            onDismiss = { viewModel.onDeleteDialogDismiss() },
            onConfirm = {}
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .align(TopCenter),
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
                                text = DateTimeUtil.formatSurveyDate(survey?.deadline),
                                modifier = Modifier
                                    .alpha(0.6f)
                                    .background(Color.White),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    survey?.let { survey ->
                        survey.options.forEach { totalVotes += it.numberOfVotes }

                        Surface(
                            elevation = 8.dp,
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .padding(vertical = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colors.background)
                            ) {
                                Text(
                                    text = "${survey?.title}", fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
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

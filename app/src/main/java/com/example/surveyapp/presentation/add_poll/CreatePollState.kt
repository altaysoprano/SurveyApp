package com.example.surveyapp.presentation.add_poll

import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.models.Survey

data class CreatePollState(
    val data: Survey? = null,
    val title: String = "",
    val options: List<Option> = listOf(Option(0, "", 0), Option(0,"", 0)),
    val isLoading: Boolean = false,
    val error: String = "",
    val isAdded: Boolean = false,
    val dialogState: Boolean = false,
    val isCheckBoxChecked: Boolean = false,
    val setTimeDialogState: Boolean = false,
    val surveyTime: SurveyTime = SurveyTime(1, 0, 0)
)
package com.example.surveyapp.presentation.poll_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class PollDetailViewModel @Inject constructor() : ViewModel() {

    private val _pollDetailState = mutableStateOf(PollDetailState())
    val pollDetailState = _pollDetailState

    fun onVoted() {
        _pollDetailState.value = _pollDetailState.value.copy(
            isVoted = true
        )
    }

}
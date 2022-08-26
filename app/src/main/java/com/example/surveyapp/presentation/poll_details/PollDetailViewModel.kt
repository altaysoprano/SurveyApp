package com.example.surveyapp.presentation.poll_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollDetailViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _pollDetailState = mutableStateOf(PollDetailState())
    val pollDetailState = _pollDetailState

    fun onVote(id: String, optionId: Int, options: List<Option>) = viewModelScope.launch {
        useCases.voteSurvey(id, optionId, options).collect { response ->
            when (response) {
                is Response.Loading -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isLoading = true
                    )
                }
                is Response.Success -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isVoted = true,
                        isLoading = false
                    )
                }
                is Response.Error -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}
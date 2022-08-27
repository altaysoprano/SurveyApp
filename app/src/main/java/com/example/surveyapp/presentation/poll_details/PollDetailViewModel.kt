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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollDetailViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _pollDetailState = mutableStateOf(PollDetailState())
    val pollDetailState = _pollDetailState

    private lateinit var auth: FirebaseAuth

    init {

    }

    fun onVote(id: String, optionId: Int, options: List<Option>) = viewModelScope.launch {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val emailName = currentUser?.email

        useCases.voteSurvey(emailName ?: "", id, optionId, options).collect { response ->
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
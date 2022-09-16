package com.example.surveyapp.presentation.poll_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PollDetailViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pollDetailState = mutableStateOf(PollDetailState())
    val pollDetailState = _pollDetailState

    private var auth: FirebaseAuth

    init {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val emailName = currentUser?.email

        // val surveyId = savedStateHandle.get<Survey>("survey")?.id
        val survey = savedStateHandle.get<Survey>("survey")
        val deadline = survey?.deadline

        checkIsSurveyOver(deadline ?: Date())
        getEmailById(emailName ?: "", survey?.id ?: "")
    }

    fun getEmailById(email: String, id: String) = viewModelScope.launch {
        useCases.getEmailById(email, id).collect { response ->
            when (response) {
                is Response.Loading -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isLoading = true
                    )
                }
                is Response.Success -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isLoading = false,
                        isVoted = true,
                        email = response.data
                    )
                }
                is Response.Error -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isLoading = false //BURADA VE ONVOTE FONKSİYONUNDA BAŞARISIZLIK DURUMLARINDA BİR ŞEYLER YAPTIRABİLİRSİN
                    )
                }
            }
        }
    }

    fun checkIsSurveyOver(date: Date) {
        val currentDate = Calendar.getInstance().time
        if(date < currentDate) {
            _pollDetailState.value = _pollDetailState.value.copy(
                isOver = true
            )
        }
    }

    fun onVote(id: String, optionId: Int, options: List<Option>, surveyTitle: String, deadline: Date) = viewModelScope.launch {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val emailName = currentUser?.email

        useCases.voteSurvey(emailName ?: "", id, optionId, options, surveyTitle, deadline).collect { response ->
            when (response) {
                is Response.Loading -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isLoading = true,
                        loadingText = "Vote is saving..."
                    )
                }
                is Response.Success -> {
                    _pollDetailState.value = _pollDetailState.value.copy(
                        isVoted = true,
                        isLoading = false,
                        email = response.data
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
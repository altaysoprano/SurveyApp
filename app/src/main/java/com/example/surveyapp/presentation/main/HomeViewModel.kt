package com.example.surveyapp.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    var surveysReference by mutableStateOf<Response<List<Survey>>>(Response.Loading)
        private set

    private val _surveySearchText = mutableStateOf("")
    val surveySearchText = _surveySearchText

    private val _searchSurveyState = mutableStateOf(SearchSurveyState())
    val searchSurveyState = _searchSurveyState

    private fun getSurveys() = viewModelScope.launch {
        useCases.getSurveys().collect { response ->
            surveysReference = response as Response<List<Survey>>
        }
    }

    fun getSurveyById(id: String) = viewModelScope.launch {
        if (id.isBlank()) {
            _searchSurveyState.value = _searchSurveyState.value.copy(
                data = null,
                error = "",
                isTextBlank = true
            )
        } else {
            useCases.getSurveyById(id).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _searchSurveyState.value = _searchSurveyState.value.copy(
                            isLoading = true,
                            data = null,
                            error = "",
                            isTextBlank = false
                        )
                    }
                    is Response.Success -> {
                        _searchSurveyState.value = _searchSurveyState.value.copy(
                            isLoading = false,
                            data = response.data
                        )
                    }
                    is Response.Error -> {
                        _searchSurveyState.value = _searchSurveyState.value.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }
            }
        }
    }

    fun onSearchSurvey(id: String) {
        getSurveyById(id)
    }

    fun onNavigatedToPollDetail() {
        _searchSurveyState.value = _searchSurveyState.value.copy(
            data = null
        )
    }

}
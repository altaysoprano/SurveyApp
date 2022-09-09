package com.example.surveyapp.presentation.all_surveys

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.domain.usecase.UseCases
import com.example.surveyapp.presentation.main.SearchSurveyState
import com.example.surveyapp.presentation.main.SurveyListState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllSurveysViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _allSurveysState = mutableStateOf(AllSurveysState())
    val allSurveysState = _allSurveysState

    private val _searchSurveyState = mutableStateOf(SearchSurveyState())
    val searchSurveyState = _searchSurveyState

    private lateinit var auth: FirebaseAuth

    init {
        val collectionName = savedStateHandle.get<String>("collectionName")
        getOwnedSurveys(collectionName ?: "")
    }

    fun getOwnedSurveys(collectionName: String) = viewModelScope.launch {

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val emailName = currentUser?.email

        useCases.getSurveys(emailName ?: "", collectionName).collect { response ->
            when(response) {
                is Response.Loading -> {
                    _allSurveysState.value = _allSurveysState.value.copy(
                        isLoading = true
                    )
                }
                is Response.Success -> {
                    _allSurveysState.value = _allSurveysState.value.copy(
                        isLoading = false,
                        data = response.data
                    )
                }
                is Response.Error -> {
                    _allSurveysState.value = _allSurveysState.value.copy(
                        isLoading = false,
                        error = response.message
                    )
                }
            }
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

    fun onNavigatedToPollDetail() {
        _searchSurveyState.value = _searchSurveyState.value.copy(
            data = null
        )
    }
}
package com.example.surveyapp.presentation.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.domain.usecase.UseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _surveySearchText = mutableStateOf("")
    val surveySearchText = _surveySearchText

    private val _searchSurveyState = mutableStateOf(SearchSurveyState())
    val searchSurveyState = _searchSurveyState

    private val _surveyListState = mutableStateOf(SurveyListState())
    val surveyListState = _surveyListState

    private var auth: FirebaseAuth = Firebase.auth
    val currentUser = auth.currentUser
    val emailName = currentUser?.email

    init {
        addUser()
        getOwnedSurveys(emailName ?: "")
        getVotedSurveys(emailName ?: "")
    }

    fun addUser() = viewModelScope.launch {

        useCases.addUser(emailName ?: "" ).collect { response ->
            when(response) {
                is Response.Loading -> {
                    Log.d("Mesaj: ", "User bakılıyor") //Buraya ayrıca bir loading statei sağlanabilir
                }
                is Response.Success -> {
                    Log.d("Mesaj: ", "User bulundu")
                }
                is Response.Error -> {
                    Log.d("Mesaj: ", "User işleminde sorun var!!!")
                }
            }
        }
    }

    fun getVotedSurveys(emailName: String) = viewModelScope.launch {
        useCases.getSurveys(emailName ?: "", "votedSurveys").collect { response ->
            when(response) {
                is Response.Loading -> {
                    _surveyListState.value = _surveyListState.value.copy(
                        isVotedSurveysLoading = true
                    )
                }
                is Response.Success -> {
                    _surveyListState.value = _surveyListState.value.copy(
                        isVotedSurveysLoading = false,
                        votedSurveysData = response.data
                    )
                }
                is Response.Error -> {
                    _surveyListState.value = _surveyListState.value.copy(
                        isVotedSurveysLoading = false,
                        votedSurveysError = response.message
                    )
                }
            }
        }
    }

    fun getOwnedSurveys(emailName: String) = viewModelScope.launch {
        useCases.getSurveys(emailName ?: "", "ownedSurveys").collect { response ->
            when(response) {
                is Response.Loading -> {
                    _surveyListState.value = _surveyListState.value.copy(
                        isOwnedSurveysLoading = true
                    )
                }
                is Response.Success -> {
                    _surveyListState.value = _surveyListState.value.copy(
                        isOwnedSurveysLoading = false,
                        ownedSurveysData = response.data
                    )
                }
                is Response.Error -> {
                    _surveyListState.value = _surveyListState.value.copy(
                        isOwnedSurveysLoading = false,
                        ownedSurveysError = response.message
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
            useCases.getSurveyById(id.trim()).collect { response ->
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
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

    var surveysReference by mutableStateOf<Response<List<Survey>>>(Response.Loading)
        private set

    private val _surveySearchText = mutableStateOf("")
    val surveySearchText = _surveySearchText

    private val _searchSurveyState = mutableStateOf(SearchSurveyState())
    val searchSurveyState = _searchSurveyState

    private lateinit var auth: FirebaseAuth

    init {
        addUser()
    }

    fun addUser() = viewModelScope.launch {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val emailName = currentUser?.email

        useCases.addUser(emailName ?: "" ).collect { response ->
            when(response) {
                is Response.Loading -> {
                    Log.d("Mesaj: ", "User bakılıyor")
                }
                is Response.Success -> {
                    Log.d("Mesaj: ", "User işlemi tamamlandı")
                }
                is Response.Error -> {
                    Log.d("Mesaj: ", "User işleminde sorun var!!!")
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

    fun onSearchSurvey(id: String) {
        getSurveyById(id)
    }

    fun onNavigatedToPollDetail() {
        _searchSurveyState.value = _searchSurveyState.value.copy(
            data = null
        )
    }

}
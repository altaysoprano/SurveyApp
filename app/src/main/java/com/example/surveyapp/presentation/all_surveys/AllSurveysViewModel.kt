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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
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

    private var allSurveys: MutableList<Survey> = mutableListOf()

    private lateinit var auth: FirebaseAuth

    private var collectionName = savedStateHandle.get<String>("collectionName")

    init {
        collectionName = savedStateHandle.get<String>("collectionName")
        getSurveys(collectionName ?: "")
    }

    fun getSurveys(collectionName: String) = viewModelScope.launch {

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
                    allSurveys = response.data
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

    fun onSearch(text: String) {
        if(text.isNotBlank()) {
            _allSurveysState.value = _allSurveysState.value.copy(
                text = text,
                data = allSurveys.filter { it.title.contains(text, ignoreCase = true) }
            )
        } else {
            _allSurveysState.value = _allSurveysState.value.copy(
                text = text,
                data = allSurveys
            )
        }
    }

    fun onNavigatedToPollDetail() {
        _searchSurveyState.value = _searchSurveyState.value.copy(
            data = null
        )
    }

/*
    fun onPaginate() = viewModelScope.launch {
        _allSurveysState.value = _allSurveysState.value.copy(
            limit = _allSurveysState.value.limit + 20
        )
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val emailName = currentUser?.email
        val limit = _allSurveysState.value.limit

        useCases.getSurveys(emailName ?: "", collectionName ?: "", limit).collect { response ->
            when(response) {
                is Response.Loading -> {
                    _allSurveysState.value = _allSurveysState.value.copy(
                        isPaginating = true
                    )
                }
                is Response.Success -> {
                    _allSurveysState.value = _allSurveysState.value.copy(
                        isPaginating = false,
                        data = response.data
                    )
                }
                is Response.Error -> {
                    _allSurveysState.value = _allSurveysState.value.copy(
                        isPaginating = false,
                        error = response.message //BURADA DA BİR HATA MESAJI OLABİLİR. SURVEYLER İLK BAŞTA YÜKLENİP SONRA İNTERNETİ KAPATIP BAK
                    )
                }
            }
        }
    }
*/
}
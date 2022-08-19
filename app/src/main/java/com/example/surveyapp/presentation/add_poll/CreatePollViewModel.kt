package com.example.surveyapp.presentation.add_poll

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.data.repository.FirebaseRepository
import com.example.surveyapp.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePollViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _createPollState = mutableStateOf(CreatePollState())
    val createPollState = _createPollState

    fun addSurvey(title: String, options: List<Option>) =
        viewModelScope.launch {
            useCases.addSurvey(title, options).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _createPollState.value = _createPollState.value.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Success -> {
                        _createPollState.value = _createPollState.value.copy(
                            data = response.data as Survey?,
                            isLoading = false,
                            dialogState = true
                        )
                    }
                    is Response.Error -> {
                        _createPollState.value = _createPollState.value.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }
            }
        }

    fun onTitleChanged(title: String) {
        _createPollState.value = _createPollState.value.copy(
            title = title
        )
    }

    fun addOption() {
        val newOptions = _createPollState.value.options.toMutableList()
        newOptions.add(Option("", 0, true))
        _createPollState.value = _createPollState.value.copy(
            options = newOptions
        )
    }

    fun deleteOption(option: Option) {
        val newOptions = _createPollState.value.options.toMutableList()
        newOptions.remove(option)
        _createPollState.value = _createPollState.value.copy(
            options = newOptions
        )
    }

    fun onOptionChanged(name: String, index: Int) {
        val newOptions = _createPollState.value.options.toMutableList()
        newOptions.set(index, Option(name, 0, isNewOption = index != 0 && index != 1))
        _createPollState.value = _createPollState.value.copy(
            options = newOptions
        )
    }

    fun onPollAdded() {
        _createPollState.value = _createPollState.value.copy(
            isAdded = false
        )
    }

    fun onAlertDialogDismiss() {
        _createPollState.value = _createPollState.value.copy(
            dialogState = false,
            isAdded = true
        )
    }
}
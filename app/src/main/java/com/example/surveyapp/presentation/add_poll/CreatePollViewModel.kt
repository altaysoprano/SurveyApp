package com.example.surveyapp.presentation.add_poll

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.BuildConfig
import com.example.surveyapp.common.Response
import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.models.Survey
import com.example.surveyapp.data.repository.FirebaseRepository
import com.example.surveyapp.domain.usecase.UseCases
import com.example.surveyapp.presentation.poll_details.SnackbarEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreatePollViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _createPollState = mutableStateOf(CreatePollState())
    val createPollState = _createPollState

    private lateinit var auth: FirebaseAuth

    fun addSurvey(title: String, options: List<Option>) =
        viewModelScope.launch {
            val isOwnVoteChecked = _createPollState.value.isCheckBoxChecked
            auth = Firebase.auth
            val currentUser = auth.currentUser
            val emailName = currentUser?.email
            val deadline = getDeadLine(_createPollState.value.surveyTime)

            useCases.addSurvey(emailName ?: "", isOwnVoteChecked, title, options, deadline).collect { response ->
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

    fun onCheckBoxChanged(isChecked: Boolean) {
        _createPollState.value = _createPollState.value.copy(
            isCheckBoxChecked = isChecked
        )
    }

    fun addOption() {
        val newOptions = _createPollState.value.options.toMutableList()
        newOptions.add(Option(0, "", 0, true))
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
        newOptions.set(index, Option(index, name, 0, isNewOption = index != 0 && index != 1))
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

    fun onSetTimeButtonPressed() {
        _createPollState.value = _createPollState.value.copy(
            setTimeDialogState = true
        )
    }

    fun onSetTimeDialogDismiss() {
        _createPollState.value = _createPollState.value.copy(
            setTimeDialogState = false
        )
    }

    fun onSetTimeDialogConfirm(day: Int, hour: Int, minute: Int) {
        val surveyTime = SurveyTime(day, hour, minute)
        _createPollState.value = _createPollState.value.copy(
            surveyTime = surveyTime,
            setTimeDialogState = false
        )
    }

    fun getDeadLine(surveyTime: SurveyTime): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, surveyTime.day)
        calendar.add(Calendar.HOUR, surveyTime.hour)
        calendar.add(Calendar.MINUTE, surveyTime.minute)
        val deadline = calendar.time
        return deadline
    }

    @ExperimentalPermissionsApi
    fun onShare(
        context: Context
    ) {
            val shareType = "text/plain"
            val surveyCode = _createPollState.value.data?.id
            val surveyTitle = _createPollState.value.data?.title
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "QUESTION: $surveyTitle\n\n" +
                        "Do you want to vote in this survey? SURVEY CODE: $surveyCode\n\n" +
                        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n")
                type = shareType
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share to: ")
            context.startActivity(shareIntent)
    }
}
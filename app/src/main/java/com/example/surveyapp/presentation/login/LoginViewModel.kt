package com.example.surveyapp.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.common.AuthenticationResource
import com.example.surveyapp.common.FirebaseAuthenticationResult
import com.example.surveyapp.domain.usecase.FirebaseAuthUseCase
import com.example.surveyapp.domain.usecase.UseCases
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCases: UseCases) :
    ViewModel() {

    private val _authenticationState = mutableStateOf(AuthState())
    val authenticationState = _authenticationState

    private lateinit var auth: FirebaseAuth

    var authState = mutableStateOf<FirebaseAuthenticationResult<AuthenticationResource>?>(null)
        private set

    init {
        checkSignedIn()
    }

    fun loginWithCredential(authCredential: AuthCredential) {
        viewModelScope.launch {
            useCases.firebaseAuthUseCase.invoke(authCredential).collect {

                when (it.authenticationState) {
                    AuthenticationResource.AUTHENTICATED -> {
                        _authenticationState.value = _authenticationState.value.copy(
                            isSignedIn = true,
                            isLoading = false
                        )
                    }

                    AuthenticationResource.UNAUTHENTICATED -> {
                        _authenticationState.value = _authenticationState.value.copy(
                            isSignedIn = false,
                            isLoading = false
                        )
                        println(authState.value?.firebaseException?.localizedMessage)
                    }

                    AuthenticationResource.IN_PROGRESS -> {
                        _authenticationState.value = _authenticationState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun checkSignedIn() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _authenticationState.value = _authenticationState.value.copy(
                isSignedIn = true,
                isLoading = false
            )
        }
    }
}

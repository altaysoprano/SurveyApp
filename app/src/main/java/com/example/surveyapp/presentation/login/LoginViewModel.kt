package com.example.surveyapp.presentation.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.common.AuthenticationResource
import com.example.surveyapp.common.FirebaseAuthenticationResult
import com.example.surveyapp.domain.FirebaseAuthUseCase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseAuthUseCase: FirebaseAuthUseCase) :
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
            firebaseAuthUseCase.invoke(authCredential).collect {

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
            Log.d("Mesaj: ", "current user dolu")
            _authenticationState.value = _authenticationState.value.copy(
                isSignedIn = true,
                isLoading = false
            )
        }
    }
}

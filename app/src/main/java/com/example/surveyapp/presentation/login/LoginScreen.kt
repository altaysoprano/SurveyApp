package com.example.surveyapp.presentation.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.R
import com.example.surveyapp.common.AuthenticationResource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@ExperimentalMaterialApi
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<LoginViewModel>()
    val authState = viewModel.authenticationState

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Mesaj: ", account.email + "-" + account.displayName)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.loginWithCredential(credential)
            } catch (e: ApiException) {
                Log.w("Error", "Google sign in failed", e)
            }
        }

    LaunchedEffect(key1 = authState.value.isSignedIn) {
        if(authState.value.isSignedIn) {
            navController.navigate(context.getString(R.string.main_screen)) {
                popUpTo(context.getString(R.string.login_screen)) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GoogleSignInButton(
                text = "Sign In with Google",
                loadingText = "Signing In...",
                isLoading = authState.value.isLoading
            ) {
                googleLogin(context, launcher)
            }
        }
    }
}

private fun googleLogin(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    lateinit var googleSignInClient: GoogleSignInClient

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id_X))
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(context, gso)

    val signInIntent = googleSignInClient.signInIntent

    launcher.launch(signInIntent)

}

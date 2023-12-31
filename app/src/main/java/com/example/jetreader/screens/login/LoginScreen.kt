package com.example.jetreader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.example.jetreader.R

import com.example.jetreader.components.EmailInput
import com.example.jetreader.components.PasswordInput
import com.example.jetreader.components.ReaderLogo
import com.example.jetreader.navigation.ReaderScreens

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showLoginForm = rememberSaveable{
        mutableStateOf(true)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            ReaderLogo()
            if(showLoginForm.value){
                AuthForm(){email,password ->
                    viewModel.signIn(email, password = password){
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                }
            }else{
                AuthForm(loading = false,isCreateAccount = true){email,password ->

                }
            }

        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier.height(15.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            val text = if(showLoginForm.value) "Sign up" else "Login"
            Text(text = "New User?")
            Text(text = text, modifier = Modifier
                .clickable {
                    showLoginForm.value = !showLoginForm.value
                }
                .padding(start = 5.dp),
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthForm(loading: Boolean = false, isCreateAccount: Boolean = false, onDone: (String,String) -> Unit = {email,password ->}){
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController  = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(
            rememberScrollState()
        )
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if(isCreateAccount){
            Text(text = stringResource(id = R.string.create_acct), modifier = Modifier.padding(4.dp))
        }else{
            Text(text = "", modifier = Modifier.padding(4.dp))
        }
        EmailInput(emailState = email, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        }, enabled = !loading)
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
            }
        )
        SubmitButton(
            textId = if(isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean, onClick: () -> Unit = {}) {
   Button( onClick = onClick,
       modifier = Modifier
           .fillMaxWidth()
           .padding(3.dp),
       enabled = !loading && validInputs,
       shape = CircleShape
       ) {
       if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
       else Text(text = textId, modifier = Modifier.padding(5.dp),)
       
   }
}




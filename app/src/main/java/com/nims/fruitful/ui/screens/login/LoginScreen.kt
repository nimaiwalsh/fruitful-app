package com.nims.fruitful.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nims.fruitful.ui.common.composable.*
import com.nims.fruitful.ui.common.ext.basicButton
import com.nims.fruitful.ui.common.ext.fieldModifier
import com.nims.fruitful.ui.common.ext.textButton
import com.nims.fruitful.R.string as AppText

@Composable
fun LoginScreen(openAndPopUp: (String, String) -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    BasicToolbar(AppText.login_details)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton(AppText.sign_in, Modifier.basicButton()) {
            viewModel.onSignInClick(openAndPopUp)
        }

        BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
            viewModel.onForgotPasswordClick()
        }
    }
}
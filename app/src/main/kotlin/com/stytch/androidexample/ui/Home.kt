package com.stytch.androidexample.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stytch.androidexample.R
import com.stytch.androidexample.ui.elements.DividerWithText
import com.stytch.androidexample.ui.elements.PageTitle
import com.stytch.androidexample.ui.elements.PhoneEntry
import com.stytch.androidexample.ui.elements.SocialLoginButton

@Composable
fun Home(
    homeViewModel: HomeViewModel = viewModel(),
    onOTPSent: (OTPResult) -> Unit,
    onGoogleAuthenticated: () -> Unit,
) {
    val context = LocalContext.current as ComponentActivity
    val (errorMessage, setErrorMessage) = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(homeViewModel.otpSentTo) {
        homeViewModel.otpSentTo?.let {
            onOTPSent(it)
        }
    }
    Column {
        PageTitle(text = stringResource(id = R.string.home_title))
        SocialLoginButton(
            modifier = Modifier.padding(bottom = 12.dp),
            onClick = { homeViewModel.startGoogleOAuth(context, onGoogleAuthenticated, setErrorMessage) },
            iconDrawable = painterResource(id = R.drawable.google),
            iconDescription = stringResource(id = R.string.google_logo),
            text = stringResource(id = R.string.continue_with_google),
        )
        SocialLoginButton(
            modifier = Modifier.padding(bottom = 24.dp),
            onClick = { homeViewModel.startAppleOAuth(context) },
            iconDrawable = painterResource(id = R.drawable.apple),
            iconDescription = stringResource(id = R.string.apple_logo),
            text = stringResource(id = R.string.continue_with_apple),
        )
        DividerWithText(
            modifier = Modifier.padding(bottom = 24.dp),
            text = stringResource(id = R.string.home_divider_text),
        )
        PhoneEntry(
            countryCode = homeViewModel.countryCodeTextState,
            onCountryCodeChanged = homeViewModel::onCountryCodeChanged,
            phoneNumber = homeViewModel.phoneNumberTextState,
            onPhoneNumberChanged = homeViewModel::onPhoneNumberChanged,
            onPhoneNumberSubmit = homeViewModel::sendOTPBySMS,
            phoneNumberError = homeViewModel.phoneNumberError,
        )
        if (errorMessage != null) {
            Text(text = errorMessage)
        }
    }
}

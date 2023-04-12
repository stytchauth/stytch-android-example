package com.stytch.androidexample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stytch.androidexample.R
import com.stytch.androidexample.ui.elements.PageTitle
import com.stytch.androidexample.ui.theme.Cement
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun OTP(
    otpViewModel: OTPViewModel = viewModel(),
    methodId: String,
    phoneNumber: String,
    onBack: () -> Unit,
    onAuthenticated: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var otp by remember { mutableStateOf("") }
    val state = otpViewModel.otpState.collectAsState()
    LaunchedEffect(state.value) {
        (state.value as? OTPState.Authenticated)?.let {
            onAuthenticated()
        }
    }
    LaunchedEffect(otp) {
        if (otp.length == 6) {
            focusManager.clearFocus()
            otpViewModel.authenticate(otp, methodId)
        }
    }
    Column {
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .clickable { onBack() },
        )
        PageTitle(
            text = stringResource(id = R.string.otp_title),
            textAlign = TextAlign.Start,
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.otp_body))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(" $phoneNumber")
                }
                append(".")
            },
            style = Typography.body1.copy(textAlign = TextAlign.Start),
            modifier = Modifier.padding(bottom = 32.dp),
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Cement,
                unfocusedBorderColor = Cement,
                backgroundColor = Color.White,
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            value = otp,
            onValueChange = {
                if (it.length <= 6) otp = it
            },
            textStyle = Typography.body1,
            isError = state.value is OTPState.Error,
        )
        if (state.value is OTPState.Error) {
            Text(
                text = (state.value as OTPState.Error).reason,
                style = Typography.body2,
            )
        }
    }
}

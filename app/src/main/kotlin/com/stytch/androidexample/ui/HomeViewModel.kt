package com.stytch.androidexample.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.stytch.androidexample.BuildConfig
import com.stytch.androidexample.GOOGLE_OAUTH_REQUEST
import com.stytch.androidexample.THIRD_PARTY_OAUTH_REQUEST
import com.stytch.sdk.common.StytchResult
import com.stytch.sdk.consumer.StytchClient
import com.stytch.sdk.consumer.oauth.OAuth
import com.stytch.sdk.consumer.otp.OTP
import kotlinx.coroutines.launch

data class OTPResult(
    val phoneNumber: String,
    val methodId: String,
)
class HomeViewModel : ViewModel() {
    var otpSentTo by mutableStateOf<OTPResult?>(null)
    var countryCodeTextState by mutableStateOf(TextFieldValue("1"))
    var phoneNumberTextState by mutableStateOf(TextFieldValue(""))
    var phoneNumberError by mutableStateOf("")

    fun onCountryCodeChanged(value: TextFieldValue) {
        countryCodeTextState = value
        phoneNumberError = ""
    }

    fun onPhoneNumberChanged(value: TextFieldValue) {
        phoneNumberTextState = value
        phoneNumberError = ""
    }

    fun sendOTPBySMS() {
        val phoneNumber = PhoneNumber().apply {
            countryCode = countryCodeTextState.text.toInt()
            nationalNumber = phoneNumberTextState.text.toLong()
        }
        val e164 = PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
        viewModelScope.launch {
            when (val result = StytchClient.otps.sms.loginOrCreate(OTP.SmsOTP.Parameters(phoneNumber = e164))) {
                is StytchResult.Success -> {
                    otpSentTo = OTPResult(
                        phoneNumber = PhoneNumberUtil.getInstance().format(
                            phoneNumber,
                            PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL,
                        ),
                        methodId = result.value.methodId,
                    )
                }
                is StytchResult.Error -> {
                    phoneNumberError = result.exception.message ?: "Invalid number, please try again."
                }
            }
        }
    }

    fun startGoogleOAuth(context: ComponentActivity) {
        viewModelScope.launch {
            val didStartOneTap = StytchClient.oauth.googleOneTap.start(
                OAuth.GoogleOneTap.StartParameters(
                    context = context,
                    clientId = BuildConfig.GOOGLE_OAUTH_CLIENT_ID,
                    oAuthRequestIdentifier = GOOGLE_OAUTH_REQUEST,
                ),
            )
            if (!didStartOneTap) {
                // Google OneTap is unavailable, fallback to traditional OAuth
                StytchClient.oauth.google.start(
                    OAuth.ThirdParty.StartParameters(
                        context = context,
                        oAuthRequestIdentifier = THIRD_PARTY_OAUTH_REQUEST,
                        loginRedirectUrl = "stytch-example://oauth",
                        signupRedirectUrl = "stytch-example://oauth",
                    ),
                )
            }
        }
    }

    fun startAppleOAuth(context: ComponentActivity) {
        StytchClient.oauth.apple.start(
            OAuth.ThirdParty.StartParameters(
                context = context,
                oAuthRequestIdentifier = THIRD_PARTY_OAUTH_REQUEST,
                loginRedirectUrl = "stytch-example://oauth",
                signupRedirectUrl = "stytch-example://oauth",
            ),
        )
    }
}

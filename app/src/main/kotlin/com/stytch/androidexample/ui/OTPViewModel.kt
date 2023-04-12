package com.stytch.androidexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stytch.sdk.common.StytchResult
import com.stytch.sdk.consumer.StytchClient
import com.stytch.sdk.consumer.otp.OTP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface OTPState {
    object Idle : OTPState
    object Authenticated : OTPState
    data class Error(val reason: String) : OTPState
}

class OTPViewModel : ViewModel() {
    private val _otpState = MutableStateFlow<OTPState>(OTPState.Idle)
    val otpState: StateFlow<OTPState>
        get() = _otpState

    fun authenticate(token: String, methodId: String) {
        viewModelScope.launch {
            val params = OTP.AuthParameters(token = token, methodId = methodId)
            when (StytchClient.otps.authenticate(params)) {
                is StytchResult.Success -> _otpState.value = OTPState.Authenticated
                is StytchResult.Error -> _otpState.value = OTPState.Error("Invalid passcode, please try again.")
            }
        }
    }
}

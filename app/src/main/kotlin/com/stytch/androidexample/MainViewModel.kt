package com.stytch.androidexample

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stytch.sdk.common.DeeplinkHandledStatus
import com.stytch.sdk.common.StytchResult
import com.stytch.sdk.common.sso.SSOError
import com.stytch.sdk.consumer.StytchClient
import com.stytch.sdk.consumer.oauth.OAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface NavigationState {
    object Idle : NavigationState
    data class Navigate(val route: String) : NavigationState
}

class MainViewModel : ViewModel() {
    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val navigationState: StateFlow<NavigationState>
        get() = _navigationState

    private val _authenticationError = MutableStateFlow<String?>(null)
    val authenticationError: StateFlow<String?>
        get() = _authenticationError

    fun navigateTo(route: String) {
        _navigationState.value = NavigationState.Navigate(route)
    }

    fun authenticateGoogleOneTapLogin(data: Intent) {
        viewModelScope.launch {
            val parameters = OAuth.GoogleOneTap.AuthenticateParameters(data)
            when (val result = StytchClient.oauth.googleOneTap.authenticate(parameters)) {
                is StytchResult.Success -> {
                    _authenticationError.value = null
                    _navigationState.value = NavigationState.Navigate("profile")
                }
                is StytchResult.Error -> _authenticationError.value = result.exception.reason.toString()
            }
        }
    }

    fun authenticateThirdPartyOAuth(resultCode: Int, intent: Intent) {
        viewModelScope.launch {
            if (resultCode == Activity.RESULT_OK) {
                intent.data?.let {
                    when (val result = StytchClient.handle(it, 60U)) {
                        is DeeplinkHandledStatus.NotHandled -> _authenticationError.value = result.reason
                        is DeeplinkHandledStatus.Handled -> {
                            _authenticationError.value = null
                            _navigationState.value = NavigationState.Navigate("profile")
                        }
                        // This only happens for password reset deeplinks
                        is DeeplinkHandledStatus.ManualHandlingRequired -> {}
                    }
                }
            } else {
                intent.extras?.getSerializable(SSOError.SSO_EXCEPTION)?.let {
                    when (it as SSOError) {
                        is SSOError.UserCanceled -> {
                            _authenticationError.value = null
                        } // do nothing
                        is SSOError.NoBrowserFound,
                        is SSOError.NoURIFound,
                        -> _authenticationError.value = it.message
                    }
                }
            }
        }
    }
}

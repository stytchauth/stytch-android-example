package com.stytch.androidexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stytch.sdk.common.StytchExceptions
import com.stytch.sdk.common.StytchResult
import com.stytch.sdk.consumer.StytchClient
import com.stytch.sdk.consumer.network.models.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ProfileState {
    object Loading : ProfileState
    object LoggedOut : ProfileState
    data class Loaded(val profile: UserData) : ProfileState
    data class Error(val error: StytchExceptions) : ProfileState
}

class ProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profile: StateFlow<ProfileState>
        get() = _profile

    init {
        viewModelScope.launch {
            when (val resp = StytchClient.user.getUser()) {
                is StytchResult.Success -> _profile.value = ProfileState.Loaded(resp.value)
                is StytchResult.Error -> _profile.value = ProfileState.Error(resp.exception)
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            StytchClient.sessions.revoke()
            _profile.value = ProfileState.LoggedOut
        }
    }
}

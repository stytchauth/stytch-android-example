package com.stytch.androidexample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stytch.androidexample.R
import com.stytch.androidexample.ui.elements.PageTitle
import com.stytch.androidexample.ui.elements.ProfileDataCard
import com.stytch.androidexample.ui.elements.StytchButton
import com.stytch.androidexample.ui.theme.Typography
import com.stytch.sdk.consumer.network.models.UserData

@Composable
fun Profile(
    profileViewModel: ProfileViewModel = viewModel(),
    onLoggedOut: () -> Unit,
) {
    val profile = profileViewModel.profile.collectAsState()
    LaunchedEffect(profile.value) {
        if (profile.value == ProfileState.LoggedOut) {
            onLoggedOut()
        }
    }
    Column {
        PageTitle(text = stringResource(id = R.string.profile_title))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .verticalScroll(rememberScrollState()),
        ) {
            (profile.value as? ProfileState.Loaded)?.let {
                ProfileData(profile = it.profile)
            }
            (profile.value as? ProfileState.Error)?.let {
                Text(
                    text = it.error.toString() ?: "Unknown error occurred",
                    style = Typography.body2,
                )
            }
        }
        StytchButton(
            text = stringResource(id = R.string.button_logout),
            onClick = profileViewModel::logout,
            enabled = true,
        )
    }
}

@Composable
fun ProfileData(
    profile: UserData,
) {
    Column {
        ProfileDataCard(
            icon = painterResource(id = R.drawable.badge),
            title = stringResource(id = R.string.profile_user_id),
            body = profile.userId,
        )
        if (
            !profile.name.firstName.isNullOrBlank() ||
            !profile.name.middleName.isNullOrBlank() ||
            !profile.name.lastName.isNullOrBlank()
        ) {
            ProfileDataCard(
                icon = painterResource(id = R.drawable.person),
                title = stringResource(id = R.string.profile_name),
                body = "${profile.name.firstName} ${profile.name.middleName} ${profile.name.lastName}",
            )
        }
        if (profile.emails.isNotEmpty()) {
            profile.emails.forEach {
                ProfileDataCard(
                    icon = painterResource(id = R.drawable.email),
                    title = stringResource(id = R.string.profile_email),
                    body = it.email,
                )
                ProfileDataCard(
                    icon = painterResource(id = R.drawable.contact_mail),
                    title = stringResource(id = R.string.profile_email_id),
                    body = it.emailId,
                )
            }
        }
        if (profile.phoneNumbers.isNotEmpty()) {
            profile.phoneNumbers.forEach {
                ProfileDataCard(
                    icon = painterResource(id = R.drawable.numbers),
                    title = stringResource(id = R.string.profile_phone_number),
                    body = it.phoneNumber,
                )
                ProfileDataCard(
                    icon = painterResource(id = R.drawable.textsms),
                    title = stringResource(id = R.string.profile_phone_id),
                    body = it.id,
                )
            }
        }
        if (profile.providers.isNotEmpty()) {
            profile.providers.forEach {
                ProfileDataCard(
                    icon = painterResource(id = R.drawable.dns),
                    title = stringResource(id = R.string.profile_oauth_provider),
                    body = it.providerType,
                )
                ProfileDataCard(
                    icon = painterResource(id = R.drawable.dns),
                    title = stringResource(id = R.string.profile_oauth_subject),
                    body = it.providerSubject,
                )
            }
        }
    }
}

package com.stytch.androidexample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stytch.androidexample.NavigationState
import com.stytch.androidexample.R
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun ExampleApp(
    navController: NavHostController,
    navigationState: State<NavigationState>,
    authenticationErrors: State<String?>,
    navigateTo: (String) -> Unit,
) {
    LaunchedEffect(navigationState.value) {
        (navigationState.value as? NavigationState.Navigate)?.let {
            navController.navigate(it.route)
        }
    }
    Column {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(start = 32.dp, top = 64.dp, end = 32.dp, bottom = 24.dp),
        ) {
            composable("home") {
                Home(
                    onOTPSent = { navigateTo("otp/${it.methodId}/${it.phoneNumber}") },
                    onGoogleAuthenticated = { navigateTo("profile") }
                )
            }
            composable("otp/{methodId}/{phoneNumber}") {
                OTP(
                    methodId = it.arguments?.getString("methodId") ?: "",
                    phoneNumber = it.arguments?.getString("phoneNumber") ?: "",
                    onBack = { navigateTo("home") },
                    onAuthenticated = { navigateTo("profile") },
                )
            }
            composable("profile") {
                Profile(
                    onLoggedOut = { navigateTo("home") },
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.powered_by_stytch),
                contentDescription = stringResource(id = R.string.powered_by_stytch),
            )
        }
        authenticationErrors.value?.let {
            Text(
                text = it,
                style = Typography.body2,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

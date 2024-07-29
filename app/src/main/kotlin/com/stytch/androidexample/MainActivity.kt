package com.stytch.androidexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.stytch.androidexample.ui.ExampleApp
import com.stytch.androidexample.ui.theme.StytchAndroidExampleTheme

const val THIRD_PARTY_OAUTH_REQUEST = 4

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navigationState = viewModel.navigationState.collectAsState()
            val authenticationErrors = viewModel.authenticationError.collectAsState()
            StytchAndroidExampleTheme {
                ExampleApp(navController, navigationState, authenticationErrors, viewModel::navigateTo)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            THIRD_PARTY_OAUTH_REQUEST -> intent?.let { viewModel.authenticateThirdPartyOAuth(resultCode, it) }
        }
    }
}

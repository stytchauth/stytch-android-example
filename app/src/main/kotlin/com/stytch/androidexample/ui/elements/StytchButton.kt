package com.stytch.androidexample.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.stytch.androidexample.ui.theme.Chalk
import com.stytch.androidexample.ui.theme.Charcoal
import com.stytch.androidexample.ui.theme.Disabled
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun StytchButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = { },
    enabled: Boolean,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Charcoal,
            disabledBackgroundColor = Chalk,
        ),
    ) {
        Text(
            text = text,
            color = if (enabled) Color.White else Disabled,
            style = Typography.button,
        )
    }
}

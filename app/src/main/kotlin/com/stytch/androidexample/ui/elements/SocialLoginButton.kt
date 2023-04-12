package com.stytch.androidexample.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stytch.androidexample.ui.theme.Charcoal
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun SocialLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconDrawable: Painter,
    iconDescription: String,
    text: String,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Charcoal),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(45.dp),
        ) {
            Image(
                painter = iconDrawable,
                contentDescription = iconDescription,
                modifier = Modifier.width(24.dp).padding(end = 4.dp),
            )
            Text(
                text = text,
                style = Typography.button.copy(lineHeight = 45.sp),
            )
        }
    }
}

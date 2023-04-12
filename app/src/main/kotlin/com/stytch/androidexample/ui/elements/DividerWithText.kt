package com.stytch.androidexample.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stytch.androidexample.ui.theme.Disabled
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun DividerWithText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(1f).height(25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Divider(color = Disabled, thickness = 1.dp, modifier = Modifier.weight(1f))
        Text(
            text = text,
            modifier = Modifier.weight(0.5f),
            style = Typography.body1,
        )
        Divider(color = Disabled, thickness = 1.dp, modifier = Modifier.weight(1f))
    }
}

package com.stytch.androidexample.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun PageTitle(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        style = Typography.h1.copy(textAlign = textAlign),
        modifier = modifier.fillMaxWidth().padding(bottom = 24.dp),
    )
}

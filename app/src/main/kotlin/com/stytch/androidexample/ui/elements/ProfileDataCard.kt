package com.stytch.androidexample.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.stytch.androidexample.ui.theme.Cement
import com.stytch.androidexample.ui.theme.Typography

@Composable
fun ProfileDataCard(
    icon: Painter,
    title: String,
    body: String,
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
        Row(modifier = Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = icon, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text(text = title, style = Typography.h2)
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Cement,
                unfocusedBorderColor = Cement,
                backgroundColor = Color.White,
            ),
            textStyle = Typography.caption,
            value = body,
            onValueChange = {},
            readOnly = true,
        )
    }
}

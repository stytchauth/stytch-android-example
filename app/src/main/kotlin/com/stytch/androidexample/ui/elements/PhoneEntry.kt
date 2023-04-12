package com.stytch.androidexample.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.stytch.androidexample.R
import com.stytch.androidexample.ui.theme.Cement
import com.stytch.androidexample.ui.theme.Charcoal
import com.stytch.androidexample.ui.theme.Danger
import com.stytch.androidexample.ui.theme.Disabled
import com.stytch.androidexample.ui.theme.Typography
import com.stytch.androidexample.utils.PhoneNumberVisualTransformation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneEntry(
    countryCode: TextFieldValue,
    onCountryCodeChanged: (TextFieldValue) -> Unit,
    phoneNumber: TextFieldValue,
    onPhoneNumberChanged: (TextFieldValue) -> Unit,
    phoneNumberError: String = "",
    onPhoneNumberSubmit: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isError = phoneNumberError.isNotBlank()
    val countryCodes = PhoneNumberUtil.getInstance().supportedCallingCodes.toList().sorted()
    val region = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(countryCode.text.toInt())
    val exampleNumber = PhoneNumberUtil.getInstance().getExampleNumber(region)?.nationalNumber?.toString() ?: ""
    val maxPhoneLengthForRegion = exampleNumber.length
    var expanded by remember { mutableStateOf(false) }
    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.33f),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = "+${countryCode.text}",
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Cement,
                        unfocusedBorderColor = Cement,
                        backgroundColor = Color.White,
                    ),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                ) {
                    countryCodes.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                onCountryCodeChanged(TextFieldValue(selectionOption.toString()))
                                expanded = false
                            },
                        ) {
                            Text(
                                text = "+$selectionOption",
                                style = Typography.body1.copy(
                                    textAlign = TextAlign.Start,
                                    color = Disabled,
                                ),
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    errorBorderColor = Danger,
                    errorCursorColor = Danger,
                    focusedBorderColor = Cement,
                    unfocusedBorderColor = Cement,
                    backgroundColor = Color.White,
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                ),
                value = phoneNumber,
                onValueChange = {
                    if (it.text.length <= maxPhoneLengthForRegion) onPhoneNumberChanged(it)
                },
                placeholder = {
                    Text(
                        text = "(123) 456-7890",
                        style = Typography.body1,
                        color = Disabled,
                    )
                },
                visualTransformation = PhoneNumberVisualTransformation(region),
                textStyle = Typography.body1.copy(
                    textAlign = TextAlign.Start,
                    color = if (isError) Charcoal else Disabled,
                ),
                isError = isError,
            )
        }
        StytchButton(
            onClick = onPhoneNumberSubmit,
            modifier = Modifier.height(45.dp),
            text = stringResource(id = R.string.button_continue),
            enabled = phoneNumber.text.length >= maxPhoneLengthForRegion,
        )
    }
}

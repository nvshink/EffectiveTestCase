package com.nvshink.effectivetestcase.ui.screen.login


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.nvshink.effectivetestcase.R
import com.nvshink.effectivetestcase.ui.components.MyTextField
import com.nvshink.effectivetestcase.ui.event.ProfileEvent
import com.nvshink.effectivetestcase.ui.states.ProfileUIState

@Composable
fun LogInScreen(
    uiState: ProfileUIState,
    onLogInClick: (() -> Unit)? = null,
    onRegistrationClick: (() -> Unit)? = null,
    onForgotPasswordClick: (() -> Unit)? = null,
    onEvent: (ProfileEvent) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.log_in_screen_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(24.dp))
            MyTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = { it: String ->
                    onEvent(ProfileEvent.SetEmail(it))
                },
                placeholder = stringResource(R.string.email_placeholder),
                label = stringResource(R.string.label_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.isErrorLogIn
            )
            Spacer(Modifier.size(16.dp))
            MyTextField(
                value = uiState.password,
                onValueChange = { it: String ->
                    onEvent(ProfileEvent.SetPassword(it))
                },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = stringResource(R.string.password_placeholder),
                label = stringResource(R.string.label_password),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.isErrorLogIn
            )
            Spacer(Modifier.size(24.dp))
            Button(
                onClick = onLogInClick ?: {},
                enabled = uiState.email.isNotEmpty() && uiState.password.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth().height(48.dp)
            ) {
                Text(text = stringResource(R.string.button_log_in_name))
            }
            Spacer(Modifier.size(16.dp))
            val annotatedString = buildAnnotatedString {
                append(stringResource(R.string.log_in_screen_no_profile_question))
                append(" ")
                pushStringAnnotation(tag = "REGISTER", annotation = "register")
                withStyle(style = SpanStyle(color = if (onRegistrationClick == null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary)) {
                    append(stringResource(R.string.text_button_registration_name))
                }
                append("\n")
                pushStringAnnotation(tag = "FORGOT_PASSWORD", annotation = "forgot_password")
                withStyle(style = SpanStyle(color = if (onForgotPasswordClick == null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary)) {
                    append(stringResource(R.string.text_button_forgot_password_name))
                }
                pop()
            }
            var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
            Text(
                text = annotatedString,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures { offsetPosition ->
                            textLayoutResult?.let { layoutResult ->
                                val position = layoutResult.getOffsetForPosition(offsetPosition)
                                annotatedString.getStringAnnotations(
                                    tag = "URL",
                                    start = position,
                                    end = position
                                )
                                    .firstOrNull()?.let { annotation ->
                                        when (annotation.item) {
                                            "registration" -> onRegistrationClick
                                            "forgot_password" -> onForgotPasswordClick
                                        }
                                    }
                            }
                        }
                    })
            HorizontalDivider(modifier = Modifier.padding(vertical = 32.dp))
            Row {
                val uriHandler = LocalUriHandler.current
                Button(
                    onClick = {
                        uriHandler.openUri("https://vk.com/")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2683ED)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .height(46.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.vk_logo),
                        contentDescription = stringResource(R.string.vk_logo_description),
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        uriHandler.openUri("https://ok.ru/")
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .height(46.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFF98509),
                                    Color(0xFFF95D00)
                                )
                            )
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ok_logo),
                        contentDescription = stringResource(R.string.ok_logo_description),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

        }
    }
}
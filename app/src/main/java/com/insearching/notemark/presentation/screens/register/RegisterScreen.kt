package com.insearching.notemark.presentation.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.notemark.R
import com.insearching.notemark.core.ScreenSizesPreview
import com.insearching.notemark.core.extension.applyIf
import com.insearching.notemark.presentation.components.NoteMarkClickableText
import com.insearching.notemark.presentation.components.NoteMarkPrimaryButton
import com.insearching.notemark.presentation.components.NoteMarkTextField
import com.insearching.notemark.ui.theme.LocalScreenOrientation
import com.insearching.notemark.ui.theme.NoteMarkTheme
import com.insearching.notemark.ui.theme.ScreenOrientation
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>(),
    onLoginClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val actionLabel = stringResource(R.string.dismiss)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                RegisterEvent.OnLoginEvent -> onLoginClick()
                RegisterEvent.OnRegisterSuccess -> onLoginClick()
                is RegisterEvent.OnRegisterFailed -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        RegisterScreen(
            modifier = modifier,
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    state: RegisterViewState,
    onAction: (RegisterAction) -> Unit,
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(NoteMarkTheme.color.primary)
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            when (LocalScreenOrientation.current) {
                ScreenOrientation.PhonePortrait -> PhonePortraitRegisterScreen(
                    state = state,
                    onAction = onAction
                )

                ScreenOrientation.TabletPortrait -> TabletPortraitRegisterScreen(
                    state = state,
                    onAction = onAction,
                )

                ScreenOrientation.Landscape -> LandscapeRegisterScreen(
                    state = state,
                    onAction = onAction,
                )
            }
        }
    }
}

@Composable
private fun PhonePortraitRegisterScreen(
    state: RegisterViewState,
    onAction: (RegisterAction) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = NoteMarkTheme.color.surfaceLowest)
            .padding(NoteMarkTheme.offset.medium)
    ) {
        Title()
        Spacer(Modifier.height(NoteMarkTheme.offset.average))
        RegisterForm(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
private fun LandscapeRegisterScreen(
    state: RegisterViewState,
    onAction: (RegisterAction) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = NoteMarkTheme.color.surfaceLowest)
            .padding(NoteMarkTheme.offset.medium)
    ) {
        Title(
            modifier = Modifier
                .weight(1f)
                .padding(start = NoteMarkTheme.offset.great)
        )
        Spacer(Modifier.width(NoteMarkTheme.offset.average))
        RegisterForm(
            modifier = Modifier.weight(1f),
            state = state,
            onAction = onAction,
            scrollable = true
        )
    }
}

@Composable
private fun TabletPortraitRegisterScreen(
    state: RegisterViewState,
    onAction: (RegisterAction) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = NoteMarkTheme.color.surfaceLowest)
            .padding(NoteMarkTheme.offset.medium)
    ) {
        Title(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = NoteMarkTheme.offset.great,
                    top = NoteMarkTheme.offset.large,
                )
        )
        Spacer(modifier = Modifier.width(NoteMarkTheme.offset.average))
        RegisterForm(
            modifier = Modifier.weight(1f),
            state = state,
            onAction = onAction
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
    ) {
        Text(
            text = stringResource(R.string.create_account),
            style = NoteMarkTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.capture_your_thoughts_and_ideas),
            style = NoteMarkTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun RegisterForm(
    modifier: Modifier = Modifier,
    state: RegisterViewState,
    onAction: (RegisterAction) -> Unit,
    scrollable: Boolean = false,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .applyIf(scrollable) {
                verticalScroll(scrollState)
            },
        verticalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.username.value,
            label = stringResource(R.string.username),
            placeholder = "John.doe",
            supportText = stringResource(R.string.username_support_text),
            errorText = state.username.error?.asString(),
            onValueChange = { onAction(RegisterAction.OnUserNameChanged(it)) },
        )
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email.value,
            label = stringResource(R.string.email),
            placeholder = "john.doe@example.com",
            errorText = state.email.error?.asString(),
            onValueChange = { onAction(RegisterAction.OnEmailChanged(it)) },
        )
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password.value,
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            supportText = stringResource(R.string.password_support_text),
            errorText = state.password.error?.asString(),
            isPassword = true,
            onValueChange = { onAction(RegisterAction.OnPassChanged(it)) },
        )
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.repeatPassword.value,
            label = stringResource(R.string.repeat_password),
            placeholder = stringResource(R.string.password),
            errorText = state.repeatPassword.error?.asString(),
            isPassword = true,
            onValueChange = { onAction(RegisterAction.OnPassRepeatChanged(it)) },
        )
        NoteMarkPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_account),
            enabled = state.registerEnabled,
            onClick = { onAction(RegisterAction.OnCreateAccount) },
        )
        Spacer(modifier = Modifier.height(NoteMarkTheme.offset.medium))
        NoteMarkClickableText(
            text = stringResource(R.string.already_have_an_account),
            onClick = { onAction(RegisterAction.OnLogin) },
        )
    }
}

@ScreenSizesPreview
@Composable
private fun CreateAccountScreenPreview() {
    NoteMarkTheme {
        RegisterScreen(
            state = RegisterViewState.Initial,
            onAction = {}
        )
    }
}
package com.insearching.notemark.presentation.screens.login

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.notemark.R
import com.insearching.notemark.core.ScreenSizesPreview
import com.insearching.notemark.presentation.components.NoteMarkClickableText
import com.insearching.notemark.presentation.components.NoteMarkPrimaryButton
import com.insearching.notemark.presentation.components.NoteMarkTextField
import com.insearching.notemark.ui.theme.LocalScreenOrientation
import com.insearching.notemark.ui.theme.NoteMarkTheme
import com.insearching.notemark.ui.theme.ScreenOrientation
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    onCreateNewAccount: () -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                LoginEvent.OnCreateNewAccount -> onCreateNewAccount()
                LoginEvent.OnLogin -> {
                    Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LoginScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginViewState,
    onAction: (LoginAction) -> Unit,
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
                ScreenOrientation.PhonePortrait -> PhonePortraitLoginScreen(
                    state = state,
                    onAction = onAction
                )

                ScreenOrientation.TabletPortrait -> TabletPortraitLoginScreen(
                    state = state,
                    onAction = onAction
                )

                ScreenOrientation.Landscape -> LandscapeLoginScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun PhonePortraitLoginScreen(
    state: LoginViewState,
    onAction: (LoginAction) -> Unit,
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
        LoginForm(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
private fun LandscapeLoginScreen(
    state: LoginViewState,
    onAction: (LoginAction) -> Unit,
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
        LoginForm(
            modifier = Modifier.weight(1f),
            state = state,
            onAction = onAction
        )
    }
}

@Composable
private fun TabletPortraitLoginScreen(
    state: LoginViewState,
    onAction: (LoginAction) -> Unit,
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
        LoginForm(
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
            text = stringResource(R.string.login),
            style = NoteMarkTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.capture_your_thoughts_and_ideas),
            style = NoteMarkTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun LoginForm(
    modifier: Modifier = Modifier,
    state: LoginViewState,
    onAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            label = stringResource(R.string.email),
            placeholder = "john.doe@example.com",
            onValueChange = { onAction(LoginAction.OnEmailChanged(it)) },
        )
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            isPassword = true,
            onValueChange = { onAction(LoginAction.OnPassChanged(it)) },
        )
        NoteMarkPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login),
            enabled = state.loginEnabled,
            onClick = { onAction(LoginAction.OnLogin) },
        )
        Spacer(modifier = Modifier.height(NoteMarkTheme.offset.medium))
        NoteMarkClickableText(
            text = stringResource(R.string.dont_have_an_account),
            onClick = { onAction(LoginAction.OnCreateNewAccount) },
        )
    }
}

@ScreenSizesPreview
@Composable
private fun LoginScreenPreview() {
    NoteMarkTheme {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            state = LoginViewState.Initial,
            onAction = {}
        )
    }
}
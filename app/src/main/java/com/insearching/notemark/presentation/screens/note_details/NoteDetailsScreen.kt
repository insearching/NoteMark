package com.insearching.notemark.presentation.screens.note_details

import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.notemark.R
import com.insearching.notemark.core.extension.noRippleClickable
import com.insearching.notemark.presentation.components.NoteMarkTransparentTextField
import com.insearching.notemark.presentation.dialog.DiscardChangesDialog
import com.insearching.notemark.ui.theme.NoteMarkTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun NoteDetailsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: NoteDetailsViewModel = koinViewModel<NoteDetailsViewModel>(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var noteDiscarded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                NoteDetailsEvent.OnNoteSaved -> onBack()
                NoteDetailsEvent.OnDiscardEvent -> {
                    noteDiscarded = true
                }

                NoteDetailsEvent.OnBack -> onBack()
            }
        }
    }

    NoteDetailsScreen(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )

    if (noteDiscarded) {
        DiscardChangesDialog(
            onConfirm = onBack,
            onDismiss = { noteDiscarded = false }
        )
    }
}

@Composable
fun NoteDetailsScreen(
    modifier: Modifier = Modifier,
    state: NoteDetailsState,
    onAction: (NoteDetailsAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val imeState = rememberImeState()

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(Int.MAX_VALUE)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NoteMarkTheme.color.surface)
            .imePadding()
            .verticalScroll(scrollState)
    ) {
        TopBar(onAction = onAction)

        NoteMarkTransparentTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NoteMarkTheme.offset.regular),
            value = state.title,
            onValueChange = { onAction(NoteDetailsAction.OnTitleChanged(it)) },
            textStyle = NoteMarkTheme.typography.titleLarge,
            color = NoteMarkTheme.color.onSurface,
            placeholder = stringResource(R.string.note_title),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
        )

        HorizontalDivider(thickness = 1.dp, color = NoteMarkTheme.color.surface)

        NoteMarkTransparentTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(NoteMarkTheme.offset.regular)
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            delay(300) // let keyboard open
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            value = state.content,
            onValueChange = { onAction(NoteDetailsAction.OnContentChanged(it)) },
            textStyle = NoteMarkTheme.typography.bodyLarge,
            color = NoteMarkTheme.color.onSurfaceVar,
            placeholder = stringResource(R.string.note_content),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onAction(NoteDetailsAction.OnSaveNoteClicked)
                }
            ),
        )
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onAction: (NoteDetailsAction) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(NoteMarkTheme.color.surfaceLowest)
            .padding(
                horizontal = NoteMarkTheme.offset.regular,
                vertical = NoteMarkTheme.offset.medium
            )
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { onAction(NoteDetailsAction.OnDiscardNoteClicked) },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
            )
        }
        Text(
            text = stringResource(R.string.save_note).uppercase(),
            style = NoteMarkTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
                color = NoteMarkTheme.color.primary
            ),
            modifier = Modifier
                .noRippleClickable(onClick = { onAction(NoteDetailsAction.OnSaveNoteClicked) })
        )
    }
}

@Composable
fun rememberImeState(): State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}

@PreviewScreenSizes
@Composable
private fun NoteDetailsScreenPreview() {
    NoteMarkTheme {
        NoteDetailsScreen(
            state = NoteDetailsState(
                title = "Title",
                content = "Content"
            ),
            onAction = { }
        )
    }
}
package com.insearching.notemark.presentation.screens.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insearching.notemark.R
import com.insearching.notemark.core.ScreenSizesPreview
import com.insearching.notemark.core.extension.onClickAndLongClick
import com.insearching.notemark.domain.model.Note
import com.insearching.notemark.presentation.dialog.DeleteNoteDialog
import com.insearching.notemark.ui.theme.LocalScreenOrientation
import com.insearching.notemark.ui.theme.NoteMarkTheme
import com.insearching.notemark.ui.theme.ScreenOrientation
import com.insearching.notemark.utils.toShortDayMonth
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime

@Composable
fun NoteListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = koinViewModel<NoteListViewModel>(),
    onAddNote: () -> Unit,
    onEditNote: (Note) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NoteListScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onAction = { action ->
            when (action) {
                NoteListAction.OnNoteAdd -> onAddNote()
                is NoteListAction.OnNoteEdit -> onEditNote(action.note)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun NoteListScreen(
    modifier: Modifier = Modifier,
    state: NoteListState,
    onAction: (NoteListAction) -> Unit,
) {
    var notToDelete by remember { mutableStateOf<Note?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NoteMarkTheme.color.surfaceLowest)
    ) {
        Scaffold(
            topBar = { TopBar(state = state) },
            floatingActionButton = {
                AddButton(onClick = { onAction(NoteListAction.OnNoteAdd) })
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(NoteMarkTheme.color.surface),
            ) {
                when (state) {
                    is NoteListState.Success -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(columnSize()),
                            verticalItemSpacing = itemSpacing(),
                            horizontalArrangement = Arrangement.spacedBy(itemSpacing()),
                            contentPadding = contentPadding(),
                            content = {
                                items(state.notes, key = { it.id ?: 0 }) { note ->
                                    NoteCard(
                                        note = note,
                                        onClick = { onAction(NoteListAction.OnNoteEdit(note)) },
                                        onLongClick = {
                                            notToDelete = note
                                        }
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    NoteListState.Initial -> {
                        Text(
                            text = stringResource(R.string.empty_board_message),
                            style = NoteMarkTheme.typography.titleSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = NoteMarkTheme.offset.big)
                        )
                    }
                }

                notToDelete?.let {
                    DeleteNoteDialog(
                        onConfirm = { onAction(NoteListAction.OnNoteDelete(it)) },
                        onDismiss = { notToDelete = null }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    state: NoteListState,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(NoteMarkTheme.color.surfaceLowest)
            .padding(topContentPadding())
            .statusBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = NoteMarkTheme.typography.titleMedium
        )
        if (state is NoteListState.Success) {
            ProfileIcon(
                modifier = Modifier.size(40.dp),
                text = state.userName
            )
        }
    }
}

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .clip(NoteMarkTheme.shape.medium)
            .background(NoteMarkTheme.color.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = NoteMarkTheme.typography.titleMedium.copy(
                fontSize = 17.sp,
                lineHeight = 24.sp,
                color = NoteMarkTheme.color.onPrimary
            )
        )
    }
}

@Composable
private fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
        modifier = modifier
            .fillMaxWidth()
            .clip(NoteMarkTheme.shape.medium)
            .background(NoteMarkTheme.color.surfaceLowest)
            .onClickAndLongClick(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(NoteMarkTheme.offset.medium)
    ) {
        Text(
            text = note.createdAt.toShortDayMonth(),
            style = NoteMarkTheme.typography.bodyMedium,
            color = NoteMarkTheme.color.primary
        )
        Spacer(Modifier.height(NoteMarkTheme.offset.tiny))

        Text(
            text = note.title,
            style = NoteMarkTheme.typography.titleMedium,
            color = NoteMarkTheme.color.onSurface
        )

        Text(
            text = note.content.ellipsize(maxLetters()),
            style = NoteMarkTheme.typography.bodySmall,
            color = NoteMarkTheme.color.onSurfaceVar,
        )
    }
}

fun String.ellipsize(maxLength: Int): String {
    return if (length > maxLength) {
        take(maxLength).trimEnd() + "…"
    } else this
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier.size(64.dp),
        onClick = onClick,
        containerColor = Color.Transparent,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF58A1F8), Color(0xFF5A4CF7))
                    ),
                    shape = NoteMarkTheme.shape.average
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun columnSize(): Int {
    return when (LocalScreenOrientation.current) {
        ScreenOrientation.PhonePortrait -> 2
        ScreenOrientation.TabletPortrait -> 2
        ScreenOrientation.Landscape -> 3
    }
}

@Composable
private fun maxLetters(): Int {
    return when (LocalScreenOrientation.current) {
        ScreenOrientation.PhonePortrait -> 150
        ScreenOrientation.TabletPortrait -> 250
        ScreenOrientation.Landscape -> 250
    }
}

@Composable
private fun itemSpacing(): Dp {
    return when (LocalScreenOrientation.current) {
        ScreenOrientation.PhonePortrait -> NoteMarkTheme.offset.small
        ScreenOrientation.TabletPortrait -> NoteMarkTheme.offset.intermediate
        ScreenOrientation.Landscape -> NoteMarkTheme.offset.intermediate
    }
}

@Composable
private fun contentPadding(): PaddingValues {
    return when (LocalScreenOrientation.current) {
        ScreenOrientation.PhonePortrait -> PaddingValues(NoteMarkTheme.offset.small)
        ScreenOrientation.TabletPortrait -> PaddingValues(
            start = NoteMarkTheme.offset.enormous,
            end = NoteMarkTheme.offset.intermediate,
            top = NoteMarkTheme.offset.intermediate
        )

        ScreenOrientation.Landscape -> PaddingValues(
            start = NoteMarkTheme.offset.enormous,
            end = NoteMarkTheme.offset.intermediate,
            top = NoteMarkTheme.offset.intermediate
        )
    }
}

@Composable
private fun topContentPadding(): PaddingValues {
    return when (LocalScreenOrientation.current) {
        ScreenOrientation.PhonePortrait -> PaddingValues(NoteMarkTheme.offset.medium)
        ScreenOrientation.TabletPortrait -> PaddingValues(
            start = NoteMarkTheme.offset.massive,
            end = NoteMarkTheme.offset.intermediate,
            top = NoteMarkTheme.offset.intermediate,
            bottom = NoteMarkTheme.offset.intermediate,
        )
        ScreenOrientation.Landscape -> PaddingValues(
            start = NoteMarkTheme.offset.enormous,
            end = NoteMarkTheme.offset.intermediate,
            top = NoteMarkTheme.offset.intermediate,
            bottom = NoteMarkTheme.offset.intermediate,
        )
    }
}


@ScreenSizesPreview
@Composable
private fun NoteListScreenDataPreview() {
    NoteMarkTheme {
        NoteListScreen(
            state = NoteListState.Success(
                notes = (1L..10L).map {
                    Note(
                        title = "Title $it",
                        content = (1..it * 2).map { "Some text $it times" }.toList()
                            .joinToString(", "),
                        createdAt = ZonedDateTime.now(),
                        lastEditedAt = ZonedDateTime.now()
                    )
                }
                    .toList(),
                userName = "SH"
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun NoteListScreenInitialPreview() {
    NoteMarkTheme {
        NoteListScreen(
            state = NoteListState.Initial,
            onAction = {}
        )
    }
}


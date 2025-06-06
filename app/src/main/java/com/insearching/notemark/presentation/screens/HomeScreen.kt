package com.insearching.notemark.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NoteMarkTheme.color.primary)
            .padding(NoteMarkTheme.offset.huge),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Welcome to NoteMark app!",
            style = NoteMarkTheme.typography.titleLarge,
            color = NoteMarkTheme.color.onPrimary,
        )
    }
}
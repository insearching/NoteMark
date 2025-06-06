package com.insearching.notemark.presentation.screens.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insearching.notemark.R
import com.insearching.notemark.presentation.components.NoteMarkPrimaryButton
import com.insearching.notemark.presentation.components.NoteMarkSecondaryButton
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(NoteMarkTheme.color.surfaceLowest)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            painter = painterResource(R.drawable.ic_onboarding_image),
            contentDescription = "onboarding image",
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(NoteMarkTheme.offset.tiny),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(
                    color = NoteMarkTheme.color.surfaceLowest
                )
                .padding(NoteMarkTheme.offset.medium)
        ) {
            Text(
                text = stringResource(R.string.your_own_collection_of_notes),
                style = NoteMarkTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.capture_your_thoughts_and_ideas),
                style = NoteMarkTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(NoteMarkTheme.offset.average))
            NoteMarkPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.get_started),
                onClick = onGetStartedClick,
            )
            Spacer(Modifier.height(NoteMarkTheme.offset.little))
            NoteMarkSecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.login),
                onClick = onLoginClick,
            )
        }
    }
}

@Preview(device = PIXEL_4)
@Composable
private fun LandingScreenPreview() {
    NoteMarkTheme {
        LandingScreen(
            onGetStartedClick = {},
            onLoginClick = {}
        )
    }
}
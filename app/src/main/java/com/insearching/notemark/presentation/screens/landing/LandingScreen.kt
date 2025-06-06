package com.insearching.notemark.presentation.screens.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.insearching.notemark.R
import com.insearching.notemark.core.ScreenSizesPreview
import com.insearching.notemark.presentation.components.NoteMarkPrimaryButton
import com.insearching.notemark.presentation.components.NoteMarkSecondaryButton
import com.insearching.notemark.ui.theme.LocalScreenOrientation
import com.insearching.notemark.ui.theme.NoteMarkTheme
import com.insearching.notemark.ui.theme.ScreenOrientation

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    when (LocalScreenOrientation.current) {
        ScreenOrientation.PhonePortrait -> PhonePortraitLandingScreen(
            modifier = modifier.fillMaxSize(),
            onGetStartedClick = onGetStartedClick,
            onLoginClick = onLoginClick,
        )

        ScreenOrientation.Landscape -> LandscapeLandingScreen(
            modifier = modifier.fillMaxSize(),
            onGetStartedClick = onGetStartedClick,
            onLoginClick = onLoginClick,
        )

        ScreenOrientation.TabletPortrait -> TabletPortraitLandingScreen(
            modifier = modifier.fillMaxSize(),
            onGetStartedClick = onGetStartedClick,
            onLoginClick = onLoginClick,
        )
    }
}


@Composable
fun PhonePortraitLandingScreen(
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
        LandingForm(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(
                    color = NoteMarkTheme.color.surfaceLowest
                )
                .padding(horizontal = NoteMarkTheme.offset.medium, vertical = NoteMarkTheme.offset.average),
            onGetStartedClick = onGetStartedClick,
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
fun LandscapeLandingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(NoteMarkTheme.color.surfaceLowest)
    ) {
        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(R.drawable.ic_onboarding_image),
            contentDescription = "onboarding image",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFFE0EAFF))
                .padding(start = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            LandingForm(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .background(
                        color = NoteMarkTheme.color.surfaceLowest
                    )
                    .padding(
                        horizontal = NoteMarkTheme.offset.enormous,
                        vertical = NoteMarkTheme.offset.great
                    ),
                onGetStartedClick = onGetStartedClick,
                onLoginClick = onLoginClick,
            )
        }
    }
}

@Composable
fun TabletPortraitLandingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Box(
        modifier = modifier.background(NoteMarkTheme.color.surfaceLowest)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_onboarding_image),
            contentDescription = "onboarding image",
            contentScale = ContentScale.Crop
        )
        LandingForm(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = NoteMarkTheme.offset.giant)
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(color = NoteMarkTheme.color.surfaceLowest)
                .padding(horizontal = NoteMarkTheme.offset.huge, vertical = NoteMarkTheme.offset.enormous),
            onGetStartedClick = onGetStartedClick,
            onLoginClick = onLoginClick,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LandingForm(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit,
    textAlign: TextAlign = TextAlign.Start,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            NoteMarkTheme.offset.tiny,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.your_own_collection_of_notes),
            style = NoteMarkTheme.typography.titleLarge,
            textAlign = textAlign
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.capture_your_thoughts_and_ideas),
            style = NoteMarkTheme.typography.bodyLarge,
            textAlign = textAlign
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

@ScreenSizesPreview
@Composable
private fun LandingScreenPreview() {
    NoteMarkTheme {
        LandingScreen(
            onGetStartedClick = {},
            onLoginClick = {}
        )
    }
}
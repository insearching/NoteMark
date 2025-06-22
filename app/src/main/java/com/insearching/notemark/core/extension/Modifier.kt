package com.insearching.notemark.core.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    this.then(
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    )
}

fun Modifier.applyIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.then(block())
    } else {
        this
    }
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.onLongClick(
    onLongClick: () -> Unit
): Modifier = this.then(
    pointerInput(Unit) {
        detectTapGestures(
            onLongPress = { onLongClick() }
        )
    }
)

fun Modifier.onClickAndLongClick(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit
): Modifier = pointerInput(Unit) {
    detectTapGestures(
        onTap = { onClick() },
        onLongPress = { onLongClick() }
    )
}
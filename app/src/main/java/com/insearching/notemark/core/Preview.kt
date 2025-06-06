package com.insearching.notemark.core

import androidx.compose.ui.tooling.preview.Preview


@Preview(
    group = "Screen Size",
    name = "Phone",
    device = "id:pixel_8",
    showBackground = true
)
@Preview(
    group = "Screen Size",
    name = "Phone Landscape",
    device = "spec:parent=pixel_8,orientation=landscape",
    showBackground = true
)
@Preview(
    group = "Screen Size",
    name = "Tablet",
    device = "spec:parent=Nexus 10,orientation=portrait",
    showBackground = true
)
@Preview(
    group = "Screen Size",
    name = "Tablet",
    device = "spec:parent=Nexus 10,orientation=landscape",
    showBackground = true
)
annotation class ScreenSizesPreview
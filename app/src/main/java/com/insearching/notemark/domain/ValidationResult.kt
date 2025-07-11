package com.insearching.notemark.domain

import com.insearching.notemark.core.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
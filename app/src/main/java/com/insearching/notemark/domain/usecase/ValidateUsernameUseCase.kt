package com.insearching.notemark.domain.usecase

import com.insearching.notemark.R
import com.insearching.notemark.core.UiText
import com.insearching.notemark.domain.ValidationResult

class ValidateUsernameUseCase {

    operator fun invoke(username: String): ValidationResult {
        val trimmed = username.trim()

        if (trimmed.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_username_too_short)
            )
        }

        if (trimmed.length > 20) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_username_too_long)
            )
        }

        return ValidationResult(successful = true)
    }
}
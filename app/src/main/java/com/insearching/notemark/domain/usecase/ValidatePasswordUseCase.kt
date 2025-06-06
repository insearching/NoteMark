package com.insearching.notemark.domain.usecase

import com.insearching.notemark.R
import com.insearching.notemark.core.UiText
import com.insearching.notemark.domain.ValidationResult

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult {
        val passTrimmed = password.trim()
        if (passTrimmed.isEmpty()) {
            return ValidationResult(successful = false)
        }
        val hasLetter = passTrimmed.any { it.isLetter() }
        val hasDigit = passTrimmed.any { it.isDigit() }

        if (passTrimmed.length < 8 || !hasLetter || !hasDigit) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_password_needs_number_or_symbol)
            )
        }

        return ValidationResult(successful = true)
    }
}
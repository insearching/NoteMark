package com.insearching.notemark.domain.usecase

import com.insearching.notemark.R
import com.insearching.notemark.core.UiText
import com.insearching.notemark.domain.ValidationResult

class ValidateEmailUseCase {

    operator fun invoke(email: String): ValidationResult {
        val emailTrimmed = email.trim()

        if (emailTrimmed.isEmpty()) {
            return ValidationResult(successful = false)
        }

        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()

        if (!emailPattern.matches(emailTrimmed)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_email_invalid)
            )
        }

        return ValidationResult(successful = true)
    }
}
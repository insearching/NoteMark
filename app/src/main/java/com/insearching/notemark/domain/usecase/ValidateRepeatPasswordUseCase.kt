package com.insearching.notemark.domain.usecase


import com.insearching.notemark.R
import com.insearching.notemark.core.UiText

class ValidateRepeatPasswordUseCase {

    operator fun invoke(firstPass: String, secondPass: String): ValidationResult {
        val firstPassTrimmed = firstPass.trim()
        val secondPassTrimmed = secondPass.trim()
        if (secondPass.isEmpty()) {
            return ValidationResult(successful = false)
        }

        if(firstPassTrimmed != secondPassTrimmed){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_passwords_dont_match)
            )
        }

        return ValidationResult(successful = true)
    }
}
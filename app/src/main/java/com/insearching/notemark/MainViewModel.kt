package com.insearching.notemark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.notemark.domain.SessionStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage,
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1_000L)
            _isLoggedIn.value = isLoggedIn()
            _isReady.value = true
        }
    }

    private suspend fun isLoggedIn(): Boolean {
        return sessionStorage.getUserToken() != null
    }
}
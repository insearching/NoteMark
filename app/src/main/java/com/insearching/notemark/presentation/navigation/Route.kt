package com.insearching.notemark.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object LandingScreen : Route()

    @Serializable
    data object LoginScreen : Route()

    @Serializable
    data object RegisterScreen : Route()

}
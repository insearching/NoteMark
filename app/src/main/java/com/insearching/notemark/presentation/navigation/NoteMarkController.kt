package com.insearching.notemark.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.insearching.notemark.presentation.screens.landing.LandingScreen
import com.insearching.notemark.presentation.screens.login.LoginScreenRoot
import com.insearching.notemark.presentation.screens.note_details.NoteDetailsScreenRoot
import com.insearching.notemark.presentation.screens.note_list.NoteListScreenRoot
import com.insearching.notemark.presentation.screens.register.RegisterScreenRoot

@Composable
fun NoteMarkController(
    modifier: Modifier = Modifier,
    startDestination: Route,
    controller: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = controller,
        startDestination = startDestination,
    ) {
        composable<Route.LandingScreen> {
            LandingScreen(
                modifier = modifier.fillMaxSize(),
                onGetStartedClick = {
                    controller.navigate(Route.RegisterScreen)
                },
                onLoginClick = {
                    controller.navigate(Route.LoginScreen)
                }
            )
        }
        composable<Route.LoginScreen> {
            LoginScreenRoot(
                onCreateNewAccount = {
                    controller.popBackStack()
                    controller.navigate(Route.RegisterScreen)
                },
                onLoginSuccess = {
                    controller.popBackStack()
                    controller.navigate(Route.NoteListScreen)
                }
            )
        }
        composable<Route.RegisterScreen> {
            RegisterScreenRoot(
                onLoginClick = {
                    controller.popBackStack()
                    controller.navigate(Route.LoginScreen)
                }
            )
        }
        composable<Route.NoteListScreen> {
            NoteListScreenRoot(
                onAddNote = {
                    controller.navigate(Route.NoteDetailsScreen())
                },
                onEditNote = { note ->
                    controller.navigate(Route.NoteDetailsScreen(note.id))
                }
            )
        }

        composable<Route.NoteDetailsScreen> {
            NoteDetailsScreenRoot(
                onBack = {
                    controller.popBackStack()
                }
            )
        }
    }
}
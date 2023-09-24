package com.smarchinskya.mvistatemachine.welcomescreen.presentation

import com.smarchinskya.mvistatemachine.base.State

sealed interface WelcomeScreenState: State {

    data object Loading : WelcomeScreenState

    data class Display(
        val welcomeText: String = "",
        val isInfoDialogVisible: Boolean = false,
    ) : WelcomeScreenState

    data class Error(
        val message: String = "",
    ) : WelcomeScreenState
}

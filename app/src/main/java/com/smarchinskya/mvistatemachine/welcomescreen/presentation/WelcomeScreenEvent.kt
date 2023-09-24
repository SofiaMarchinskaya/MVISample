package com.smarchinskya.mvistatemachine.welcomescreen.presentation

import com.smarchinskya.mvistatemachine.base.Event

sealed interface WelcomeScreenEvent: Event {

    data object Terminate : WelcomeScreenEvent
}

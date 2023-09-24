package com.smarchinskya.mvistatemachine.welcomescreen.domain

import javax.inject.Inject
import kotlinx.coroutines.delay

class WelcomeScreenInteractorImpl @Inject constructor()
    : WelcomeScreenInteractor {

    override suspend fun getSomeData(): String {
        delay(3000L)
        return "Данные"
    }
}

package com.smarchinskya.mvistatemachine.welcomescreen.domain

interface WelcomeScreenInteractor {
    suspend fun getSomeData(): String
}
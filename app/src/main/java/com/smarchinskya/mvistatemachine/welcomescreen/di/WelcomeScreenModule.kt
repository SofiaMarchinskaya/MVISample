package com.smarchinskya.mvistatemachine.welcomescreen.di

import com.smarchinskya.mvistatemachine.welcomescreen.domain.WelcomeScreenInteractor
import com.smarchinskya.mvistatemachine.welcomescreen.domain.WelcomeScreenInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WelcomeScreenModule {

    @Binds
    fun bindInteractor(impl: WelcomeScreenInteractorImpl): WelcomeScreenInteractor
}

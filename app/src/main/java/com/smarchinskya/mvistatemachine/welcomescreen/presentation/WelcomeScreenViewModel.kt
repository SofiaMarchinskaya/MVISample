package com.smarchinskya.mvistatemachine.welcomescreen.presentation

import androidx.lifecycle.viewModelScope
import com.smarchinskya.mvistatemachine.base.BaseViewModel
import com.smarchinskya.mvistatemachine.welcomescreen.domain.WelcomeScreenInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val interactor: WelcomeScreenInteractor,
) : BaseViewModel<WelcomeScreenState, WelcomeScreenEvent>(WelcomeScreenState.Loading) {

    init {
        viewModelScope.launch {
            val data = interactor.getSomeData()
            updateState {
                WelcomeScreenState.Display(
                    welcomeText = data,
                )
            }
        }
    }

    fun onShowErrorClick() {
        updateState {
            WelcomeScreenState.Error("message")
        }
    }

    fun onDismissErrorClick() {
        viewModelScope.launch {
            sendEvent(WelcomeScreenEvent.Terminate)
        }
    }

    fun onShowDialogClick() {
        updateState<WelcomeScreenState.Display> {
            it.copy(
                isInfoDialogVisible = true,
            )
        }
    }

    fun onDismissDialogClick() {
        updateState<WelcomeScreenState.Display> {
            it.copy(
                isInfoDialogVisible = false,
            )
        }
    }
}

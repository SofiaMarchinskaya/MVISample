package com.smarchinskya.mvistatemachine.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

/**
 * Класс базовой ViewModel
 * @param initialState Начальное состояние экрана
 */
abstract class BaseViewModel<STATE : State, EVENT : Event>(initialState: STATE) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _event = MutableSharedFlow<EVENT>(extraBufferCapacity = 1)

    /** События */
    val event = _event.asSharedFlow()

    protected val currentState: STATE
        get() = _state.value

    /**
     * Обновление стейта возможно только через функции updateState.
     * Данная реализация нужна, чтобы избежать объемного кода приведения типов при использовании
     * sealed классов для описания стейта экрана
     *
     * @param transform функция обновления стейта
     * @param onCastError обработка ошибки приведения типов
     */
    fun <CASTED : STATE> updateState(
        onCastError: (STATE) -> STATE = ::handleWrongErrorState,
        transform: (CASTED) -> STATE,
    ) {
        _state.update {
            (it as CASTED)?.let(transform) ?: onCastError(it)
        }
    }

    /**
     * Упрощенная реализация обновления
     */
    fun updateState(transform: (STATE) -> STATE) =
        _state.update(transform)

    /**
     * Отправка события
     */
    protected suspend fun sendEvent(event: EVENT) =
        _event.emit(event)

    private fun handleWrongErrorState(state: STATE): STATE {
        Log.d("ERROR", "WRONG STATE")// add logger?
        return state
    }
}

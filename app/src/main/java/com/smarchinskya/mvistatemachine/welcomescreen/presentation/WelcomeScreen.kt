package com.smarchinskya.mvistatemachine.welcomescreen.presentation

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smarchinskya.mvistatemachine.ui.theme.MVIStateMachineTheme

@Composable
fun WelcomeScreen() {
    val viewModel: WelcomeScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    viewModel.ObserveEvents()
    WatchStateChanges(
        state = state.value,
        viewModel = viewModel,
    )
}

@Composable
private fun WelcomeScreenViewModel.ObserveEvents() {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(Unit) {
        event.collect {
            when (it) {
                is WelcomeScreenEvent.Terminate -> {
                    activity?.finish()
                }
            }
        }
    }
}

@Composable
private fun WelcomeScreenViewModel.ObserveDialogs(state: WelcomeScreenState.Display) {
    if (state.isInfoDialogVisible) {
        InfoDialog(onDismiss = ::onDismissDialogClick)
    }
}

@Composable
private fun WatchStateChanges(
    state: WelcomeScreenState,
    viewModel: WelcomeScreenViewModel,
) {
    when (state) {
        is WelcomeScreenState.Loading -> {
            Loader()
        }

        is WelcomeScreenState.Display -> {
            viewModel.ObserveDialogs(state = state)
            WelcomeScreenContent(state, viewModel::onShowErrorClick, viewModel::onShowDialogClick)
        }

        is WelcomeScreenState.Error -> {
            Error(
                message = state.message,
                onDismiss = viewModel::onDismissErrorClick,
            )
        }
    }
}

@Composable
fun Loader(
    trackWidth: Dp = 4.dp,
    trackColor: Color = Color.Blue,
) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp),
            color = trackColor,
            strokeWidth = trackWidth,
        )
    }
}

@Composable
private fun Error(
    message: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismiss,
            ) {
                Text(text = "ОТМЕНА")
            }
        },
        text = @Composable {
            Text(text = message)
        },
    )
}

@Composable
private fun InfoDialog(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismiss,
            ) {
                Text(text = "ОК")
            }
        },
        text = @Composable {
            Text(text = "Информация")
        },
    )
}

@Composable
private fun WelcomeScreenContent(
    state: WelcomeScreenState.Display,
    onShowErrorClick: () -> Unit,
    onShowDialogClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.welcomeText,
        )
        Button(onClick = onShowErrorClick) {
            Text(text = "Показать ошибку")
        }
        Button(onClick = onShowDialogClick) {
            Text(text = "Показать неблокирующий диалог")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun WelcomeScreenPreview() {
    MVIStateMachineTheme {
        WelcomeScreenContent(
            state = WelcomeScreenState.Display("ПРИВЕТ"),
            onShowErrorClick = {},
            onShowDialogClick = {},
        )
    }
}

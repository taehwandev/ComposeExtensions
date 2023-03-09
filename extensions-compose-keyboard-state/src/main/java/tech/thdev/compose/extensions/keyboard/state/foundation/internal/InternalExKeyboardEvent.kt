package tech.thdev.compose.extensions.keyboard.state.foundation.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tech.thdev.compose.extensions.keyboard.state.ExKeyboardState

@Composable
internal fun rememberKeyboardOpen(): State<ExKeyboardState> {
    val view = LocalView.current
    return produceState(initialValue = false.toState()) {
        ViewCompat.setOnApplyWindowInsetsListener(view.rootView) { _, listener ->
            value = listener.isVisible(WindowInsetsCompat.Type.ime()).toState()
            listener
        }
        awaitDispose {}
    }
}

private fun Boolean.toState(): ExKeyboardState =
    if (this) ExKeyboardState.SHOW else ExKeyboardState.HIDE
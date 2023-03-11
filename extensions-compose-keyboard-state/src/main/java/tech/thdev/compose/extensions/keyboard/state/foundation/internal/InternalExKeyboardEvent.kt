package tech.thdev.compose.extensions.keyboard.state.foundation.internal

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tech.thdev.compose.extensions.keyboard.state.ExKeyboardState

@Composable
internal fun rememberKeyboardOpen(): State<ExKeyboardState> {
    val keyboardState = remember { mutableStateOf(ExKeyboardState.HIDE) }
    val view = LocalView.current

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
            keyboardState.value = isKeyboardOpen.toState()
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }
    return keyboardState
}

private fun Boolean.toState(): ExKeyboardState =
    if (this) ExKeyboardState.SHOW else ExKeyboardState.HIDE
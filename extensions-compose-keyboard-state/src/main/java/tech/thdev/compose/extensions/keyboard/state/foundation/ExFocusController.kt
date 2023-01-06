package tech.thdev.compose.extensions.keyboard.state.foundation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import tech.thdev.compose.extensions.keyboard.state.foundation.internal.rememberKeyboardOpen
import tech.thdev.compose.extensions.keyboard.state.isHide
import tech.thdev.compose.extensions.keyboard.state.isOpen
import tech.thdev.compose.extensions.keyboard.state.localowners.LocalMutableExKeyboardStateSourceOwner

/**
 * Modifier for handling keyboard state.
 *
 * Use scaffold modifier
 * CompositionLocalProvider(
 *      LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource()
 * ) {
 *      Scaffold(
 *          modifier = Modifier
 *              .removeFocusWhenKeyboardIsHidden()
 *      ) {
 *      }
 * }
 *
 * when checking status
 * <pre>
 * val keyboardState by mutableKeyboardStateSource. keyboardState()
 *
 * DisposableEffect(showKeyboard) {
 *   // Use keyboard state
 *   onDispose {
 *
 *   }
 * }
 * </pre>
 */
fun Modifier.removeFocusWhenKeyboardIsHidden(): Modifier = composed {
    val mutableKeyboardStateSource = LocalMutableExKeyboardStateSourceOwner.current

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardState by rememberKeyboardOpen()

    LaunchedEffect(keyboardState) {
        mutableKeyboardStateSource.emit(keyboardState)
    }

    fun clear() {
        focusManager.clearFocus()
        keyboardAppearedSinceLastFocused = false
    }

    LaunchedEffect(key1 = keyboardState.isOpen(), key2 = isFocused) {
        if (isFocused) {
            if (keyboardState.isOpen()) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                clear()
            }
        }
    }

    this
        .onFocusEvent {
            if (isFocused != it.isFocused) {
                isFocused = it.isFocused
                if (isFocused && keyboardState.isHide()) {
                    keyboardAppearedSinceLastFocused = false
                }
            }
        }
        .pointerInput(Unit) {
            detectTapGestures {
                clear()
            }
        }
        .focusRequester(focusRequester)
}
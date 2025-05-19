package tech.thdev.compose.extensions.keyboard.state.foundation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Modifier for handling keyboard state.
 *
 * Use scaffold modifier
 * Scaffold(
 *     modifier = Modifier
 *         .keyboardHide()
 * ) {
 *      // Your code
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
fun Modifier.keyboardHide(): Modifier = composed {
    val focusManager = LocalFocusManager.current

    var keyboardVisibleDuringThisFocusSession by remember { mutableStateOf(false) }
    var hasFocus by remember { mutableStateOf(false) }
    val keyboardVisible by rememberKeyboardVisible()

    fun clear() {
        focusManager.clearFocus()
    }

    LaunchedEffect(keyboardVisible, hasFocus) {
        if (hasFocus) {
            if (keyboardVisible) {
                keyboardVisibleDuringThisFocusSession = true
            } else {
                if (keyboardVisibleDuringThisFocusSession) {
                    clear()
                }
            }
        } else {
            keyboardVisibleDuringThisFocusSession = false
        }
    }

    this
        .onFocusEvent {
            if (hasFocus != it.hasFocus) {
                hasFocus = it.hasFocus
            }
        }
        .pointerInput(Unit) {
            detectTapGestures {
                if (hasFocus && keyboardVisible) {
                    clear()
                }
            }
        }
}

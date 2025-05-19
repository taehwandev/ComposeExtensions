package tech.thdev.compose.extensions.keyboard.state.foundation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

/**
 * Modifier for handling keyboard state.
 *
 * Use scaffold modifier
 * Scaffold(
 *     modifier = Modifier
 *         .removeFocusWhenKeyboardIsHidden()
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
@Composable
fun rememberKeyboardVisible(): State<Boolean> {
    val density = LocalDensity.current
    // WindowInsets.ime provides WindowInsets information related to the IME (Input Method Editor, i.e., the keyboard).
    val imeInsets = WindowInsets.ime

    // Using derivedStateOf, the state is updated and relevant Composables are recomposed
    // only when the result of the imeInsets.getBottom(density) > 0 calculation actually changes.
    return remember {
        derivedStateOf {
            imeInsets.getBottom(density) > 0
        }
    }
}

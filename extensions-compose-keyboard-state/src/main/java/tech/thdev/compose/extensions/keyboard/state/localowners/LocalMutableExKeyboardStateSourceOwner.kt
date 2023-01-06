package tech.thdev.compose.extensions.keyboard.state.localowners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import tech.thdev.compose.extensions.keyboard.state.MutableExKeyboardStateSource

object LocalMutableExKeyboardStateSourceOwner {

    private val LocalComposition = staticCompositionLocalOf<MutableExKeyboardStateSource> {
        noLocalProvidedFor("LocalFocusManager")
    }

    val current: MutableExKeyboardStateSource
        @Composable
        get() = LocalComposition.current

    infix fun provides(registerOwner: MutableExKeyboardStateSource): ProvidedValue<MutableExKeyboardStateSource> =
        LocalComposition provides registerOwner
}
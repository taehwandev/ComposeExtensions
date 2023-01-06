package tech.thdev.compose.extensions.keyboard.state.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import tech.thdev.compose.extensions.keyboard.state.ExKeyboardStateSource
import tech.thdev.compose.extensions.keyboard.state.isOpen

@Composable
fun ExKeyboardStateSource.collectIsKeyboardAsState(): State<Boolean> {
    val isShowKeyboard = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        keyboardState().collect { state ->
            isShowKeyboard.value = state.isOpen()
        }
    }
    return isShowKeyboard
}
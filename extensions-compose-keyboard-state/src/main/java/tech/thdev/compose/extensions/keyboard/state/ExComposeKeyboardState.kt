package tech.thdev.compose.extensions.keyboard.state

enum class ExKeyboardState {
    SHOW,
    HIDE,
}

internal fun ExKeyboardState.isOpen(): Boolean =
    this == ExKeyboardState.SHOW

internal fun ExKeyboardState.isHide(): Boolean =
    this == ExKeyboardState.HIDE
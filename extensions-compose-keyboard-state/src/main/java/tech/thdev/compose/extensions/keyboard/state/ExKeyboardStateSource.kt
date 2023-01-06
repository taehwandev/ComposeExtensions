package tech.thdev.compose.extensions.keyboard.state

import androidx.compose.runtime.Stable
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Stable
interface ExKeyboardStateSource {

    fun keyboardState(): Flow<ExKeyboardState>
}

@Stable
interface MutableExKeyboardStateSource : ExKeyboardStateSource {

    suspend fun emit(keyboardState: ExKeyboardState)

    fun tryEmit(keyboardState: ExKeyboardState): Boolean
}

fun MutableExKeyboardStateSource(): MutableExKeyboardStateSource = MutableExKeyboardStateSourceImpl()

@Stable
private class MutableExKeyboardStateSourceImpl : MutableExKeyboardStateSource {

    private val state = MutableSharedFlow<ExKeyboardState>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override fun keyboardState(): Flow<ExKeyboardState> =
        state
            .distinctUntilChanged()

    override suspend fun emit(keyboardState: ExKeyboardState) {
        state.emit(keyboardState)
    }

    override fun tryEmit(keyboardState: ExKeyboardState): Boolean {
        return state.tryEmit(keyboardState)
    }
}

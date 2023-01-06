package tech.thdev.compose.extensions.keyboard.state.localowners

internal fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
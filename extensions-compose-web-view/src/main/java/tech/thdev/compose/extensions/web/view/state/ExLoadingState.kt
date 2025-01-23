package tech.thdev.compose.extensions.web.view.state

/**
 * Sealed class for constraining possible loading states.
 * See [Loading] and [Finished].
 */
sealed class ExLoadingState {

    /**
     * Describes a WebView that has not yet loaded for the first time.
     */
    data object Initializing : ExLoadingState()

    /**
     * Describes a webview between `onPageStarted` and `onPageFinished` events, contains a
     * [progress] property which is updated by the webview.
     */
    data class Loading(val progress: Float) : ExLoadingState()

    /**
     * Describes a webview that has finished loading content.
     */
    data object Finished : ExLoadingState()
}

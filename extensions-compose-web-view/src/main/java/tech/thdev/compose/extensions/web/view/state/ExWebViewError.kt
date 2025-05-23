package tech.thdev.compose.extensions.web.view.state

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import androidx.compose.runtime.Immutable

/**
 * A wrapper class to hold errors from the WebView.
 */
@Immutable
data class ExWebViewError(
    /**
     * The request the error came from.
     */
    val request: WebResourceRequest?,
    /**
     * The error that was reported.
     */
    val error: WebResourceError
)

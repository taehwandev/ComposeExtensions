package tech.thdev.compose.extensions.web.view.webkit

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import tech.thdev.compose.extensions.web.view.state.ExLoadingState
import tech.thdev.compose.extensions.web.view.state.ExWebViewState

/**
 * ExWebChromeClient
 *
 * A parent class implementation of WebChromeClient that can be subclassed to add custom behaviour.
 *
 * As Accompanist Web needs to set its own web client to function, it provides this intermediary
 * class that can be overriden if further custom behaviour is required.
 */
open class ExWebChromeClient : WebChromeClient() {
    open lateinit var state: ExWebViewState
        internal set

    override fun onReceivedTitle(view: WebView, title: String?) {
        super.onReceivedTitle(view, title)
        state.pageTitle = title
    }

    override fun onReceivedIcon(view: WebView, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
        state.pageIcon = icon
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (state.loadingState is ExLoadingState.Finished) return
        state.loadingState = ExLoadingState.Loading(newProgress / 100.0f)
    }
}

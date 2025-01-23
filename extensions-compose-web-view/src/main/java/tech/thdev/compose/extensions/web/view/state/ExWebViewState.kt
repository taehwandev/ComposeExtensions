package tech.thdev.compose.extensions.web.view.state

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import tech.thdev.compose.extensions.web.view.ExWebView

/**
 * A state holder to hold the state for the WebView. In most cases this will be remembered
 * using the rememberWebViewState(uri) function.
 */
@Stable
class ExWebViewState(exWebContent: ExWebContent) {
    var lastLoadedUrl: String? by mutableStateOf(null)
        internal set

    /**
     *  The content being loaded by the WebView
     */
    var content: ExWebContent by mutableStateOf(exWebContent)

    /**
     * Whether the WebView is currently [ExLoadingState.Loading] data in its main frame (along with
     * progress) or the data loading has [ExLoadingState.Finished]. See [ExLoadingState]
     */
    var loadingState: ExLoadingState by mutableStateOf(ExLoadingState.Initializing)
        internal set

    /**
     * Whether the webview is currently loading data in its main frame
     */
    val isLoading: Boolean
        get() = loadingState !is ExLoadingState.Finished

    /**
     * The title received from the loaded content of the current page
     */
    var pageTitle: String? by mutableStateOf(null)
        internal set

    /**
     * the favicon received from the loaded content of the current page
     */
    var pageIcon: Bitmap? by mutableStateOf(null)
        internal set

    /**
     * A list for errors captured in the last load. Reset when a new page is loaded.
     * Errors could be from any resource (iframe, image, etc.), not just for the main page.
     * For more fine grained control use the OnError callback of the WebView.
     */
    val errorsForCurrentRequest: SnapshotStateList<ExWebViewError> = mutableStateListOf()

    /**
     * The saved view state from when the view was destroyed last. To restore state,
     * use the navigator and only call loadUrl if the bundle is null.
     * See WebViewSaveStateSample.
     */
    var viewState: Bundle? = null
        internal set

    // We need access to this in the state saver. An internal DisposableEffect or AndroidView
    // onDestroy is called after the state saver and so can't be used.
    internal var webView by mutableStateOf<WebView?>(null)
}

/**
 * Creates a WebView state that is remembered across Compositions.
 *
 * @param url The url to load in the WebView
 * @param additionalHttpHeaders Optional, additional HTTP headers that are passed to [ExWebView.loadUrl].
 *                              Note that these headers are used for all subsequent requests of the WebView.
 */
@Composable
fun rememberWebViewState(
    url: String,
    additionalHttpHeaders: Map<String, String> = emptyMap()
): ExWebViewState =
// Rather than using .apply {} here we will recreate the state, this prevents
    // a recomposition loop when the webview updates the url itself.
    remember {
        ExWebViewState(
            ExWebContent.Url(
                url = url,
                additionalHttpHeaders = additionalHttpHeaders
            )
        )
    }.apply {
        this.content = ExWebContent.Url(
            url = url,
            additionalHttpHeaders = additionalHttpHeaders
        )
    }

/**
 * Creates a WebView state that is remembered across Compositions.
 *
 * @param data The uri to load in the WebView
 */
@Composable
fun rememberWebViewStateWithHTMLData(
    data: String,
    baseUrl: String? = null,
    encoding: String = "utf-8",
    mimeType: String? = null,
    historyUrl: String? = null
): ExWebViewState =
    remember {
        ExWebViewState(ExWebContent.Data(data, baseUrl, encoding, mimeType, historyUrl))
    }.apply {
        this.content = ExWebContent.Data(
            data, baseUrl, encoding, mimeType, historyUrl
        )
    }

/**
 * Creates a WebView state that is remembered across Compositions.
 *
 * @param url The url to load in the WebView
 * @param postData The data to be posted to the WebView with the url
 */
@Composable
fun rememberWebViewState(
    url: String,
    postData: ByteArray
): ExWebViewState =
// Rather than using .apply {} here we will recreate the state, this prevents
    // a recomposition loop when the webview updates the url itself.
    remember {
        ExWebViewState(
            ExWebContent.Post(
                url = url,
                postData = postData
            )
        )
    }.apply {
        this.content = ExWebContent.Post(
            url = url,
            postData = postData
        )
    }

/**
 * Creates a WebView state that is remembered across Compositions and saved
 * across activity recreation.
 * When using saved state, you cannot change the URL via recomposition. The only way to load
 * a URL is via a WebViewNavigator.
 *
 * @param data The uri to load in the WebView
 * @sample com.google.accompanist.sample.webview.WebViewSaveStateSample
 */
@Composable
fun rememberSaveableWebViewState(): ExWebViewState =
    rememberSaveable(saver = WebStateSaver) {
        ExWebViewState(ExWebContent.NavigatorOnly)
    }

val WebStateSaver: Saver<ExWebViewState, Any> = run {
    val pageTitleKey = "pagetitle"
    val lastLoadedUrlKey = "lastloaded"
    val stateBundle = "bundle"
    mapSaver(
        save = {
            val viewState = Bundle().apply { it.webView?.saveState(this) }
            mapOf(
                pageTitleKey to it.pageTitle,
                lastLoadedUrlKey to it.lastLoadedUrl,
                stateBundle to viewState
            )
        },
        restore = {
            ExWebViewState(ExWebContent.NavigatorOnly).apply {
                this.pageTitle = it[pageTitleKey] as String?
                this.lastLoadedUrl = it[lastLoadedUrlKey] as String?
                this.viewState = it[stateBundle] as Bundle?
            }
        }
    )
}

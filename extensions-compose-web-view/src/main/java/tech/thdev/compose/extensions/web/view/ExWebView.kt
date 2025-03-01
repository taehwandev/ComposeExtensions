/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.thdev.compose.extensions.web.view

import android.content.Context
import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import tech.thdev.compose.extensions.web.view.state.ExWebContent
import tech.thdev.compose.extensions.web.view.state.ExWebViewNavigator
import tech.thdev.compose.extensions.web.view.state.ExWebViewState
import tech.thdev.compose.extensions.web.view.state.rememberWebViewNavigator
import tech.thdev.compose.extensions.web.view.webkit.ExWebChromeClient
import tech.thdev.compose.extensions.web.view.webkit.ExWebViewClient

/**
 * A wrapper around the Android View WebView to provide a basic WebView composable.
 *
 * If you require more customisation you are most likely better rolling your own and using this
 * wrapper as an example.
 *
 * The WebView attempts to set the layoutParams based on the Compose modifier passed in. If it
 * is incorrectly sizing, use the layoutParams composable function instead.
 *
 * @param state The webview state holder where the Uri to load is defined.
 * @param modifier A compose modifier
 * @param captureBackPresses Set to true to have this Composable capture back presses and navigate
 * the WebView back.
 * @param navigator An optional navigator object that can be used to control the WebView's
 * navigation from outside the composable.
 * @param onCreated Called when the WebView is first created, this can be used to set additional
 * settings on the WebView. WebChromeClient and WebViewClient should not be set here as they will be
 * subsequently overwritten after this lambda is called.
 * @param onDispose Called when the WebView is destroyed. Provides a bundle which can be saved
 * if you need to save and restore state in this WebView.
 * @param client Provides access to WebViewClient via subclassing
 * @param chromeClient Provides access to WebChromeClient via subclassing
 * @param factory An optional WebView factory for using a custom subclass of WebView
 * @sample com.google.accompanist.sample.webview.BasicWebViewSample
 */
@Composable
fun ExWebView(
    state: ExWebViewState,
    modifier: Modifier = Modifier,
    captureBackPresses: Boolean = true,
    navigator: ExWebViewNavigator = rememberWebViewNavigator(),
    onCreated: (WebView) -> Unit = {},
    onDispose: (WebView) -> Unit = {},
    client: ExWebViewClient = remember { ExWebViewClient() },
    chromeClient: ExWebChromeClient = remember { ExWebChromeClient() },
    factory: ((Context) -> WebView)? = null,
) {
    BoxWithConstraints(modifier) {
        // WebView changes it's layout strategy based on
        // it's layoutParams. We convert from Compose Modifier to
        // layout params here.
        val width =
            if (constraints.hasFixedWidth)
                LayoutParams.MATCH_PARENT
            else
                LayoutParams.WRAP_CONTENT
        val height =
            if (constraints.hasFixedHeight)
                LayoutParams.MATCH_PARENT
            else
                LayoutParams.WRAP_CONTENT
        val layoutParams = FrameLayout.LayoutParams(
            width,
            height
        )
        ExWebView(
            state,
            layoutParams,
            Modifier,
            captureBackPresses,
            navigator,
            onCreated,
            onDispose,
            client,
            chromeClient,
            factory
        )
    }
}

/**
 * A wrapper around the Android View WebView to provide a basic WebView composable.
 *
 * If you require more customisation you are most likely better rolling your own and using this
 * wrapper as an example.
 *
 * The WebView attempts to set the layoutParams based on the Compose modifier passed in. If it
 * is incorrectly sizing, use the layoutParams composable function instead.
 *
 * @param state The webview state holder where the Uri to load is defined.
 * @param layoutParams A FrameLayout.LayoutParams object to custom size the underlying WebView.
 * @param modifier A compose modifier
 * @param captureBackPresses Set to true to have this Composable capture back presses and navigate
 * the WebView back.
 * @param navigator An optional navigator object that can be used to control the WebView's
 * navigation from outside the composable.
 * @param onCreated Called when the WebView is first created, this can be used to set additional
 * settings on the WebView. WebChromeClient and WebViewClient should not be set here as they will be
 * subsequently overwritten after this lambda is called.
 * @param onDispose Called when the WebView is destroyed. Provides a bundle which can be saved
 * if you need to save and restore state in this WebView.
 * @param client Provides access to WebViewClient via subclassing
 * @param chromeClient Provides access to WebChromeClient via subclassing
 * @param factory An optional WebView factory for using a custom subclass of WebView
 */
@Composable
fun ExWebView(
    state: ExWebViewState,
    layoutParams: FrameLayout.LayoutParams,
    modifier: Modifier = Modifier,
    captureBackPresses: Boolean = true,
    navigator: ExWebViewNavigator = rememberWebViewNavigator(),
    onCreated: (WebView) -> Unit = {},
    onDispose: (WebView) -> Unit = {},
    client: ExWebViewClient = remember { ExWebViewClient() },
    chromeClient: ExWebChromeClient = remember { ExWebChromeClient() },
    factory: ((Context) -> WebView)? = null,
) {
    val webView = state.webView
    BackHandler(captureBackPresses && navigator.canGoBack) {
        webView?.goBack()
    }
    webView?.let { wv ->
        LaunchedEffect(wv, navigator) {
            with(navigator) {
                wv.handleNavigationEvents()
            }
        }
        LaunchedEffect(wv, state) {
            snapshotFlow { state.content }.collect { content ->
                when (content) {
                    is ExWebContent.Url -> {
                        wv.loadUrl(content.url, content.additionalHttpHeaders)
                    }

                    is ExWebContent.Data -> {
                        wv.loadDataWithBaseURL(
                            content.baseUrl,
                            content.data,
                            content.mimeType,
                            content.encoding,
                            content.historyUrl
                        )
                    }

                    is ExWebContent.Post -> {
                        wv.postUrl(
                            content.url,
                            content.postData
                        )
                    }

                    is ExWebContent.NavigatorOnly -> {
                        // NO-OP
                    }
                }
            }
        }
    }
    // Set the state of the client and chrome client
    // This is done internally to ensure they always are the same instance as the
    // parent Web composable
    client.state = state
    client.navigator = navigator
    chromeClient.state = state
    AndroidView(
        factory = { context ->
            (factory?.invoke(context) ?: WebView(context)).apply {
                onCreated(this)
                this.layoutParams = layoutParams
                state.viewState?.let {
                    this.restoreState(it)
                }
                webChromeClient = chromeClient
                webViewClient = client
            }.also { state.webView = it }
        },
        modifier = modifier,
        onRelease = {
            onDispose(it)
        }
    )
}

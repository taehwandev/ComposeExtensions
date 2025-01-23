package tech.thdev.compose.extensions.web.view.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed class ExWebContent {
    data class Url(
        val url: String,
        val additionalHttpHeaders: Map<String, String> = emptyMap(),
    ) : ExWebContent()

    @Immutable
    data class Data(
        val data: String,
        val baseUrl: String? = null,
        val encoding: String = "utf-8",
        val mimeType: String? = null,
        val historyUrl: String? = null
    ) : ExWebContent()

    @Immutable
    data class Post(
        val url: String,
        val postData: ByteArray
    ) : ExWebContent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Post
            if (url != other.url) return false
            if (!postData.contentEquals(other.postData)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = url.hashCode()
            result = 31 * result + postData.contentHashCode()
            return result
        }
    }

    fun getCurrentUrl(): String? {
        return when (this) {
            is Url -> url
            is Data -> baseUrl
            is Post -> url
            is NavigatorOnly -> throw IllegalStateException("Unsupported")
        }
    }

    @Immutable
    data object NavigatorOnly : ExWebContent()
}

internal fun ExWebContent.withUrl(url: String) = when (this) {
    is ExWebContent.Url -> copy(url = url)
    else -> ExWebContent.Url(url)
}

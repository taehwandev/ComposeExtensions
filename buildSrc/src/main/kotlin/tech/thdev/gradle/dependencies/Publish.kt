package tech.thdev.gradle.dependencies

object Publish {
    private const val isReleaseVersion = false
    val libraryVersion: String
        get() = "1.3.2".takeIf { isReleaseVersion } ?: "1.4.0-alpha03"
    const val description = "Android Compose keyboard state modifier."
    const val publishUrl = "https://thdev.tech/ComposeKeyboardState/"
}
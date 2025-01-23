import org.gradle.api.Plugin
import org.gradle.api.Project
import tech.thdev.gradle.configureComposeAndroid
import tech.thdev.gradle.configureComposeFeature
import tech.thdev.gradle.configureKotlinAndroid

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            configureKotlinAndroid()
            configureComposeFeature()
            configureComposeAndroid()
        }
    }
}

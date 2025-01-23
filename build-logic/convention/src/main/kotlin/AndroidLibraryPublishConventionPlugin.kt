import org.gradle.api.Plugin
import org.gradle.api.Project
import tech.thdev.gradle.configureComposeAndroid
import tech.thdev.gradle.configureComposeFeature
import tech.thdev.gradle.configureKotlinAndroid

class AndroidLibraryPublishConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("maven-publish")
                apply("signing")
            }

        }
    }
}

package tech.thdev.gradle

import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import tech.thdev.gradle.extensions.androidExtension
import tech.thdev.gradle.extensions.findLibrary

fun Project.configureComposeAndroid() {
    androidExtension.apply {
        dependencies {
            implementation(findLibrary("kotlin-collectionsImmutable"))

            implementation(platform(findLibrary("androidx-compose-bom")))
            implementation(findLibrary("androidx-compose-ui"))
            implementation(findLibrary("androidx-compose-foundation"))
            implementation(findLibrary("androidx-compose-runtime"))
            implementation(findLibrary("androidx-compose-ui-tooling-preview"))
            implementation(findLibrary("androidx-compose-animation"))

            implementation(findLibrary("androidx-compose-material3"))

            implementation(findLibrary("androidx-compose-constraintLayout"))

            implementation(findLibrary("androidx-lifecycleRuntimeCompose"))

            "debugRuntimeOnly"(findLibrary("androidx-compose-ui-tooling"))
        }
    }
}

/**
 * Compose Library
 */
fun Project.configureComposeFeature() {
    androidExtension.apply {
        with(plugins) {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        buildFeatures {
            compose = true
        }
    }
}

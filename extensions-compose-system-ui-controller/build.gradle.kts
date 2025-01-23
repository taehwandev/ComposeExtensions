import tech.thdev.gradle.configureComposeFeature

plugins {
    alias(libs.plugins.tech.thdev.android.library)
    alias(libs.plugins.tech.thdev.android.library.publish)
}

val (majorVersion, minorVersion, patchVersion, code) = getVersionInfo()

ext["libraryName"] = "extensions-compose-system-ui-controller"
ext["libraryVersion"] = "$majorVersion.$minorVersion.$patchVersion"
ext["description"] = Publish.DESCRIPTION
ext["url"] = Publish.PUBLISH_URL

setNamespace("compose.extensions.system.ui.controller")

configureComposeFeature()

android {
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // AGP 8.0
    publishing {
        multipleVariants("release") {
            allVariants()
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core)
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
}

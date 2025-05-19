plugins {
    alias(libs.plugins.tech.thdev.android.application)
}
setNamespace("compose.extensions")

android {
    val (majorVersion, minorVersion, patchVersion, code) = getVersionInfo()

    defaultConfig {
        applicationId = "tech.thdev.compose.extensions"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        vectorDrawables.useSupportLibrary = true
        versionCode = code
        versionName = "$majorVersion.$minorVersion.$patchVersion"
        multiDexEnabled = true
    }

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
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.core)
    implementation(libs.google.material)

    implementation(libs.androidx.compose.activity)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.compose.material3)

    implementation(projects.extensionsComposeKeyboardState)

    debugImplementation(libs.androidx.compose.ui.tooling)
}
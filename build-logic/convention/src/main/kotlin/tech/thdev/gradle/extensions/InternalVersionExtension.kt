@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.GradleException
import org.gradle.api.Project
import java.util.Properties

@Suppress("UnusedReceiverParameter")
fun Project.getVersionInfo(): VersionInfo {
    val versionPropsFile = file("${rootDir.absolutePath}/version.properties")

    val majorVersion: String
    val minorVersion: String
    val patchVersion: String
    val versionCode: Int

    if (versionPropsFile.exists()) {
        val versionProps = Properties()
        versionProps.load(versionPropsFile.reader())

        versionCode = versionProps["versionCode"].toString().toInt()
        majorVersion = versionProps["majorVersion"].toString()
        minorVersion = versionProps["minorVersion"].toString()
        patchVersion = versionProps["patchVersion"].toString()
        return VersionInfo(
            majorVersion = majorVersion,
            minorVersion = minorVersion,
            patchVersion = patchVersion,
            versionCode = versionCode,
        )
    } else {
        throw GradleException("version.properties 파일을 찾을 수 없습니다.")
    }
}

data class VersionInfo(
    val majorVersion: String,
    val minorVersion: String,
    val patchVersion: String,
    val versionCode: Int,
)

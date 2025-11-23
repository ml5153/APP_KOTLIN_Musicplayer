import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.parkys.musicplayer"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.parkys.musicplayer"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    applicationVariants.all {
        val kstTimeZone = TimeZone.getTimeZone("Asia/Seoul")

        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss").apply {
            timeZone = kstTimeZone
        }
        val formattedDate = dateFormat.format(Date())

        val flavorName = flavorName?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } ?: ""
        val buildTypeName = buildType.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val vName = versionName
        val vCode = versionCode

        val newFileName = "musicplayer" +
                (if (flavorName.isNotEmpty()) "_$flavorName" else "") +
                "_${buildTypeName}" +
                "_${vName}" +
                "-${vCode}" +
                "_${formattedDate}" +
                ".apk"

        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = newFileName
        }
    }
}

dependencies {
    // core ktx
    implementation(libs.androidx.core.ktx)

    // appcompat
    implementation(libs.androidx.appcompat)

    // material ui
    implementation(libs.material)
    // compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.coil.compose)

    // activity extensions
    implementation(libs.androidx.activity)

    // constraintlayout
    implementation(libs.androidx.constraintlayout)

    // navigation
    implementation(libs.androidx.navigation.runtime.ktx)

    // Block-Common
    implementation(project(":Block-Common:Common-Bom"))

    // Block-Feature
    implementation(project(":Block-Feature:Feature-List"))
    implementation(project(":Block-Feature:Feature-Player"))
    implementation(libs.androidx.navigation.compose)


}
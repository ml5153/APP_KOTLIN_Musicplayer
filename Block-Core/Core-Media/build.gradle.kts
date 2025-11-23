plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")

}

android {
    namespace = "com.parkys.musicplayer.block.core.media"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    // core ktx
    implementation(libs.androidx.core.ktx)

    // appcompat
    implementation(libs.androidx.appcompat)

    // material ui
    implementation(libs.material)

    // activity extensions
    implementation(libs.androidx.activity)

    // constraintlayout
    implementation(libs.androidx.constraintlayout)

    // Block-Common
    implementation(project(":Block-Common:Common-Bom"))

    implementation("androidx.media3:media3-exoplayer:1.8.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.8.0")
    implementation("androidx.media3:media3-ui:1.8.0")
    implementation("androidx.media3:media3-ui-compose:1.8.0")
    implementation("androidx.media3:media3-session:1.8.0")
}
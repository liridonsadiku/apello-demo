// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // ⚙️ KSP (Kotlin Symbol Processing) - Used for annotation processing
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
    // 🏗️ Hilt Dependency Injection - Simplifies DI in Android
    id("com.google.dagger.hilt.android") version libs.versions.hiltAndroid.get() apply false
}
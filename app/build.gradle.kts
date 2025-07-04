plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // 🚀 Plugin Configurations
    // 🔧 Kotlin Symbol Processing (KSP) Plugin - Improves compile-time annotation processing
    id("com.google.devtools.ksp")
    // 💉 Dagger Hilt Plugin - Simplifies Dependency Injection
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.valtech.apollodemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.valtech.apollodemo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
}

dependencies {

    // 🚀 Core Dependencies
    implementation(libs.androidx.core.ktx) // 🏗️ Core KTX for Android utilities
    implementation(libs.androidx.lifecycle.runtime.ktx) // ⚙️ Lifecycle Runtime KTX for state management
    implementation(libs.androidx.activity.compose) // 🖼️ Jetpack Compose support in Activities
    implementation(platform(libs.androidx.compose.bom)) // 🎨 Compose BOM (Bill of Materials)

    // 🖌️ Jetpack Compose UI & Material Design
    implementation(libs.androidx.ui) // 🖌️ Core UI Components
    implementation(libs.androidx.ui.graphics) // 🎨 UI Graphics Rendering
    implementation(libs.androidx.ui.tooling.preview) // 🔍 UI Previews
    implementation(libs.androidx.material3) // 🎨 Material Design 3
    implementation(libs.androidx.material.icons.extended) // 🎨 Extended Material Icons

    // 🧪 Testing Dependencies
    testImplementation(libs.junit) // 🧪 JUnit for Unit Testing
    androidTestImplementation(libs.androidx.junit) // 🧪 AndroidX JUnit
    androidTestImplementation(libs.androidx.espresso.core) // 🧪 Espresso for UI Testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // 🎨 Compose UI Testing
    androidTestImplementation(libs.androidx.ui.test.junit4) // 🧪 Jetpack Compose UI Testing
    debugImplementation(libs.androidx.ui.tooling) // 🛠️ Compose Debug Tooling
    debugImplementation(libs.androidx.ui.test.manifest) // 🛠️ Compose UI Test Manifest
    implementation(libs.androidx.navigation.testing) // 🧪 Navigation Testing Tools

    //.............................................................................................
    // 🚀 Dependency Injection & Networking

    // 💉 Hilt - Dependency Injection
    // 🚀 Core Hilt Library
    implementation(libs.hilt.android)
    // 📦 Hilt Navigation for Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)
    // 🏗️ Hilt Compiler for annotation processing
    ksp(libs.hilt.compiler)

    // 🌍 Retrofit - API Networking
    // 🔌 Retrofit Core for HTTP Requests
    implementation(libs.retrofit)
    // 🔄 JSON Converter (Gson)
    implementation(libs.converter.gson)
    // 📝 Logging Interceptor (Debugging API Calls)
    implementation(libs.logging.interceptor)

    //.............................................................................................
    // ✅ Paging & Room Database Support

    // 📜 Room Database - Local Storage
    ksp(libs.androidx.room.compiler) // ⚙️ Room Compiler (Annotation Processing)
    implementation(libs.androidx.room.ktx) // 🔧 Room KTX Extensions
    implementation(libs.androidx.room.runtime) // 🏗️ Room Database Runtime

    // ✅ Paging - Infinite Scrolling Support
    implementation(libs.androidx.paging.runtime.ktx) // 🔥 Core Paging Library
    implementation(libs.androidx.paging.compose) // ✅ Paging Support for Jetpack Compose

    // ✅ Room-Paging Integration (Fix Missing Dependencies)
    implementation(libs.androidx.room.paging) // 🛠️ Fix missing paging-room issue

    //.............................................................................................
    debugImplementation(libs.leakcanary.android) // 🛠️ LeakCanary - Detect Memory Leaks

    implementation(libs.linphone)
    implementation(libs.androidx.media)


}
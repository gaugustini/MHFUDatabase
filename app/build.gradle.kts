plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.gaugustini.mhfudatabase"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.gaugustini.mhfudatabase"
        minSdk = 23
        targetSdk = 37
        versionCode = 21
        versionName = "1.2.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
        freeCompilerArgs.add("-opt-in=androidx.compose.foundation.ExperimentalFoundationApi")
    }
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Android
    implementation("androidx.core:core-ktx:1.19.0")
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation("androidx.appcompat:appcompat:1.8.0")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2026.08.00"))
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.compose.ui:ui:1.12.0")
    implementation("androidx.compose.foundation:foundation:1.12.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.12.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.12.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.navigation:navigation-compose:2.9.8")
    implementation("androidx.hilt:hilt-navigation-compose:1.4.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")

}

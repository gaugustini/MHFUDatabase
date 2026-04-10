plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.gaugustini.mhfudatabase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.gaugustini.mhfudatabase"
        minSdk = 23
        targetSdk = 36
        versionCode = 14
        versionName = "1.0.3"
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

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Android
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation("androidx.appcompat:appcompat:1.7.1")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2026.03.01"))
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.compose.ui:ui:1.10.6")
    implementation("androidx.compose.ui:ui-tooling-preview:1.10.6")
    debugImplementation("androidx.compose.ui:ui-tooling:1.10.6")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.navigation:navigation-compose:2.9.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.59.2")
    ksp("com.google.dagger:hilt-android-compiler:2.59.2")

}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.batterytestapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.batterytestapplication"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:+")
    implementation("androidx.appcompat:appcompat:+")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.android.gms:play-services-location:21.0.1") {
        exclude("com.android.support")
    }
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.tracing:tracing:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:+")

    androidTestImplementation("androidx.test:runner:+")
    androidTestImplementation("androidx.test:rules:+")
    // Optional -- UI testing with UI Automator
    androidTestImplementation("androidx.test.uiautomator:uiautomator:+")
    // Optional -- UI testing with Compose
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:+")
    androidTestImplementation("androidx.test:core-ktx:+")
    androidTestImplementation ("androidx.test.ext:junit-ktx:+")
}

dependencies {
    implementation(project(":memory:bad"))
    implementation(project(":memory:good"))
    implementation(project(":cpu:bad"))
    implementation(project(":cpu:good"))
}
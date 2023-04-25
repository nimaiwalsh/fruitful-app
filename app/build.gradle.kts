plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.nims.fruitful"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.ui.orNull
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    namespace = "com.nims.fruitful"
}

dependencies {
    // Core
    implementation(libs.material)
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.preference.ktx)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.2.1")
    implementation("androidx.compose.material3:material3:1.0.0-rc01")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.0-rc01")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")

    // Firebase
    implementation(platform ("com.google.firebase:firebase-bom:29.1.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    /* coroutines support for firebase operations */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // Testing
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
}

kapt {
    correctErrorTypes = true
}
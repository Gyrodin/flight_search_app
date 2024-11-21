plugins {
    id("com.android.application")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.flightsearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flightsearch"
        minSdk = 24
        targetSdk = 34
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
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.3"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {

        implementation(platform("androidx.compose:compose-bom:2023.05.01"))
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.core:core-ktx:1.10.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.navigation:navigation-compose:${rootProject.extra["nav_version"]}")


        implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
        implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
        testImplementation(libs.junit.junit)
        testImplementation(libs.junit.junit)
        androidTestImplementation(libs.junit.junit)
        testImplementation("androidx.test:core:1.2.0")
        testImplementation("androidx.test.ext:truth:1.2.0")
        testImplementation("com.google.truth:truth:0.44")
        testImplementation("android.arch.core:core-testing:1.1.0")
        androidTestImplementation(libs.androidx.monitor)
        androidTestImplementation(libs.androidx.monitor)
        ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")

        debugImplementation("androidx.compose.ui:ui-test-manifest")
        debugImplementation("androidx.compose.ui:ui-tooling")
    }

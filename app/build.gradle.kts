plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.nimble.nimblesurveys"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.nimble.nimblesurveys"
        minSdk = 21
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
            buildConfigField ("String", "CLIENT_ID", "\"ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE\"")
            buildConfigField ("String", "CLIENT_SECRET", "\"lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU\"")
        }
        debug {
            buildConfigField ("String", "CLIENT_ID", "\"ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE\"")
            buildConfigField ("String", "CLIENT_SECRET", "\"lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU\"")
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
        buildConfig = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("androidx.activity:activity-ktx:1.7.2")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    // Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.42")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    // Coroutines for asynchronous programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation ("org.mockito:mockito-inline:3.7.7")
    testImplementation ("org.mockito:mockito-core:4.3.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
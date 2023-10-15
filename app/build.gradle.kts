plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    //id ("com.google.gms.google-services")
    id ("kotlinx-serialization")

}

android {
    namespace = "com.apcoding.wallpaperapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apcoding.wallpaperapp"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    allprojects {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Material Scheme
    //implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")


    //Dagger Hilt
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
    implementation ("androidx.test:core-ktx:1.5.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout-compose-android:1.1.0-alpha12")

    //By adding this dependency to your Android project using Jetpack Compose, you're enabling the usage of Google Fonts with the Text component. This allows you to style the text in your Compose-based UI using fonts from the Google Fonts library.
    // Google Downloadable Fonts
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.6.0-alpha04")

    //By adding this dependency to your Android project, you're incorporating AndroidX DataStore into your app. DataStore allows you to store simple data in a more efficient and type-safe manner compared to using traditional SharedPreferences. It is designed to work well with Kotlin and supports various types of data storage including preferences.
    // DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // Firebase
    //implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
   // implementation ("com.google.firebase:firebase-auth-ktx")
   // implementation ("com.google.firebase:firebase-analytics-ktx")
   // implementation ("com.google.firebase:firebase-firestore-ktx:24.7.1")

    // Retrofit 2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //By adding this dependency to your project, you're enabling Retrofit to work seamlessly with Kotlin's serialization library, allowing you to automatically convert your data classes to and from JSON during network requests. This can help reduce boilerplate code and simplify the process of working with APIs.
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0") // Kotlin serialization support for Retrofit

    // KotlinX Serialization
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    // Screenshot
    implementation ("dev.shreyaspatil:capturable:1.0.3")

    // Room components
    implementation ("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    implementation ("androidx.room:room-paging:2.5.2")

    //ispa padhna ha
    // Paging 3.0
    implementation ("androidx.paging:paging-compose:3.2.1")

    //By adding this dependency to your Android project using Jetpack Compose, you're enabling the usage of Coil for loading and displaying images within your Compose-based UI. Coil is known for its simplicity and efficient image loading capabilities.
    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.3")
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.26.3-beta")

    // Splash Api
    implementation ("androidx.core:core-splashscreen:1.1.0-alpha02")

    //Accompanist is a set of libraries that provides helpful extensions for Jetpack Compose. In this case, the "accompanist-systemuicontroller" module likely offers utilities for managing the system UI (such as the status bar and navigation bar) in Compose-based apps. This might include features like setting the status bar color, handling immersive mode, and other system UI-related interactions.
    // Accompanist
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.17.0")

    //By adding this dependency to your Android project using Jetpack Compose, you're enabling the usage of LiveData, an Android architecture component, with your Compose-based UIs. This might include features like observing LiveData and updating Compose UI components in response to LiveData changes.
    // LiveData
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.0-alpha06")

    //icons
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")

}
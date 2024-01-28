plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.ibm.pokemonapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ibm.pokemonapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/api/v2/\"")
        buildConfigField("String", "DREAM_WORLD_IMAGES_URL", "\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/\"")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Define versions
    val coreKtxVersion = "1.12.0"
    val lifecycleVersion = "2.7.0"
    val activityComposeVersion = "1.8.2"
    val composeBomVersion = "2023.08.00"
    val junitVersion = "4.13.2"
    val espressoVersion = "3.5.1"
    val retrofitVersion = "2.9.0"
    val okHttpVersion = "4.10.0"
    val timberVersion = "4.7.1"
    val coroutinesVersion = "1.7.3"
    val daggerHiltVersion = "2.50"
    val hiltNavigationComposeVersion = "1.1.0"
    val coilVersion = "2.5.0"
    val paletteVersion = "1.0.0"
    val mockkVersion = "1.12.3"
    val splashScreenVersion = "1.0.1"

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("junit:junit:$junitVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    implementation("com.squareup.retrofit2:converter-scalars:${retrofitVersion}") // TODO

    // Timber
    implementation("com.jakewharton.timber:timber:$timberVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${coroutinesVersion}")


    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-compiler:2.50")
    kapt("androidx.hilt:hilt-compiler:$hiltNavigationComposeVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    // Coil // TODO
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-svg:$coilVersion")

    implementation("androidx.palette:palette-ktx:$paletteVersion") // TODO

    testImplementation("io.mockk:mockk:${mockkVersion}")

    implementation ("androidx.core:core-splashscreen:$splashScreenVersion")

}

kapt {
    correctErrorTypes = true
}

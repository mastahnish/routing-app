import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}
val routingApiKey: String = gradleLocalProperties(rootDir).getProperty("ROUTING_API_KEY")
android {
    namespace = "pl.jacekrys.routingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "pl.jacekrys.routingapp"
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
        debug {
            buildConfigField(
                "String",
                "BUSRIGHT_BASE_URL",
                "\"https://busright-interview.deno.dev/mobile/routes/\""
            )
            buildConfigField(
                "String",
                "ROUTING_BASE_URL",
                "\"https://api.geoapify.com\""
            )
            buildConfigField(
                "String",
                "ROUTING_API_KEY",
                "\"${routingApiKey}\""
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            buildConfigField(
                "String",
                "BUSRIGHT_BASE_URL",
                "\"https://busright-interview.deno.dev/mobile/routes/\""
            )
            buildConfigField(
                "String",
                "ROUTING_BASE_URL",
                "\"https://api.geoapify.com\""
            )
            buildConfigField(
                "String",
                "ROUTING_API_KEY",
                "\"${routingApiKey}\""
            )
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
        buildConfig = true
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
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.33.1-alpha")
    ksp("androidx.lifecycle:lifecycle-compiler:2.6.1")

    // NAVIGATION
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // DATABASE
    val room_version = "2.5.2"
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // NETWORKING
    val moshi = "1.14.0"
    val retrofit = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.moshi:moshi:$moshi")
    implementation("com.squareup.moshi:moshi-kotlin:$moshi")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshi")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // DEPENDENCY INJECTION
    val koinAndroidVersion = "3.4.3"
    val koinAndroidComposeVersion = "3.4.6"
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
    implementation("io.insert-koin:koin-androidx-compose-navigation:$koinAndroidComposeVersion")

    // MAPS
    implementation("com.google.maps.android:maps-compose:2.14.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // LOGGING
    implementation("com.jakewharton.timber:timber:5.0.1")

    // TEST
    testImplementation("org.amshove.kluent:kluent:1.72")
    testImplementation("io.mockk:mockk:1.13.7")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

}
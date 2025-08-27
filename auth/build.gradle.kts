plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.auth"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    implementation 'org.slf4j:slf4j-api:1.7.29'
//    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1'
//    testImplementation 'androidx.arch.core:core-testing:2.1.0'
//    testImplementation 'app.cash.turbine:turbine:0.7.0'
//    testImplementation 'io.mockk:mockk:1.13.5'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:1.5.0"
//    debugImplementation "androidx.compose.ui:ui-test-manifest:1.5.0-alpha03"
//    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
//    androidTestImplementation 'androidx.fragment:fragment-testing:1.5.3'
//    androidTestImplementation "androidx.arch.core:core-testing:2.1.0"
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.47")
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.47")

    //firebase
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)

    // Retrofit
    implementation(libs.square.retrofit)
    implementation(libs.square.gson)
    implementation(libs.square.okhttp)
    implementation(libs.okhttp.interceptor)

    //compose
//    implementation "androidx.compose.ui:ui:1.5.0"
//    implementation "androidx.compose.material:material:1.5.0"
//    implementation "androidx.compose.ui:ui-tooling-preview:1.5.0"
//    implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"
//    implementation "androidx.activity:activity-compose:1.6.1"
//    implementation "androidx.constraintlayout:constraintlayout-compose:1.0.1"
//    implementation "org.slf4j:slf4j-log4j12:1.7.29"
//    implementation "androidx.tracing:tracing:1.1.0"
    implementation(libs.coil.compose)

    implementation(project(":testing"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:util"))

    //jetpackDatastore
    implementation(libs.androidx.datastore)

    //hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidx.compiler)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.gms)
}

android {
    namespace = "com.ev.greenh"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ev.greenh"
        minSdk = 26
        targetSdk = 35
        versionCode = 18
        versionName = "2.7"

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
    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    flavorDimensions += "default"

    productFlavors {
        create("production") {
            dimension = "default"
            applicationId = "com.ev.greenh"
            buildConfigField("String", "Android_Push_Enviornment", "\"production\"")
            resValue("string", "app_name", "Gardners Hub")
        }
        create("staging") {
            dimension = "default"
            applicationId = "com.ev.greenh.staging"
            buildConfigField("String", "Android_Push_Enviornment", "\"development\"")
            resValue("string", "app_name", "GardnersStaging")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
//    testOptions {
//        unitTests.includeAndroidResources = true
//        unitTests.returnDefaultValues = true
//    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.datastore)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidx.compiler)
    implementation(libs.splash)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.razorpay)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.functions)
    implementation(libs.firebase.appcheck)
    implementation(platform(libs.firebaseBom))
    implementation(libs.androidx.browser)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":auth"))
    implementation(project(":shop:ui"))
    implementation(project(":shop:data"))
    implementation(project(":testing"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:util"))
    implementation(project(":admin"))
    implementation(project(":analytics"))



//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1'


    // Coroutine Lifecycle Scopes
//    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
//    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
//    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1"

    implementation(libs.square.retrofit)
    implementation(libs.square.gson)
    implementation(libs.square.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.androidx.fragment)

    //compose
//    implementation "androidx.compose.ui:ui:1.5.0"
//    implementation "androidx.compose.material:material:1.5.0"
//    implementation "androidx.compose.ui:ui-tooling-preview:1.5.0"
//    implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"
//    implementation "androidx.activity:activity-compose:1.6.1"
//    implementation "androidx.constraintlayout:constraintlayout-compose:1.0.1"
//    implementation "org.slf4j:slf4j-log4j12:1.7.29"
//    implementation "androidx.tracing:tracing:1.1.0"

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.worker)

    implementation("io.coil-kt:coil-compose:2.0.0-rc01")

    implementation("androidx.camera:camera-core:1.3.0-rc01")
    implementation("androidx.camera:camera-camera2:1.3.0-rc01")
    implementation("androidx.camera:camera-lifecycle:1.3.0-rc01")
    implementation("androidx.camera:camera-video:1.3.0-rc01")
    implementation("androidx.camera:camera-view:1.3.0-rc01")
    implementation("androidx.camera:camera-extensions:1.3.0-rc01")
//    implementation 'androidx.activity:activity:1.9.3'

    // Local unit tests
//    testImplementation "androidx.test:core:1.4.0"
//    testImplementation "junit:junit:4.13.2"
//    testImplementation "androidx.arch.core:core-testing:2.1.0"
//    testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1"
//    testImplementation "com.google.truth:truth:1.1.4"
//    testImplementation "org.mockito:mockito-core:5.4.0"
//    testImplementation "org.mockito.kotlin:mockito-kotlin:5.0.0"
//    testImplementation "io.mockk:mockk:1.13.5"
//    testImplementation "app.cash.turbine:turbine:0.7.0"
//    testImplementation "com.squareup.okhttp3:mockwebserver:4.9.1"
//    debugImplementation "androidx.compose.ui:ui-test-manifest:1.0.0-beta05"
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.0-rc01'
//    androidTestImplementation 'androidx.test.espresso:espresso-intents:3.5.0-rc01'

    // Instrumentation tests
//    androidTestImplementation "junit:junit:4.13.2"
//    androidTestImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1"
//    androidTestImplementation "androidx.arch.core:core-testing:2.1.0"
//    androidTestImplementation "com.google.truth:truth:1.1.3"
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test:core-ktx:1.4.0'
//    androidTestImplementation "com.squareup.okhttp3:mockwebserver:4.9.1"
//    androidTestImplementation "io.mockk:mockk-android:1.13.7"
//    androidTestImplementation 'androidx.test:runner:1.3.0'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:1.5.0"
//    androidTestImplementation 'androidx.test.ext:junit-ktx:1.2.0-alpha01'
//    androidTestImplementation 'androidx.fragment:fragment-testing:1.5.3'
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.47")
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.47")

    implementation(libs.androidx.paging)
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.support)

    implementation(libs.androidx.lifecycle.process)
}

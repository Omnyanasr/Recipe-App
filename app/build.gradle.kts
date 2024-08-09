plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")}

android {
    namespace = "com.example.recipeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recipeapp"
        minSdk = 24
        targetSdk = 34
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

}


dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-common-jvm:2.7.0-alpha05")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation ("androidx.browser:browser:1.4.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.12.0")

    //Coroutines
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    val lifecycle_version = "2.4.3"
    val activity_version = "1.2.2"

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.activity:activity-ktx:$activity_version")

    
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")


    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.6.2")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.12.0")

    //Coroutines
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")



    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.activity:activity-ktx:$activity_version")



    implementation ("com.airbnb.android:lottie:5.0.3")



}
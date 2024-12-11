import java.util.regex.Pattern.compile

plugins {

    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.easymeet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easymeet"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.sun.mail:android-mail:1.6.2")
    implementation("com.sun.mail:android-activation:1.6.2")
    compile("com.brevo:brevo:1.0.0")
    implementation("com.google.firebase:firebase-messaging:24.1.0")
    implementation ("com.google.firebase:firebase-core:21.1.1")
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
}
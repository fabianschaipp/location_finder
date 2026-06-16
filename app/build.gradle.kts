plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.locationfinder"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.locationfinder"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(platform("com.adobe.marketing.mobile:sdk-bom:[3.8.0,4.0.0)"))
    implementation("com.adobe.marketing.mobile:places")
    implementation("com.adobe.marketing.mobile:edgeidentity")
    implementation("com.adobe.marketing.mobile:edgeconsent")
    implementation("com.adobe.marketing.mobile:assurance")
    implementation("com.adobe.marketing.mobile:edge")
    implementation("com.adobe.marketing.mobile:userprofile")
    implementation("com.adobe.marketing.mobile:core")
    implementation("com.adobe.marketing.mobile:identity")
    implementation("com.adobe.marketing.mobile:lifecycle")
    implementation("com.adobe.marketing.mobile:signal")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
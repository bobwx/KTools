plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {

    //仅仅在编译的时候起作用，建议总是使用最新版本，值是一个API Level
    compileSdkVersion(29)

    //构建工具的版本，在build-tools中的那些(aapt,dexdump,zipalign,apksigner)，一般是API-Level.x.x
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        ndk {
            abiFilters("arm64-v8a")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    testImplementation("junit:junit:4.13")

    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.anko:anko:0.10.8")

}

repositories {
    jcenter()
    mavenCentral()
    google()
}

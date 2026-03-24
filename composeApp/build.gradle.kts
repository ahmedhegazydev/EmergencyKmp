import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)


//    id("com.google.devtools.ksp")
//    alias(libs.plugins.hilt)    // ✅

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

//            implementation(libs.multiplatform.settings)
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

            // Compose (حسب BOM عندك)
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

            implementation("io.insert-koin:koin-android:4.1.1")
            implementation("io.insert-koin:koin-androidx-compose:4.1.1")

            implementation("androidx.navigation:navigation-compose:2.9.7")
//            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)

            implementation("io.insert-koin:koin-core:4.1.1")
            implementation("io.insert-koin:koin-compose:4.1.1")
            implementation("io.insert-koin:koin-compose-viewmodel:4.1.1")

            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.example.emergencykmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.emergencykmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)

//    implementation("com.google.dagger:hilt-android:2.59")
////    kapt("com.google.dagger:hilt-android-compiler:2.59")
////    add("kspAndroid", "com.google.dagger:hilt-android-compiler:2.51.1")
//
//    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}

compose.desktop {
    application {
        mainClass = "com.example.emergencykmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.example.emergencykmp"
            packageVersion = "1.0.0"
        }
    }
}


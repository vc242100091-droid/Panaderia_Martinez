plugins {
    // 📱 Plugin de aplicación Android
    alias(libs.plugins.android.application)

    // 🎨 Jetpack Compose
    alias(libs.plugins.kotlin.compose)

    // 🔥 Firebase (OBLIGATORIO para que funcione)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.panaderiamartinez"

    // 📱 SDK de compilación
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.panaderiamartinez"

        // 📱 Compatibilidad mínima
        minSdk = 24

        // 📱 Versión objetivo
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            // 🔒 Configuración de Proguard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // ⚙️ Compatibilidad con Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // 🎨 Activar Compose
    buildFeatures {
        compose = true
    }
}

dependencies {

    //////////////////////////////////////////////////////////
    // 📱 ANDROID BASE
    //////////////////////////////////////////////////////////
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    //////////////////////////////////////////////////////////
    // 🎨 JETPACK COMPOSE
    //////////////////////////////////////////////////////////
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //////////////////////////////////////////////////////////
    // 🔥 FIREBASE (CORRECTO)
    //////////////////////////////////////////////////////////

    // 🔥 BOM → controla versiones automáticamente
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // 🔐 Autenticación (login)
    implementation("com.google.firebase:firebase-auth-ktx")

    // 🗂️ Base de datos (Firestore)
    implementation("com.google.firebase:firebase-firestore-ktx")

    //////////////////////////////////////////////////////////
    // 🧪 TESTING
    //////////////////////////////////////////////////////////
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    //////////////////////////////////////////////////////////
    // 🛠 DEBUG
    //////////////////////////////////////////////////////////
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
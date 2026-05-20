// 🔝 Top-level build file (nivel proyecto)

plugins {
    // 📱 Android (no se aplica aquí, solo se declara)
    alias(libs.plugins.android.application) apply false

    // 🎨 Compose
    alias(libs.plugins.kotlin.compose) apply false

    // 🔥 Firebase (Google Services)
    alias(libs.plugins.google.services) apply false
}
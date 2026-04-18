plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.bh4haptool"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.bh4haptool"
        minSdk = 31
        targetSdk = 36
        versionCode = 6
        versionName = "1.4.0"

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
        compose = true
    }
}

dependencies {
    implementation(project(":core:toolkit"))
    implementation(project(":feature:shakedraw"))
    implementation(project(":feature:catchcat"))
    implementation(project(":feature:frisbeegroup"))
    implementation(project(":feature:minesweeper"))
    implementation(project(":feature:tetris"))
    implementation(project(":feature:sokoban"))
    implementation(project(":feature:pomodoro"))
    implementation(project(":feature:quickdecide"))
    implementation(project(":feature:luckywheel"))
    implementation(project(":feature:scoreboard"))
    implementation(project(":feature:turnqueue"))
    implementation(project(":feature:eventcountdown"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

tasks.register("copyAppIcon") {
    val srcImage = rootProject.file("Gemini_Generated_Image_g83407g83407g834.png")
    val dstImage = project.file("src/main/res/mipmap-xxhdpi/my_app_icon.png")
    doLast {
        if (srcImage.exists()) {
            dstImage.parentFile.mkdirs()
            srcImage.copyTo(dstImage, overwrite = true)
        }
    }
}

tasks.matching { it.name.startsWith("preBuild") }.configureEach {
    dependsOn("copyAppIcon")
}

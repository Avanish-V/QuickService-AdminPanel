import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)

}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")

            implementation(compose.components.resources)

            //implementation("io.ktor:ktor-client-core:2.3.0")
//            implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
//            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
            //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
//            implementation("io.ktor:ktor-client-cio:2.3.0")
//            implementation("io.ktor:ktor-client-logging:2.3.0")

            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.6.0-wasm-alpha2"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha03")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-beta02")


            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.io.ktor.ktor.client.serialization)
            implementation(libs.ktor.core)

            implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha06")
            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")

            implementation("io.github.vinceglb:filekit-core:0.5.0")
            implementation("io.github.vinceglb:filekit-compose:0.5.0")

            implementation("network.chaintech:kmp-date-time-picker:1.0.3")
            implementation("io.github.thechance101:chart:Beta-0.0.5")


        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.okhttp)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.1")
        }


    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "me.sample.library.resources"
    generateResClass = always
}
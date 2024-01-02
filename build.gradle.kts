plugins {
    kotlin("jvm") version "1.9.21"
    id("idea")
    kotlin("plugin.serialization") version "1.9.21"
}

group = "homelab"
version = "1.0-SNAPSHOT"

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.github.serpro69:kotlin-faker:1.15.0")
    implementation("org.apache.lucene:lucene-core:9.9.1")
    implementation("org.apache.lucene:lucene-queryparser:9.9.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
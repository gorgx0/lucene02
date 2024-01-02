plugins {
    kotlin("jvm") version "1.9.21"
}

group = "homelab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.lucene:lucene-core:9.9.1")
    implementation("org.apache.lucene:lucene-queryparser:9.9.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
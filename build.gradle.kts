import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.71"
    id("application")

}

application {
    mainClassName = "MainKt"
}

group = "io.github.elena-lyulina.codetracker-gumtree"
version = "1.0-SNAPSHOT"


java.sourceCompatibility = JavaVersion.VERSION_1_8


repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(fileTree("libs"))
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}



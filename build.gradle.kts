import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    id("org.springframework.boot") version "2.7.10" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.0" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.0" apply false
}

group = "com.kl"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {

        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    group = "com.kl.demostep"

    dependencies {
        project(":common")
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    id("org.springframework.boot") version "2.7.10" apply false
    kotlin("plugin.spring") version "1.8.20" apply false
    kotlin("plugin.jpa") version "1.8.20" apply false
    kotlin("plugin.serialization") version "1.8.20" apply false
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
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation("io.awspring.cloud:spring-cloud-aws-messaging:2.4.3")
        implementation("io.awspring.cloud:spring-cloud-aws-autoconfigure:2.4.3")
        implementation("com.amazonaws:aws-java-sdk-stepfunctions:1.12.417")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
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
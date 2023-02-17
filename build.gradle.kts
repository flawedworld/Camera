// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

plugins {
  id("org.sonarqube") version "4.0.0.2929"
}

sonarqube {
  properties {
    property("sonar.projectKey", "flawedworld_grapheneos-camera")
    property("sonar.organization", "flawedworld")
    property("sonar.host.url", "https://sonarcloud.io")
  }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral() 
        //The Maven Central repo is “the largest collection of Java and other open source components” and a default source of libraries for Maven and Gradle “Serving Open Source Components Since 2002”. The vast majority of libraries used in Android apps, outside of Google's own, are fetched from Maven Central.
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
        //classpath ("com.google.gms:google-services:4.4.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

tasks.register("clean", Delete::class) {
delete(rootProject.buildDir)
}
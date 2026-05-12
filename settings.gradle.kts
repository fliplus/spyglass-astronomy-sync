pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots")
	}
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.4"
}

stonecutter {
    create(rootProject) {
        versions("26.1")
        vcsVersion = "26.1"
    }
}

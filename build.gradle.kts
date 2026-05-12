plugins {
    id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT"
}

version = "${property("mod.version")}+${property("deps.minecraft")}"
group = "${property("mod.group")}"

base.archivesName = "${property("mod.id")}"

repositories {
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven")
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }

    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")

    implementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")

    implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric-api")}")
    implementation("maven.modrinth:spyglass-astronomy:${property("deps.spyglass-astronomy")}")

    runtimeOnly("me.djtheredstoner:DevAuth-fabric:${property("deps.devauth")}")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraft", project.property("mod.minecraft"))

        filesMatching("fabric.mod.json") {
            expand(mapOf(
                "version" to project.version,
                "minecraft" to project.property("mod.minecraft")
            ))
        }
    }

    jar {
        from("LICENSE.md")
    }

    withType<JavaCompile>().configureEach {
        options.release = 25
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

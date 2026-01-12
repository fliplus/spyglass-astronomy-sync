plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT"
}

version = "${property("mod.version")}+${stonecutter.current.version}"
group = "${property("mod.group")}"

base.archivesName = "${property("mod.id")}"

repositories {
    maven("https://maven.parchmentmc.org")

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
    minecraft("com.mojang:minecraft:${stonecutter.current.project}")
    mappings(loom.layered() {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric-api")}")
    modImplementation("maven.modrinth:spyglass-astronomy:${property("deps.spyglass-astronomy")}")

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
        options.release = 21
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
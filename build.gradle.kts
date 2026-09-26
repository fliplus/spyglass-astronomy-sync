plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.modstitch.manifests)
    alias(libs.plugins.modstitch.modrepos)
}

base.archivesName = property("mod.id") as String
version = "${property("mod.version")}+${sc.current.version}"

repositories {
    modrinthApi.exclusive()
    curseMaven.exclusive()
    terraformersMC()
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")

    implementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    fun fabricApi(vararg modules: String) =
        modules.forEach { implementation(fabricApi.module(it, property("deps.fabric_api") as String)) }

    fabricApi(
        "fabric-command-api-v2",
        "fabric-networking-api-v1",
    )

    runtimeOnly("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}") {
        exclude(group = "net.fabricmc", module = "fabric-loader")
    }

    implementation("maven.modrinth:spyglass-astronomy:${property("deps.spyglass_astronomy")}")

    implementation("com.terraformersmc:modmenu:${property("deps.modmenu")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

loom {
    runConfigs.all {
        runDirectory = rootProject.file("run")
    }
}

manifests.fabricModJson(sourceSets.main.get()) {
    modId = property("mod.id") as String
    version = property("mod.version") as String
    displayName = property("mod.name") as String
    description = property("mod.description") as String
    authors = (property("mod.authors") as String).split(",")
    sourcesUrl = property("mod.sources") as String
    licenses = listOf(property("mod.license") as String)
    iconPath = "icon.png"

    entrypoint("main", "dev.fliplus.spyglassastronomysync.SpyglassAstronomySync")
    entrypoint("client", "dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncClient")
    entrypoint("server", "dev.fliplus.spyglassastronomysync.SpyglassAstronomySyncServer")
    mixin("${property("mod.id")}.mixins.json")

    dependency("minecraft", REQUIRED, property("mod.version_range") as String)
    dependency("fabricloader", REQUIRED, (property("deps.fabric_loader") as String).substringBeforeLast("."))
    dependency("fabric-api", REQUIRED)
    dependency("spyglass_astronomy", OPTIONAL)

    customData.put(
        "modmenu", mapOf(
            "links" to mapOf(
                "discord" to property("mod.discord")
            )
        )
    )
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

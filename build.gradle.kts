import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    idea
    id("java")
    id("io.github.goooler.shadow") version "8.1.7" // shadowJar (use fork until upstream is updated to support Java 21)
    id("xyz.jpenilla.run-paper") version "2.3.0" // runPaper
}

group = "tech.execsuroot.template" // ToDo: Change to your group
version = "1.0.0"
description = "Template for PaperMC plugin." // ToDo: Change to your plugin's description

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // PaperMC
    maven("https://repo.codemc.org/repository/maven-public/") {
        content { includeGroup("dev.jorel") } // CommandAPI
    }
}

dependencies {
    // PaperMC
    compileOnly("io.papermc.paper", "paper-api", "1.20.6-R0.1-SNAPSHOT")
    // Lombok
    val lombokVersion = "1.18.32"
    compileOnly("org.projectlombok", "lombok", lombokVersion)
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)
    // Config
    implementation("de.exlll", "configlib-yaml", "4.5.0")
    // Command
    val commandVersion = "9.4.2"
    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", commandVersion)
    compileOnly("dev.jorel", "commandapi-annotations", commandVersion)
    annotationProcessor("dev.jorel", "commandapi-annotations", commandVersion)
}

// Setting Java version to 21
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

// Setting IDEA to download sources and javadoc for dependencies to access it in the IDE
idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks {
    runServer {
        version("1.20.6") // ToDo: Change to the version you want to run the server with
    }

    // Setting project-related placeholders in resources
    processResources {
        expand(
            "project_name" to project.name,
            "project_description" to project.description,
            "project_version" to project.version
        )
    }

    shadowJar {
        relocate("de.exlll.configlib", "${project.group}.exlll.configlib")
        relocate("org.snakeyaml", "${project.group}.snakeyaml")
        relocate("dev.jorel.commandapi", "${project.group}.jorel.commandapi")
        mergeServiceFiles()
    }

    // Relocating dependencies to avoid conflicts with other plugins
    create<ShadowJar>("relocatedShadowJar") {
        group = "shadow"
        from(shadowJar.get().archiveFile)
        archiveClassifier.set("relocated")

        relocate("de.exlll.configlib", "${project.group}.exlll.configlib")
        relocate("org.snakeyaml", "${project.group}.snakeyaml")
        relocate("dev.jorel.commandapi", "${project.group}.jorel.commandapi")
        mergeServiceFiles()
    }
}

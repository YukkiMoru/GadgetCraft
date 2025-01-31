plugins {
    kotlin("jvm") version "2.1.10-RC"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.YukkiMoru"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://repo.codemc.io/repository/maven-public/") {
        name = "CodeMC"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.14.1")
    implementation("org.xerial:sqlite-jdbc:3.47.2.0")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("GadgetCraft-$version.jar")
    destinationDirectory.set(layout.buildDirectory.dir("../GadgetCraftServer/plugins"))
}
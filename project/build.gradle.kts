plugins {
    `java-library`
    `maven-publish`
    application
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api(libs.org.ow2.asm.asm)
    api(libs.org.ow2.asm.asm.tree)
    api(libs.org.antlr.antlr4)
    api(libs.org.assertj.assertj.core)
}

group = "io.github.fearnlang"
version = "1.0-SNAPSHOT"
description = "fearnlang"

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

application {
    mainClass.set("io.github.fearnlang.FearnC")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    
    // Include all dependencies in the jar
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    
    // Handles duplicate files in dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Custom task to copy the JAR to ../release directory and rename it
tasks.register<Copy>("copyJarToRelease") {
    dependsOn(tasks.jar)
    from(tasks.jar)
    into(layout.projectDirectory.file("../release"))
    rename { "FearnC.jar" } // Rename the JAR file
}

// Ensure the copy task runs after the build
tasks.build {
    finalizedBy(tasks.named("copyJarToRelease"))
}
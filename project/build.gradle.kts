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
    // ASM for bytecode manipulation
    api(libs.org.ow2.asm.asm)
    api(libs.org.ow2.asm.asm.tree)

    // ANTLR for parsing
    api(libs.org.antlr.antlr4)

    // AssertJ for object assertions and comparison
    api(libs.org.assertj.assertj.core)

    // Jackson for YAML parsing
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.18.2")

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

// Define the task to generate the JAR file
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
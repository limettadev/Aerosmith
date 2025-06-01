plugins {
    id("aerosmith.conventions")
    id("paperclip")
    id("com.gradleup.shadow") version "9.0.0-beta7"
}

dependencies {
    implementation("io.sigpipe:jbsdiff:1.0")
}

sourceSets {
    create("java9")
}

tasks {
    jar {
        archiveClassifier.set("original")
        manifest {
            val agentClass = "dev.limetta.aerosmith.paperclip.Agent"
            attributes(
                "Main-Class" to "dev.limetta.aerosmith.paperclip.Paperclip",
                "Multi-Release" to true,
                "Launcher-Agent-Class" to agentClass,
                "Premain-Class" to agentClass,
            )
        }
    }

    shadowJar {
        val prefix = "dev.limetta.aerosmith.paperclip.libs"
        arrayOf("org.apache", "org.tukaani", "io.sigpipe").forEach { pack ->
            relocate(pack, "$prefix.$pack")
        }

        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")

        into("META-INF/versions/9") {
            from(sourceSets["java9"].output)
        }

        from(project.rootDir.resolve("LICENSE.md")) {
            into("META-INF/license")
            rename { "pandaspigot-LICENSE.md" }
        }
    }
}

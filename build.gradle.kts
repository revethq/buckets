import org.jboss.jandex.IndexWriter
import org.jboss.jandex.Indexer

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.allopen) apply false
    alias(libs.plugins.quarkus) apply false
    alias(libs.plugins.ktlint) apply false
    base
}

buildscript {
    dependencies {
        classpath("io.smallrye:jandex:3.2.0")
    }
}

allprojects {
    group = "com.revet.buckets"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.8.0")
    }

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
            javaParameters.set(true)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // Generate Jandex index for CDI bean discovery in non-application modules
    if (project.name != "web") {
        tasks.register("jandex") {
            description = "Generate Jandex index"
            group = "build"

            dependsOn(tasks.named("classes"))

            doLast {
                val indexer = Indexer()
                val classesDir =
                    layout.buildDirectory
                        .dir("classes/kotlin/main")
                        .get()
                        .asFile

                if (classesDir.exists()) {
                    classesDir
                        .walkTopDown()
                        .filter { it.isFile && it.extension == "class" }
                        .forEach { classFile ->
                            classFile.inputStream().use { indexer.index(it) }
                        }

                    val index = indexer.complete()
                    val metaInfDir =
                        layout.buildDirectory
                            .dir("resources/main/META-INF")
                            .get()
                            .asFile
                    metaInfDir.mkdirs()

                    val jandexFile = File(metaInfDir, "jandex.idx")
                    jandexFile.outputStream().use { IndexWriter(it).write(index) }
                }
            }
        }

        tasks.named("jar") {
            dependsOn("jandex")
        }
    }
}

import org.gradle.api.publish.tasks.GenerateModuleMetadata
import org.jboss.jandex.IndexWriter
import org.jboss.jandex.Indexer

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.allopen) apply false
    alias(libs.plugins.quarkus) apply false
    alias(libs.plugins.ktlint) apply false
    base
    `maven-publish`
    alias(libs.plugins.jreleaser)
}

buildscript {
    dependencies {
        classpath("io.smallrye:jandex:3.2.0")
    }
}

allprojects {
    group = "com.revethq.buckets"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    if (project.name != "web") {
        apply(plugin = "java-library")
        apply(plugin = "maven-publish")
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.8.0")
    }

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
        if (project.name != "web") {
            withJavadocJar()
            withSourcesJar()
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

    if (project.name != "web") {
        tasks.withType<GenerateModuleMetadata> {
            suppressedValidationErrors.add("enforced-platform")
        }

        configure<PublishingExtension> {
            publications {
                create<MavenPublication>("maven") {
                    artifactId = "revet-buckets-${project.name}"
                    from(components["java"])

                    pom {
                        name.set("Revet Buckets - ${project.name}")
                        description.set("Revet Buckets ${project.name} module")
                        url.set("https://github.com/revethq/buckets")
                        inceptionYear.set("2025")

                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }

                        developers {
                            developer {
                                id.set("your-id")
                                name.set("Your Name")
                            }
                        }

                        scm {
                            url.set("https://github.com/revethq/buckets")
                            connection.set("scm:git:git://github.com/revethq/buckets.git")
                            developerConnection.set("scm:git:ssh://git@github.com/revethq/buckets.git")
                        }
                    }
                }
            }

            repositories {
                maven {
                    url = uri(layout.buildDirectory.dir("staging-deploy"))
                }
            }
        }
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

jreleaser {
    project {
        links {
            homepage.set("https://github.com/revethq/buckets")
        }
    }

    release {
        github {
            overwrite.set(true)
        }
    }

    signing {
        active.set(org.jreleaser.model.Active.ALWAYS)
        armored.set(true)
    }

    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active.set(org.jreleaser.model.Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository("core/build/staging-deploy")
                    stagingRepository("persistence-runtime/build/staging-deploy")
                    stagingRepository("provider-s3/build/staging-deploy")
                    stagingRepository("provider-gcs/build/staging-deploy")
                    stagingRepository("provider-azure-blob/build/staging-deploy")
                }
            }
        }
    }
}

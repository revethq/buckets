plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(project(":core"))

    implementation(libs.jakarta.enterprise.cdi.api)
    implementation(libs.jakarta.inject.api)

    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

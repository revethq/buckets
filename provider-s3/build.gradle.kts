plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(project(":core"))

    implementation(enforcedPlatform(libs.quarkus.bom))
    implementation(enforcedPlatform(libs.quarkus.amazon.services.bom))
    implementation(libs.quarkus.amazon.s3)
    implementation(libs.aws.url.connection.client)
    implementation(libs.jakarta.enterprise.cdi.api)
    implementation(libs.jakarta.inject.api)

    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(project(":core"))

    implementation(enforcedPlatform(libs.quarkus.bom))
    implementation(libs.quarkus.hibernate.orm.panache.kotlin)
    implementation(libs.quarkus.arc)
    implementation(libs.jakarta.enterprise.cdi.api)
    implementation(libs.jakarta.inject.api)
    implementation(libs.jakarta.transaction.api)

    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

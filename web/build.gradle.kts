plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.quarkus)
}

dependencies {
    implementation(project(":core"))
    implementation(project(":persistence-runtime"))
    implementation(project(":provider-s3"))
    implementation(project(":provider-gcs"))
    implementation(project(":provider-azure-blob"))

    implementation(enforcedPlatform(libs.quarkus.bom))
    implementation(libs.quarkus.kotlin)
    implementation(libs.quarkus.arc)
    implementation(libs.quarkus.rest)
    implementation(libs.quarkus.rest.jackson)
    implementation(libs.quarkus.hibernate.orm.panache.kotlin)
    implementation(libs.quarkus.jdbc.postgresql)
    implementation(libs.quarkus.smallrye.openapi)
    implementation(libs.quarkus.smallrye.health)
    implementation(libs.quarkus.smallrye.jwt)

    // Revet IAM
    implementation(libs.revet.core)
    implementation(libs.revet.iam.permission)
    implementation(libs.revet.iam.permission.persistence.runtime)
    implementation(libs.revet.iam.permission.web)
    implementation(libs.revet.iam.user)
    implementation(libs.revet.iam.user.persistence.runtime)
    implementation(libs.revet.iam.user.web)

    testImplementation(libs.quarkus.junit5)
    testImplementation(libs.rest.assured)
    testImplementation(libs.mockk)
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("jakarta.ws.rs.ext.Provider")
}

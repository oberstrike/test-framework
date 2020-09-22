plugins {
    kotlin("jvm")
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    val junitJupiterVersion = "5.4.2"

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    implementation("io.github.microutils:kotlin-logging:1.12.0")

    implementation("com.github.dasniko:testcontainers-keycloak:1.4.0")
    implementation(platform("org.testcontainers:testcontainers-bom:1.14.3")) //import bom
    implementation("org.testcontainers:testcontainers:1.14.3")
    implementation("org.testcontainers:junit-jupiter:1.14.3")
    implementation("org.testcontainers:postgresql:1.14.3")
    implementation("org.slf4j:slf4j-simple:1.7.30")

}

ext {
    set("PUBLISH_GROUP_ID", "main")
    set("PUBLISH_ARTIFACT_ID", "testcontainer")
    set("PUBLISH_VERSION", "1.0.0")
}

apply {
    from("../release-jar.gradle.kts")
}
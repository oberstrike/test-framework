plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    val quarkusVersion = "1.8.1.Final"

    implementation(platform("org.testcontainers:testcontainers-bom:1.14.3")) //import bom
    implementation("org.testcontainers:testcontainers:1.14.3")
    implementation("io.github.microutils:kotlin-logging:1.12.0")

    implementation("io.quarkus:quarkus-test-common:$quarkusVersion")
    implementation("io.quarkus:quarkus-junit5:$quarkusVersion")
    implementation("io.quarkus:quarkus-junit5-mockito:$quarkusVersion")
    implementation("io.rest-assured:kotlin-extensions:4.3.1")
    implementation("io.rest-assured:rest-assured:4.3.1")

    api(project(":testcontainer"))

}

ext {
    set("PUBLISH_GROUP_ID", "com.maju.quarkus")
    set("PUBLISH_ARTIFACT_ID", "testcontainer-quarkus")
    set("PUBLISH_VERSION", "1.0.0")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("$buildDir/repo")
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

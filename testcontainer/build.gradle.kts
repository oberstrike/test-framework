import java.util.*

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "1.4.0"
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    val junitJupiterVersion = "5.4.2"

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    implementation("io.github.microutils:kotlin-logging:1.12.0")

    implementation("org.testcontainers:mongodb:1.15.1")
    implementation("com.github.dasniko:testcontainers-keycloak:1.6.0")
    implementation(platform("org.testcontainers:testcontainers-bom:1.15.1")) //import bom
    implementation("org.testcontainers:testcontainers:1.15.1")
    implementation("org.testcontainers:junit-jupiter:1.15.1")
    implementation("org.testcontainers:postgresql:1.15.1")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.mongodb:mongo-java-driver:3.12.7")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

val myGroupId = "com.maju.container"
val myArtifactId = "testcontainer"
val myVersion = "1.0.1"

val dokkaJavadocJar by tasks.creating(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory.get())
    archiveClassifier.set("javadoc")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
}


val pomUrl = "https://github.com/oberstrike/test-framework"
val pomScmUrl = "https://github.com/oberstrike/test-framework"
val pomIssueUrl = "https://github.com/oberstrike/test-framework/issues"
val pomDesc = "https://github.com/oberstrike/test-framework"

val pomLicenseName = "MIT"
val pomLicenseUrl = "https://opensource.org/licenses/mit-license.php"

val pomDeveloperId = "oberstrike"
val pomDeveloperName = "Markus Jürgens"


publishing {
    publications {
        create<MavenPublication>("testcontainer") {
            groupId = myGroupId
            artifactId = myArtifactId
            version = myVersion
            from(components["java"])
            artifact(sourcesJar)
            artifact(dokkaJavadocJar)

            pom {
                packaging = "jar"
                name.set(project.name)
                description.set("A test library")
                url.set(pomUrl)
                scm {
                    url.set(pomScmUrl)
                }
                issueManagement {
                    url.set(pomIssueUrl)
                }
                licenses {
                    license {
                        name.set(pomLicenseName)
                        url.set(pomLicenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(pomDeveloperId)
                        name.set(pomDeveloperName)
                    }
                }
            }
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = !project.version.toString().endsWith("SNAPSHOT")

    setPublications("testcontainer")

    pkg.apply {
        repo = "maven"
        name = myArtifactId
        userOrg = "oberstrike"
        githubRepo = githubRepo
        vcsUrl = pomScmUrl
        description = "A testframework for testcontainer"
        setLabels("kotlin", "faker", "testing")
        setLicenses("MIT")
        desc = description
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssueUrl
        githubReleaseNotesFile = "README.md"

        version.apply {
      //      name = myArtifactId
            desc = pomDesc
            released = Date().toString()
            vcsTag = "v$myVersion"
        }
    }


}

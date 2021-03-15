import java.util.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "1.4.0"
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    val restassured = "4.3.3"

    implementation("io.github.microutils:kotlin-logging:1.12.0")
    implementation("io.rest-assured:kotlin-extensions:$restassured")
    implementation("io.rest-assured:rest-assured:$restassured")
    testImplementation("org.mockito:mockito-core:2.21.0")
    testImplementation("org.mock-server:mockserver-netty:5.11.1")
    testImplementation("com.google.code.gson:gson:2.8.6")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


val myGroupId = "com.maju.rest"
val myArtifactId = "rest"
val myVersion = "1.0.0"

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
        create<MavenPublication>("rest") {
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

    setPublications("rest")

    pkg.apply {
        repo = "maven"
        name = myArtifactId
        userOrg = "oberstrike"
        githubRepo = githubRepo
        vcsUrl = pomScmUrl
        description = "A testframework for testcontainer"
        setLabels("kotlin", "testing")
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
val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}

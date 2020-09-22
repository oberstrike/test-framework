apply(plugin = "maven")

val groupId = extra["PUBLISH_GROUP_ID"] as String
val artifactId = extra["PUBLISH_ARTIFACT_ID"] as String
val myVersion = extra["PUBLISH_VERSION"] as String

val localReleaseDest = "${buildDir}/release/${myVersion}"

val bitBucketUrl = "git:releases://git@bitbucket.org/oberstrike/test-framework.git"
val bitBucketUser = "oberstrike"
val bitbucketPassword = "test"

tasks.register("uploadArchives", Upload::class) {

    val url = "$bitBucketUrl/releases"

    repositories {
        withConvention(MavenRepositoryHandlerConvention::class) {
            mavenDeployer {
                pom {
                    groupId = groupId
                    artifactId = artifactId
                    version = myVersion
                }

                println(url)


                withGroovyBuilder {
                    "repository"("url" to uri(url)) {
                        "authentication"("userName" to bitBucketUser, "password" to bitbucketPassword)
                    }

                }

            }
        }
        println("this $this")
    }
}


tasks.register("generateRelease") {
    doLast {
        println("Release $version can be found at $localReleaseDest/")
        println("Release $version zipped can be found $buildDir/$artifactId-release-$version.zip")
    }

    dependsOn("uploadArchives", "zipRelease")

}
tasks.register("zipRelease", Zip::class) {
    from(localReleaseDest) {
        destinationDirectory.set(buildDir)
        archiveFileName.set("$artifactId-release-$myVersion.zip")
    }
}


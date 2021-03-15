plugins {
    base
    kotlin("jvm") version "1.4.20" apply false
}


allprojects {
    group = "com.maju"
    version = "1.0"

    repositories {
        jcenter()
    }

}


subprojects {


    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        println("Configuring KotlinCompile  $name in project ${project.name}...")
        kotlinOptions {
            languageVersion = "1.3"
            apiVersion = "1.3"
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }


    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

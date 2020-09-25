package com.maju


import mu.KotlinLogging
import org.testcontainers.containers.GenericContainer

val logger = KotlinLogging.logger("DockerTestResource")

class DockerTestResourceLoggingHandler :
    AbstractDockerTestResource.IOnStartHandler,
    AbstractDockerTestResource.IOnStopHandler,
    AbstractDockerTestResource.IOnConfigCreatedHandler {

    override fun onStart(container: GenericContainer<*>) {
        println("Starting ${container.getDockerImageName()}")
        logger.info { "Starting ${container.getDockerImageName()}" }
    }

    override fun onStop(container: GenericContainer<*>) {
        println("Stopping ${container.getDockerImageName()}")
        logger.info { "Stopping ${container.getDockerImageName()}" }
    }

    override fun onConfigCreated(config: Map<String, String>) {
        println("Starting with config $config")
    }

}
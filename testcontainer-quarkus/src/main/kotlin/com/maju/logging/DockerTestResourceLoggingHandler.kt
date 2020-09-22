package com.maju.logging


import mu.KotlinLogging
import org.testcontainers.containers.GenericContainer

val logger = KotlinLogging.logger("DockerTestResource")

class DockerTestResourceLoggingHandler : IOnStartHandler, IOnStopHandler {

    override fun onStart(container: GenericContainer<*>) {
        println("Starting ${container.getDockerImageName()}")
        logger.info { "Starting ${container.getDockerImageName()}" }
    }

    override fun onStop(container: GenericContainer<*>) {
        println("Stopping ${container.getDockerImageName()}")
        logger.info { "Stopping ${container.getDockerImageName()}" }
    }

}
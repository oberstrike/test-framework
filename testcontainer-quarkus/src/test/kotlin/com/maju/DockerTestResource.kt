package com.maju

import com.maju.logging.DockerTestResourceLoggingHandler
import com.maju.container.postgres.PostgresContainerCreatorImpl
import com.maju.container.postgres.PostgresOnContainerCreateHandler
import com.maju.logging.AbstractDockerTestResource
import com.maju.logging.IOnStartHandler
import com.maju.logging.IOnStopHandler


class DockerTestResource : AbstractDockerTestResource() {

    private val loggingHandler = DockerTestResourceLoggingHandler()

    override val containerCreators = listOf(
        PostgresContainerCreatorImpl(
            PostgresOnContainerCreateHandler.default(
                port = 5555,
                dbName = "ranked",
                username = "admin",
                password = "admin"
            )
        )
    )

    override val onStartHandlers: List<IOnStartHandler> = listOf(loggingHandler)

    override val onStopHandlers: List<IOnStopHandler> = listOf(loggingHandler)

    override fun start(): MutableMap<String, String> {
        val config = super.start()
        println("Config: $config")
        return config
    }

}
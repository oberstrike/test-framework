package com.maju

import com.maju.quarkus.DockerTestResourceLoggingHandler
import com.maju.container.postgres.PostgresContainerCreatorImpl
import com.maju.container.postgres.PostgresOnContainerCreateHandler
import com.maju.quarkus.AbstractDockerTestResource
import com.maju.quarkus.IOnStartHandler
import com.maju.quarkus.IOnStopHandler


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

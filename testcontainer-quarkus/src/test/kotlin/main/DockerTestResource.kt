package main

import main.logging.DockerTestResourceLoggingHandler
import main.container.postgres.PostgresContainerCreatorImpl
import main.container.postgres.PostgresOnContainerCreateHandler


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

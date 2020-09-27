package com.maju.docker

import com.maju.postgres.PostgresContainerCreatorImpl
import com.maju.postgres.PostgresOnContainerCreateHandler
import org.junit.jupiter.api.Test

class ExampleResourceTest {

    companion object {
        const val PORT = 5555
        const val DBNAME = "quarkus"
        const val USERNAME = "postgres"
        const val PASSWORD = "postgres123"
    }

    @Test
    fun test() {
        val resource = TestDockerPostgresResource()
        val config = resource.start()
        assert(config.isNotEmpty())
        assert(config["quarkus.datasource.username"] == USERNAME)
        assert(config["quarkus.datasource.password"] == PASSWORD)
        assert(config["quarkus.datasource.jdbc.url"] == "jdbc:postgresql://localhost:$PORT/$DBNAME")
        resource.stop()
    }

    class TestDockerPostgresResource : AbstractDockerTestResource() {

        private val loggingHandler = DockerTestResourceLoggingHandler()

        override val containerCreators = listOf(
            PostgresContainerCreatorImpl(
                PostgresOnContainerCreateHandler.default(
                    port = PORT,
                    dbName = DBNAME,
                    username = USERNAME,
                    password = PASSWORD
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
}
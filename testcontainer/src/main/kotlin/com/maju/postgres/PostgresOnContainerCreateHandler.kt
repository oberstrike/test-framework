package com.maju.postgres

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.maju.AbstractContainerCreator


class PostgresOnContainerCreateHandler(
    private val config: IPostgresConfig
) : AbstractContainerCreator.IContainerCreateHandler<KPostgreSQLContainer> {

    companion object {
        private const val USERNAME = "postgres"
        private const val PASSWORD = "postgres"
        private const val PORT = 5434
        private const val DB_NAME = "queue"
        private const val DB_KIND = "postgresql"

        fun create(
            config: IPostgresConfig = PostgresConfigImpl(null, mutableMapOf())
        ) = PostgresOnContainerCreateHandler(config)
    }

    override fun onContainerCreate(container: KPostgreSQLContainer): KPostgreSQLContainer {
        return container.apply {
            withDatabaseName(config.dbName)
            withUsername(config.username)
            withPassword(config.password)
            withExposedPorts(config.port)
            if (config.initScriptPath != null) withInitScript(config.initScriptPath)
            withCreateContainerCmdModifier { cmd ->
                cmd.withHostConfig(
                    HostConfig.newHostConfig().withPortBindings(
                        PortBinding.parse("${config.port}:${5432}")
                    )
                )
            }
        }
    }

    override fun createConfig(): MutableMap<String, String> {
        return mutableMapOf(
            "quarkus.datasource.jdbc.url" to "jdbc:postgresql://localhost:${config.port}/${config.dbName}",
            "quarkus.datasource.password" to config.password,
            "quarkus.datasource.username" to config.username,
            "quarkus.datasource.db-kind" to DB_KIND
        ).apply { putAll(config.properties()) }
    }

    interface IPostgresConfig {
        var username: String
        var password: String
        var port: Int
        var dbName: String
        var initScriptPath: String?
        fun properties(): MutableMap<String, String>
    }

    data class PostgresConfigImpl(
        override var initScriptPath: String?,
        var properties: MutableMap<String, String>,
        override var username: String = USERNAME,
        override var password: String = PASSWORD,
        override var port: Int = PORT,
        override var dbName: String = DB_NAME
    ) : IPostgresConfig {

        override fun properties(): MutableMap<String, String> {
            return properties
        }
    }

}

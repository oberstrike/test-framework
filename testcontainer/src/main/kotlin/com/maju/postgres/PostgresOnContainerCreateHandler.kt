package com.maju.postgres

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.maju.AbstractContainerCreator


class PostgresOnContainerCreateHandler(
    private val defaultConfig: IPostgresConfig
) : AbstractContainerCreator.IContainerCreateHandler<KPostgreSQLContainer> {

    companion object {
        private const val USERNAME = "postgres"
        private const val PASSWORD = "postgres"
        private const val PORT = 5434
        private const val DB_NAME = "queue"
        private const val DB_KIND = "postgresql"

        fun default(
            dbName: String = DB_NAME,
            username: String = USERNAME,
            password: String = PASSWORD,
            port: Int = PORT,
            initScriptPath: String? = null,
            pConfig: MutableMap<String, String> = mutableMapOf()
        ) = PostgresOnContainerCreateHandler(
            DefaultPostgresConfig(
                username = username,
                dbName = dbName,
                password = password,
                port = port,
                initScriptPath = initScriptPath,
                pConfig = pConfig
            )
        )
    }

    override fun onContainerCreate(container: KPostgreSQLContainer): KPostgreSQLContainer {
        return container.apply {
            withDatabaseName(defaultConfig.dbName)
            withUsername(defaultConfig.username)
            withPassword(defaultConfig.password)
            withExposedPorts(defaultConfig.port)
            if (defaultConfig.initScriptPath != null)
                withInitScript(defaultConfig.initScriptPath)
            withCreateContainerCmdModifier { cmd ->
                cmd.withHostConfig(
                    HostConfig.newHostConfig().withPortBindings(
                        PortBinding.parse("${defaultConfig.port}:${5432}")
                    )
                )
            }
        }
    }

    override fun createConfig(): MutableMap<String, String> {
        return mutableMapOf(
            "quarkus.datasource.jdbc.url" to "jdbc:postgresql://localhost:${defaultConfig.port}/${defaultConfig.dbName}",
            "quarkus.datasource.password" to defaultConfig.password,
            "quarkus.datasource.username" to defaultConfig.username,
            "quarkus.datasource.db-kind" to DB_KIND
        ).apply { putAll(defaultConfig.withConfig()) }
    }

    interface IPostgresConfig {
        var username: String
        var password: String
        var port: Int
        var dbName: String
        var initScriptPath: String?
        fun withConfig(): MutableMap<String, String>
    }

    data class DefaultPostgresConfig(
        override var username: String,
        override var password: String,
        override var port: Int,
        override var dbName: String,
        override var initScriptPath: String?,
        var pConfig: MutableMap<String, String>
    ) : IPostgresConfig {
        override fun withConfig(): MutableMap<String, String> {
            return pConfig
        }
    }

}

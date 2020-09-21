package main.container.postgres

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import main.container.AbstractContainerCreator


class PostgresOnContainerCreateHandler(
    private val defaultConfig: IPostgresDefaultConfig
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
            initScriptPath: String? = null
        ) = PostgresOnContainerCreateHandler(
            object : IPostgresDefaultConfig {
                override var dbName: String = dbName
                override var password: String = password
                override var username: String = username
                override var port: Int = port
                override var initScriptPath: String? = initScriptPath
            }
        )
    }

    override fun onContainerCreate(container: KPostgreSQLContainer) {
        container.apply {
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
        )
    }

    interface IPostgresDefaultConfig {
        var username: String
        var password: String
        var port: Int
        var dbName: String
        var initScriptPath: String?
    }
}

package com.maju.postgres

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

class PostgresOnContainerCreateHandlerTest {


    @Test
    fun customConfigTest() {
        val pair = "url" to "https://on.oberstrike.de"
        val customConfig = mutableMapOf(pair)

        val mock = mock<PostgresOnContainerCreateHandler.IPostgresConfig> {
            on { properties() } doReturn customConfig
        }


        val postgresOnContainerCreateHandler = PostgresOnContainerCreateHandler(mock)
        val config = postgresOnContainerCreateHandler.createConfig()

        verify(mock, times(1)).properties()

        val url = config[pair.first]
        assert(url == pair.second)
    }

    @Test
    fun defaultConfigTest() {
        val username = "quarkus.datasource.password" to "postgres"
        val password = "quarkus.datasource.username" to "postgres"
        val datasource = "quarkus.datasource.jdbc.url" to "jdbc:postgresql://localhost:5434/queue"
        val defaultConfigs = mapOf(username, password, datasource)

        val postgresOnContainerCreateHandler = PostgresOnContainerCreateHandler.create()

        val config = postgresOnContainerCreateHandler.createConfig()
        for (defaultConfig in defaultConfigs) {
            val value = config[defaultConfig.key]
            assert(value == defaultConfig.value)
        }

    }

    @Test
    fun test() {
        val postgresOnContainerCreateHandler = PostgresOnContainerCreateHandler.create()

        val postgresContainer = KPostgreSQLContainer()

        assert(postgresContainer.username == "test")


        postgresOnContainerCreateHandler.onContainerCreate(postgresContainer)

        assert(postgresContainer.username == "postgres")
    }
}

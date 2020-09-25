package com.maju.postgres

import com.maju.AbstractContainerCreator
import org.testcontainers.containers.PostgreSQLContainer

const val postgresContainerName = "postgres"

class KPostgreSQLContainer : PostgreSQLContainer<KPostgreSQLContainer>(postgresContainerName)

private val defaultPostgresHandler = PostgresOnContainerCreateHandler.default()

class PostgresContainerCreatorImpl(
    postgresOnContainerCreatedHandler: PostgresOnContainerCreateHandler = defaultPostgresHandler
) :
    AbstractContainerCreator<KPostgreSQLContainer>() {

    override val container: KPostgreSQLContainer = KPostgreSQLContainer()

    override val onContainersCreateHandlers: List<IOnContainerCreateHandler<KPostgreSQLContainer>> = listOf(
        postgresOnContainerCreatedHandler
    )

    override val onConfigCreateHandlers: List<IOnConfigCreateHandler<KPostgreSQLContainer>> = listOf(
        postgresOnContainerCreatedHandler
    )


}
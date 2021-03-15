package com.maju.postgres

import com.maju.AbstractContainerCreator


private val defaultPostgresHandler = PostgresOnContainerCreateHandler.create()

private val defaultPostgresContainer = KPostgreSQLContainer()

class PostgresContainerCreatorImpl(
    postgresOnContainerCreatedHandler: IContainerCreateHandler<KPostgreSQLContainer> = defaultPostgresHandler,
    postgresContainer: KPostgreSQLContainer = defaultPostgresContainer
) :
    AbstractContainerCreator<KPostgreSQLContainer>() {

    override val container: KPostgreSQLContainer = postgresContainer

    override val onContainersCreateHandlers: List<IOnContainerCreateHandler<KPostgreSQLContainer>> = listOf(
        postgresOnContainerCreatedHandler
    )

    override val onConfigCreateHandlers: List<IOnConfigCreateHandler<KPostgreSQLContainer>> = listOf(
        postgresOnContainerCreatedHandler
    )


}

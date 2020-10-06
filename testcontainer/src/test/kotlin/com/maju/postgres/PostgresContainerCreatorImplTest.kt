package com.maju.postgres

import org.junit.jupiter.api.Test

class PostgresContainerCreatorImplTest {

    @Test
    fun defaultPostgresContainerCreatorImplCreateConfigTest() {
        val postgresContainerCreatorImpl = PostgresContainerCreatorImpl()

        val config = postgresContainerCreatorImpl.createConfig()
        assert(config.isNotEmpty())
    }

}

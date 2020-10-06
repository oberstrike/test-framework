package com.maju.postgres

import org.testcontainers.containers.PostgreSQLContainer

open class KPostgreSQLContainer(image: String = "postgres") : PostgreSQLContainer<KPostgreSQLContainer>(image)



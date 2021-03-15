package com.maju.mongodb

import com.maju.AbstractContainerCreator
import org.testcontainers.containers.MongoDBContainer

private val defaultHandler = MongoDBContainerCreateHandler()

class MongoDBContainerCreatorImpl : AbstractContainerCreator<MongoDBContainer>() {

    override val container: MongoDBContainer
        get() = MongoDBContainer("mongo:latest")

    override val onContainersCreateHandlers: List<IOnContainerCreateHandler<MongoDBContainer>>
        get() = listOf(defaultHandler)

    override val onConfigCreateHandlers: List<IOnConfigCreateHandler<MongoDBContainer>>
        get() = listOf(defaultHandler)

}

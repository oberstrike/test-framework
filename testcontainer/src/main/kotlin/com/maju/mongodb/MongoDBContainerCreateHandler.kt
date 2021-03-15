package com.maju.mongodb

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.maju.AbstractContainerCreator
import org.testcontainers.containers.MongoDBContainer

class MongoDBContainerCreateHandler : AbstractContainerCreator.IContainerCreateHandler<MongoDBContainer> {

    override fun createConfig(): MutableMap<String, String> {
        return mutableMapOf()
    }

    override fun onContainerCreate(container: MongoDBContainer): MongoDBContainer {
        return container
    }
}

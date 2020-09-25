package com.maju

import org.testcontainers.containers.GenericContainer

abstract class AbstractContainerCreator<T : GenericContainer<T>> : IContainerCreator<T> {

    abstract val container: T

    open val onContainersCreateHandlers: List<IOnContainerCreateHandler<T>> = emptyList()

    open val onConfigCreateHandlers: List<IOnConfigCreateHandler<T>> = emptyList()

    override fun createContainer(): GenericContainer<T> {
        onContainersCreateHandlers.forEach {
            it.onContainerCreate(container)
        }
        return container
    }

    override fun createConfig(): MutableMap<String, String> {
        if (onConfigCreateHandlers.isEmpty()) return mutableMapOf()

        return onConfigCreateHandlers.map {
            it.createConfig()
        }.reduce { acc, config ->
            acc.apply {
                putAll(config)
            }
        }
    }

    interface IOnContainerCreateHandler<T> {
        fun onContainerCreate(container: T)
    }

    interface IOnConfigCreateHandler<T> {
        fun createConfig(): MutableMap<String, String>
    }

    interface IContainerCreateHandler<T> :
        IOnConfigCreateHandler<T>,
        IOnContainerCreateHandler<T>


}




package com.maju.container

import org.testcontainers.containers.GenericContainer


interface IContainerCreator<T : GenericContainer<T>?> {

    fun createContainer(): GenericContainer<T>

    fun createConfig(): MutableMap<String, String>
}


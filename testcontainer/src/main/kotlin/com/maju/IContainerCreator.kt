package com.maju

import org.testcontainers.containers.GenericContainer


interface IContainerCreator<T : GenericContainer<T>?> {

    fun createContainer(): GenericContainer<T>

    fun createConfig(): MutableMap<String, String>
}


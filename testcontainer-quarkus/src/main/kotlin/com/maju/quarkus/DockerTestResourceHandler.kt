package com.maju.quarkus

import org.testcontainers.containers.GenericContainer

interface IOnStartHandler {
    fun onStart(container: GenericContainer<*>)
}

interface IOnStopHandler {
    fun onStop(container: GenericContainer<*>)
}
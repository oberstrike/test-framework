package com.maju.logging

import org.testcontainers.containers.GenericContainer

interface IOnStartHandler {
    fun onStart(container: GenericContainer<*>)
}

interface IOnStopHandler {
    fun onStop(container: GenericContainer<*>)
}
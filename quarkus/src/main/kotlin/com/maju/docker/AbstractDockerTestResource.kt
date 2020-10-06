package com.maju.docker


import com.maju.IContainerCreator
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.GenericContainer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


abstract class AbstractDockerTestResource : QuarkusTestResourceLifecycleManager {

    open val containerCreators = emptyList<IContainerCreator<*>>()

    open val onStartHandlers = emptyList<IOnStartHandler>()

    open val onConfigCreatedHandler = emptyList<IOnConfigCreatedHandler>()

    open val onStopHandlers = emptyList<IOnStopHandler>()

    private val containers = mutableListOf<GenericContainer<*>>()

    override fun start(): MutableMap<String, String> {
        containerCreators.map { creator ->
            creator.createContainer()
        }.forEach(this::startContainer)

        if (containerCreators.isEmpty()) return mutableMapOf()

        return containerCreators
            .map { it.createConfig() }
            .reduce { acc, mutableMap ->
                acc.apply {
                    putAll(mutableMap)
                }
            }.apply {
                onConfigCreatedHandler.forEach { handler ->
                    handler.onConfigCreated(this)
                }
            }
    }

    @Throws(InterruptedException::class)
    private fun startContainer(container: GenericContainer<out GenericContainer<*>?>) {
        if (!containers.contains(container)) containers.add(container)
        container.start()
        onStartHandlers.forEach { it.onStart(container) }
    }


    override fun stop() {
        containers.forEach { container ->
            if (container.isRunning()) container.stop()
            onStopHandlers.forEach {
                it.onStop(container)
            }
        }
    }

    interface IOnStartHandler {
        fun onStart(container: GenericContainer<*>)
    }

    interface IOnStopHandler {
        fun onStop(container: GenericContainer<*>)
    }

    interface IOnConfigCreatedHandler {
        fun onConfigCreated(config: Map<String, String>)
    }

}
package com.maju.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KeycloakContainerCreatorTest {

    private val adminUsername = "test"
    private val adminPassword = "test"
    private val port = 8888
    private val realmName = "test"
    private val pairs = "config" to "custom"

    @Test
    fun testDefaultConfig() {
        val keycloakContainerCreator = KeycloakDefaultContainerCreatorImpl()
        var container = keycloakContainerCreator.container
        Assertions.assertNotNull(container)

        val keycloakContainer = container
        assert(!keycloakContainer.exposedPorts.contains(8181))

        container = keycloakContainerCreator.createContainer() as KeycloakContainer
        assert(container.exposedPorts.contains(8181))

        println(keycloakContainer)

    }


    @Test
    fun customConfigTest() {
        val keycloakContainerCreator = getKeycloakContainerCreator()
        val keycloakContainer = keycloakContainerCreator.createContainer() as KeycloakContainer
        assert(keycloakContainer.exposedPorts.contains(port))
        assert(keycloakContainer.adminUsername == adminUsername)
        assert(keycloakContainer.adminPassword == adminPassword)

        val config = keycloakContainerCreator.createConfig()
        val value = config[pairs.first]
        assert(value == pairs.second)


    }

    @Test
    fun customConfigTest_negativ() {
        val keycloakContainerCreator = getKeycloakContainerCreator()
        val keycloakContainer = keycloakContainerCreator.createContainer() as KeycloakContainer
        assert(!keycloakContainer.exposedPorts.contains(8181))
        assert(keycloakContainer.adminUsername != "admin")
        assert(keycloakContainer.adminPassword != "admin")
    }


    @Test
    fun createConfigTest() {
        val keycloakContainerCreator = getKeycloakContainerCreator()
        val config = keycloakContainerCreator.createConfig()
        assert(config.isNotEmpty())
        val url = config["quarkus.oidc.auth-server-url"]
        assert(url == "http://localhost:$port/auth/realms/$realmName")

    }

    @Test
    fun createConfigClientTest() {
        val keycloakContainerCreator = getKeycloakContainerCreator()
        val config = keycloakContainerCreator.createConfig()
        assert(config.isNotEmpty())
        val url = config["${String::class.java.packageName}.${String::class.java.simpleName}/mp-rest/url"]
        assert(url != null)
        assert(url == "http://localhost:$port/auth/realms/$realmName/protocol/openid-connect")
    }

    private fun getKeycloakContainerCreator(): KeycloakDefaultContainerCreatorImpl {
        return KeycloakDefaultContainerCreatorImpl(
            KeycloakOnContainerCreateHandler.default(
                pPort = port,
                pAdminUsername = adminUsername,
                pAdminPassword = adminPassword,
                pRealmName = realmName,
                pListOfClass = listOf(String::class.java),
                pConfig = mutableMapOf(pairs)
            )
        )
    }

}
package com.maju.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KeycloakContainerCreatorTest {


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
        val keycloakContainerCreator = KeycloakDefaultContainerCreatorImpl(
            KeycloakContainerCreateHandler.default(
                pPort = 8888,
                pAdminUsername = "test",
                pAdminPassword = "test"
            )
        )
        val keycloakContainer = keycloakContainerCreator.createContainer() as KeycloakContainer
        assert(keycloakContainer.exposedPorts.contains(8888))
        assert(keycloakContainer.adminUsername == "test")
        assert(keycloakContainer.adminPassword == "test")

    }

}
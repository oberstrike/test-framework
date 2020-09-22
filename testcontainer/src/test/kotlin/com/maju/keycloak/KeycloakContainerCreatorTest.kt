package com.maju.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import com.maju.container.keycloak.KeycloakDefaultContainerCreatorImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KeycloakContainerCreatorTest {


    private val keycloakContainerCreator = KeycloakDefaultContainerCreatorImpl()

    @Test
    fun testDefaultConfig() {
        var container = keycloakContainerCreator.container
        Assertions.assertNotNull(container)


        val keycloakContainer = container
        assert(!keycloakContainer.exposedPorts.contains(8181))

        container = keycloakContainerCreator.createContainer() as KeycloakContainer
        assert(container.exposedPorts.contains(8181))

        println(keycloakContainer)

    }


}
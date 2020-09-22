package com.maju.container.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import com.maju.container.AbstractContainerCreator


private val defaultKeycloakHandler = KeycloakContainerCreateHandler(
    KeycloakContainerCreateHandler.defaultConfig()
)

class KeycloakDefaultContainerCreatorImpl :
    AbstractContainerCreator<KeycloakContainer>() {

    override val container: KeycloakContainer = KeycloakContainer("quay.io/com.maju.keycloak/com.maju.keycloak:latest")

    override val onContainersCreateHandlers: List<IOnContainerCreateHandler<KeycloakContainer>> = listOf(
        defaultKeycloakHandler
    )

    override val onConfigCreateHandlers: List<IOnConfigCreateHandler<KeycloakContainer>> = listOf(defaultKeycloakHandler)

}


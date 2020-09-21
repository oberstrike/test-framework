package main.container.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import main.container.AbstractContainerCreator


private val defaultKeycloakHandler = KeycloakContainerCreateHandler(
    KeycloakContainerCreateHandler.defaultConfig()
)

class KeycloakDefaultContainerCreatorImpl :
    AbstractContainerCreator<KeycloakContainer>() {

    override val container: KeycloakContainer = KeycloakContainer("quay.io/keycloak/keycloak:latest")

    override val onContainersCreateHandlers: List<IOnContainerCreateHandler<KeycloakContainer>> = listOf(
        defaultKeycloakHandler
    )

    override val onConfigCreateHandlers: List<IOnConfigCreateHandler<KeycloakContainer>> = listOf(defaultKeycloakHandler)

}


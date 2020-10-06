package com.maju.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import com.maju.AbstractContainerCreator


private val defaultKeycloakHandler = KeycloakOnContainerCreateHandler.default()


class KeycloakDefaultContainerCreatorImpl(
    keycloakCreateHandler: IContainerCreateHandler<KeycloakContainer> = defaultKeycloakHandler
) :
    AbstractContainerCreator<KeycloakContainer>() {

    override val container: KeycloakContainer = KeycloakContainer("quay.io/keycloak/keycloak:latest")

    override val onContainersCreateHandlers: List<IOnContainerCreateHandler<KeycloakContainer>> = listOf(
        keycloakCreateHandler
    )

    override val onConfigCreateHandlers: List<IOnConfigCreateHandler<KeycloakContainer>> =
        listOf(keycloakCreateHandler)

}


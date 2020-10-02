package com.maju.keycloak

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.maju.AbstractContainerCreator
import com.maju.util.put
import dasniko.testcontainers.keycloak.KeycloakContainer


class KeycloakContainerCreateHandler(
    private val keycloakContainerConfig: IKeycloakContainerConfig
) : AbstractContainerCreator.IContainerCreateHandler<KeycloakContainer> {

    private var realmName = keycloakContainerConfig.realmName
    private var adminUsername = keycloakContainerConfig.adminUsername
    private var adminPassword = keycloakContainerConfig.adminPassword
    private var realmImportFile: String? = keycloakContainerConfig.realmImportFile
    private var port = keycloakContainerConfig.port
    private var listOfClass: List<Class<*>> = keycloakContainerConfig.listOfClass

    companion object {
        const val AUTH_SERVER_HOST = "localhost"

        private const val REALM_NAME = "quarkus"
        private const val ADMIN_USERNAME = "admin"
        private const val ADMIN_PASSWORD = "admin"

        //   "/imports/realm-export.json"
        private const val KEYCLOAK_PORT = 8181

        fun default(
            pRealmName: String = REALM_NAME,
            pAdminUsername: String = ADMIN_USERNAME,
            pAdminPassword: String = ADMIN_PASSWORD,
            pRealmImportFile: String? = null,
            pPort: Int = KEYCLOAK_PORT,
            pConfig: MutableMap<String, String> = mutableMapOf(),
            pListOfClass: List<Class<*>> = emptyList()
        ) = KeycloakContainerCreateHandler(
            object : IKeycloakContainerConfig {
                override var realmName: String = pRealmName
                override var adminUsername: String = pAdminUsername
                override var adminPassword: String = pAdminPassword
                override var realmImportFile: String? = pRealmImportFile
                override var port: Int = pPort
                override val listOfClass: List<Class<*>> = pListOfClass

                override fun withConfig(): MutableMap<String, String> {
                    return pConfig
                }
            })
    }


    override fun onContainerCreate(container: KeycloakContainer) {
        println("Starting keycloak-Container: http://$AUTH_SERVER_HOST:$port")
        container.withAdminUsername(adminUsername)
            .withAdminPassword(adminPassword)
            .withRealmImportFile(realmImportFile)
            .withExposedPorts(port)
            .withCreateContainerCmdModifier { cmd ->
                cmd.withHostConfig(
                    HostConfig.newHostConfig().withPortBindings(PortBinding.parse("$port:8080"))
                )
            }
    }


    override fun createConfig(): MutableMap<String, String> {
        val result = mutableMapOf<String, String>()
        val all = listOfClass.map {
            "${it.`package`.name}.${it.simpleName}/mp-rest/url" to
                    "http://localhost:$port/auth/realms/$realmName/protocol/openid-connect"
        }

        result.putAll(all)
        result.put("quarkus.oidc.auth-server-url" to "http://localhost:$port/auth/realms/$realmName")

        return result

    }


    interface IKeycloakContainerConfig {
        val realmName: String
        val adminUsername: String
        val adminPassword: String
        val realmImportFile: String?
        val port: Int
        val listOfClass: List<Class<*>>

        fun withConfig(): MutableMap<String, String>
    }


}


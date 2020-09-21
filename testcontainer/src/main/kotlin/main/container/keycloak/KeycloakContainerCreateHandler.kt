package main.container.keycloak

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import dasniko.testcontainers.keycloak.KeycloakContainer
import main.container.AbstractContainerCreator
import main.util.put


class KeycloakContainerCreateHandler(
    keycloakContainerConfig: IKeycloakContainerConfig
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

        fun defaultConfig(
            realmName: String = REALM_NAME,
            adminUsername: String = ADMIN_USERNAME,
            adminPassword: String = ADMIN_PASSWORD,
            realmImportFile: String? = null,
            port: Int = KEYCLOAK_PORT,
            config: MutableMap<String, String> = mutableMapOf(),
            listOfClass: List<Class<*>> = emptyList()
        ) = object : IKeycloakContainerConfig {
            override var realmName: String = realmName
            override var adminUsername: String = adminUsername
            override var adminPassword: String = adminPassword
            override var realmImportFile: String? = realmImportFile
            override var port: Int = port
            override val listOfClass: List<Class<*>> = listOfClass

            override fun withConfig(): MutableMap<String, String> {
                return config
            }
        }
    }


    override fun onContainerCreate(container: KeycloakContainer) {
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
            "${it.`package`.name}.${it.simpleName}/mp-rest/url=" to
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


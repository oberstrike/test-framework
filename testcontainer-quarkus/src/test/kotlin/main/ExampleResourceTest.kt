package main

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import main.DockerTestResource
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(DockerTestResource::class)
class ExampleResourceTest {

    @Test
    fun testHelloEndpoint() {
        assert(true)

    }

}
package com.xy.poi

import com.xy.poi.config.SpringProfiles
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@ActiveProfiles(value = [SpringProfiles.TESTING])
@Testcontainers
open class IntegrationTestConfig {

    // workaround to deal with Testcontainers SELF type if more complex config is needed
    class KPostgresContainer(imageName: DockerImageName) : PostgreSQLContainer<KPostgresContainer>(imageName)
}
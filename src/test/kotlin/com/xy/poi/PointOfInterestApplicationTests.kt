package com.xy.poi

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class PointOfInterestApplicationTests: IntegrationTestConfig() {

    @Test
    fun contextLoads() {
    }
}

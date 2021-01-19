package com.xy.poi.domain.repository

import com.xy.poi.IntegrationTestConfig
import com.xy.poi.TestData
import com.xy.poi.domain.GeoPoint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.stream.StreamSupport

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointOfInterestRepositoryIntegrationTest(
    @Autowired
    val repository: PointOfInterestRepository
): IntegrationTestConfig() {

    @Test
    fun findAllShouldReturnAllItems() {
        val result = repository.findAll()

        Assertions.assertEquals(
            TestData.Database.TOTAL_ITEMS,
            StreamSupport.stream(result.spliterator(), false).count()
        )
    }

    @Test
    fun findByProximityShouldReturnAllItems() {
        val result = repository.findByProximity(
            GeoPoint(0),
            GeoPoint(0),
            TestData.Database.MAX_COORDINATE_VALUE,
            null
        )

        Assertions.assertEquals(
            TestData.Database.TOTAL_ITEMS,
            StreamSupport.stream(result.spliterator(), false).count()
        )
    }

    @Test
    fun findByProximityShouldReturnTargets() {
        val targets = listOf(
            TestData.PointOfInterest.LANCHONETE,
            TestData.PointOfInterest.JOALHERIA,
            TestData.PointOfInterest.PUB,
            TestData.PointOfInterest.SUPERMERCADO
        ).map { target -> target.uuid }
        val result = repository.findByProximity(
            GeoPoint(20),
            GeoPoint(10),
            10,
            null
        )

        Assertions.assertEquals(
            4,
            StreamSupport.stream(result.spliterator(), false).count()
        )
        Assertions.assertTrue(
            result.get()
                .map { item -> item.id }
                .allMatch {
                        item -> targets.contains(item)
                }
        )
    }
}
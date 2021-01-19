package com.xy.poi

import com.xy.poi.domain.GeoPoint
import com.xy.poi.domain.Location
import com.xy.poi.domain.PointOfInterest
import com.xy.poi.domain.PointOfInterestName
import com.xy.poi.domain.repository.PointOfInterestRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.client.Traverson.TraversalBuilder
import org.springframework.hateoas.server.core.TypeReferences.PagedModelType
import org.springframework.test.web.reactive.server.WebTestClient
import java.net.URI
import org.springframework.web.client.RestTemplate

import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PoiClientIntegrationTest(
    @LocalServerPort
    val port: Int,
    @Autowired
    restTemplateBuilder: RestTemplateBuilder
): IntegrationTestConfig() {

    val restTemplate = restTemplateBuilder.build()

    companion object {
        const val SERVICE_URI = "http://localhost:%s/"
    }

    @Test
    fun postPointOfterest() {
        restTemplate.exchange(
            URI.create(java.lang.String.format(SERVICE_URI, port)),
            HttpMethod.POST,
            HttpEntity(
                PointOfInterest(
                    name = PointOfInterestName("Novo Bar"),
                    location = Location(
                        x = GeoPoint(20),
                        y = GeoPoint(20)
                    )
                )
            ),
            EntityModel::java.class
        )
    }

    @Test
    fun discoverPointsOfInterestSearch() {
        val traverson = Traverson(URI.create(java.lang.String.format(SERVICE_URI, port)), MediaTypes.HAL_JSON)

        val builder: TraversalBuilder =
            traverson.follow(
                PointOfInterest.Rest.COLLECTION,
                "search",
                PointOfInterestRepository.Relations.FIND_BY_PROXIMITY
            )

        val parameters: MutableMap<String, Any> = HashMap()
        parameters[PointOfInterestRepository.Parameters.REFERENCE_X] = "20"
        parameters[PointOfInterestRepository.Parameters.REFERENCE_Y] = "10"
        parameters[PointOfInterestRepository.Parameters.DISTANCE_IN_METERS] = "10"

        val resources: PagedModel<EntityModel<PointOfInterest>>? =
            builder.withTemplateParameters(parameters).toObject(object : PagedModelType<EntityModel<PointOfInterest>>() {}) //

        Assertions.assertEquals(
            4L,
            resources!!.metadata!!.totalElements
        )
    }
}
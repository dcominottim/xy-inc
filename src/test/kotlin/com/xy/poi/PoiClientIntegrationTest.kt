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
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.client.Traverson.TraversalBuilder
import org.springframework.hateoas.server.core.TypeReferences.PagedModelType
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PoiClientIntegrationTest(
    @LocalServerPort
    val port: Int,
    @Autowired
    restTemplateBuilder: RestTemplateBuilder
): IntegrationTestConfig() {

    val restTemplate = restTemplateBuilder.build()


    private fun baseUriBuilder(): UriComponentsBuilder {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
    }

    @Test
    fun postPointOfInterest() {
        val result: ResponseEntity<EntityModel<PointOfInterest>> = restTemplate.exchange(
            baseUriBuilder()
                .path(PointOfInterest.Rest.COLLECTION)
                .build().toUri(),
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
            object: ParameterizedTypeReference<EntityModel<PointOfInterest>>() {}
        )

        Assertions.assertEquals(HttpStatus.CREATED, result.statusCode)
    }

    @Test
    fun getPointsOfInterest() {
        val traverson = Traverson(baseUriBuilder().build().toUri(), MediaTypes.HAL_JSON)

        val builder: TraversalBuilder =
            traverson.follow(
                PointOfInterest.Rest.COLLECTION
            )

        val resources: PagedModel<EntityModel<PointOfInterest>>? =
            builder.toObject(object : PagedModelType<EntityModel<PointOfInterest>>() {})

        Assertions.assertEquals(
            7L,
            resources!!.metadata!!.totalElements
        )
    }

    @Test
    fun discoverPointsOfInterestSearch() {
        val traverson = Traverson(baseUriBuilder().build().toUri(), MediaTypes.HAL_JSON)

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
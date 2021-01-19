package com.xy.poi.domain.repository

import com.xy.poi.domain.GeoPoint
import com.xy.poi.domain.PointOfInterest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import java.util.*
import javax.validation.constraints.PositiveOrZero

@RepositoryRestResource(collectionResourceRel = PointOfInterest.Rest.COLLECTION, path = PointOfInterest.Rest.COLLECTION)
interface PointOfInterestRepository: PagingAndSortingRepository<PointOfInterest, UUID> {

    object Relations {
        const val FIND_BY_PROXIMITY = "findByProximity"
    }

    object Parameters {
        const val REFERENCE_X = "referenceX"
        const val REFERENCE_Y = "referenceY"
        const val DISTANCE_IN_METERS = "distanceInMeters"
    }

    @RestResource(rel = Relations.FIND_BY_PROXIMITY)
    @Query(value = """
        SELECT * FROM point_of_interest poi
        WHERE (
            Sqrt(
                Power(Abs(:#{#referenceX.value} - poi.x), 2) + 
                Power(Abs(:#{#referenceY.value} - poi.y), 2)
            )
        ) <= :#{#distanceInMeters}
    """, nativeQuery = true)
    fun findByProximity(
        @Param(Parameters.REFERENCE_X) referenceX: GeoPoint,
        @Param(Parameters.REFERENCE_Y) referenceY: GeoPoint,
        @Param(Parameters.DISTANCE_IN_METERS) @PositiveOrZero distanceInMeters: Int,
        pageable: Pageable?
    ): Page<PointOfInterest>
}
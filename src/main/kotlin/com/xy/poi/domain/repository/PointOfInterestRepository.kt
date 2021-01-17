package com.xy.poi.domain.repository

import com.xy.poi.domain.GeoPoint
import com.xy.poi.domain.Location
import com.xy.poi.domain.PointOfInterest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*
import javax.validation.constraints.PositiveOrZero

@RepositoryRestResource(collectionResourceRel = PointOfInterest.Rest.COLLECTION, path = PointOfInterest.Rest.COLLECTION)
interface PointOfInterestRepository: PagingAndSortingRepository<PointOfInterest, UUID> {

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
        @Param("referenceX") referenceX: GeoPoint,
        @Param("referenceY") referenceY: GeoPoint,
        @Param("distanceInMeters") @PositiveOrZero distanceInMeters: Int,
        pageable: Pageable?
    ): Page<PointOfInterest>
}
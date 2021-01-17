package com.xy.poi.domain.converter

import com.xy.poi.domain.GeoPoint
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

@Service
class LocationInputConverter: Converter<String, GeoPoint> {

    override fun convert(source: String): GeoPoint? {
        return GeoPoint(source.toInt())
    }
}
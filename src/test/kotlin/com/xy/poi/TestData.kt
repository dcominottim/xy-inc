package com.xy.poi

import java.util.*

class TestData {

    object Database {

        const val TOTAL_ITEMS = 7L
        const val MAX_COORDINATE_VALUE = Int.MAX_VALUE
    }

    enum class PointOfInterest(val uuid: UUID) {

        LANCHONETE(UUID.fromString("6bb7faf6-dd4a-436f-a734-68c51f9e11df")),
        POSTO(UUID.fromString("73846e3d-a9b4-411a-9f81-1cba680a4d7d")),
        JOALHERIA(UUID.fromString("20f8c92c-fe36-4308-9797-081d5ad261bc")),
        FLORICULTURA(UUID.fromString("bccc686b-5e57-484c-89b5-334d294f7c11")),
        PUB(UUID.fromString("10841d6c-51cb-4864-aed4-d17ed01b5c1d")),
        SUPERMERCADO(UUID.fromString("0f0393fc-226d-444d-b2c6-185894bd79f5")),
        CHURRASCARIA(UUID.fromString("20f54b7f-b84f-43d6-bafd-ebb9b1bd61d7"))
    }
}
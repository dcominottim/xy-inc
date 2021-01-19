rootProject.name = "xy_poi_app"

pluginManagement {
    val springCloudContractVersion: String by extra

    plugins {
        id("org.springframework.cloud.contract") version springCloudContractVersion
    }
}

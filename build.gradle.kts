import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

buildscript {
    val springCloudContractVersion: String by extra

    dependencies {
        classpath("org.springframework.cloud:spring-cloud-contract-gradle-plugin:${springCloudContractVersion}")
        classpath("org.springframework.cloud:spring-cloud-contract-spec-kotlin:${springCloudContractVersion}")
    }
}

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    // the version comes from settings.gradle.kts due to limitation in the Spring Cloud Contract Kotlin DSL plugin
    id("org.springframework.cloud.contract")
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
}

group = "com.xy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2020.0.0"
extra["testcontainersVersion"] = "1.15.1"
// this is needed because of a bugfix that has just landed after a comment I made in a Spring bug report about
// classes with a single-arg constructor and how Spring REST/HATEOAS handle them.
// for reference, see https://github.com/spring-projects/spring-data-rest/issues/1926
extra["springDataVersion"] = "2020.0.4-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.data:spring-data-rest-hal-explorer")
    implementation("org.postgresql:postgresql:42.2.18")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:jdbc")
    contractTestImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    contractTestImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    contractTestImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.springframework.data:spring-data-bom:${property("springDataVersion")}")
    }
}

sourceSets {
    getByName("contractTest").java.srcDirs("${project.buildDir}/generated-test-sources/contractTest")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    val testType: String? by project

    when (testType) {
        "unit" -> {
            exclude("**/ContractVerifierTest.*")
            exclude("**/*IntegrationTest.*")
            exclude("**/*IntegrationSpec.*")
        }
    }
}

contracts {
    baseClassForTests.value("com.xy.poi.ContractTestConfig")
    testFramework.value(org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5)
    failOnNoContracts.value(false)
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
}

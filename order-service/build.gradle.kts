plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.2.1"
    id("io.micronaut.test-resources") version "4.2.1"
}

version = "0.1"
group = "com.ordermanagement"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.persistence:jakarta.persistence-api")
    
    compileOnly("org.projectlombok:lombok")
    
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.yaml:snakeyaml")
    
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("io.micronaut.flyway:micronaut-flyway")
}

application {
    mainClass.set("com.ordermanagement.Application")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    useJUnitPlatform()
}

micronaut {
    version("4.2.1")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.ordermanagement.*")
    }
}

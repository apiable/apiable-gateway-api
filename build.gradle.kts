import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
	group = "io.apiable"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_19

	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
	}

}

plugins {
	val kotlinPlugin = "1.9.20" // https://kotlinlang.org/docs/gradle.html
	kotlin("jvm") version kotlinPlugin
	kotlin("plugin.allopen") version kotlinPlugin
	kotlin("plugin.serialization") version kotlinPlugin
	kotlin("kapt") version kotlinPlugin
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "19"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

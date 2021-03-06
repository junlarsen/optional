import org.jetbrains.dokka.Platform
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("org.jetbrains.dokka") version "1.4.10.2"
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("maven")
    id("maven-publish")
    id("signing")
}

group = "org.llvm4j"
version = "0.1.1-SNAPSHOT"

kotlin.explicitApi()

val isStaging = true
val isCI = System.getenv("CI") == "true"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.31")
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.4.31")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    dokkaHtml.configure {
        outputDirectory.set(file("$buildDir/javadoc"))
        moduleName.set("optional")

        dokkaSourceSets.configureEach {
            skipDeprecated.set(false)
            includeNonPublic.set(false)
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            displayName.set("JVM")
            platform.set(Platform.jvm)
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/llvm4j/optional/blob/master/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
            jdkVersion.set(8)
            noStdlibLink.set(false)
            noJdkLink.set(false)
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
    dependsOn(tasks.dokkaHtml)
}

publishing {
    publications {
        create<MavenPublication>("sonatype") {
            from(components["kotlin"])
            artifact(sourcesJar)
            artifact(javadocJar)

            repositories {
                maven {
                    url = if (isStaging) {
                        uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    } else {
                        uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    }

                    credentials {
                        username = System.getenv("DEPLOY_USERNAME")
                        password = System.getenv("DEPLOY_PASSWORD")
                    }
                }
            }

            pom {
                name.set("optional")
                description.set("Optional<T> and Result<T, E> types for Kotlin")
                url.set("https://github.com/llvm4j/optional")

                licenses {
                    license {
                        name.set("LLVM Exception")
                        url.set("https://foundation.llvm.org/relicensing/LICENSE.txt")
                    }
                }

                developers {
                    developer {
                        id.set("supergrecko")
                        name.set("Mats Larsen")
                        email.set("me@supergrecko.com")
                    }
                }

                scm {
                    connection.set("scm:git:ssh://github.com/llvm4j/optional.git")
                    developerConnection.set("scm:git:ssh://git@github.com:llvm4j/optional.git")
                    url.set("https://github.com/llvm4j/optional")
                }
            }
        }

        signing {
            signing {
                if (isCI) {
                    val signingKey: String? by project
                    val signingPassword: String? by project

                    useInMemoryPgpKeys(signingKey, signingPassword)
                } else {
                    useGpgCmd()
                }
                sign(publishing.publications["sonatype"])
            }
        }
    }
}

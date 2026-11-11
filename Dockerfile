# syntax=docker/dockerfile:1

################################################################################
# Build the application
################################################################################

FROM eclipse-temurin:17-jdk-jammy AS package

WORKDIR /build

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml .
COPY src/ src/

# Copy locally-built internal JARs
COPY libs/common-service-0.0.1-SNAPSHOT.jar /tmp/common-service.jar
COPY libs/LocationService-0.0.1-SNAPSHOT.jar /tmp/locationservice.jar

# Install local JARs and build BookingService using the SAME Maven repository
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw install:install-file \
        -Dfile=/tmp/common-service.jar \
        -DgroupId=com.hopin \
        -DartifactId=common-service \
        -Dversion=0.0.1-SNAPSHOT \
        -Dpackaging=jar && \
    ./mvnw install:install-file \
        -Dfile=/tmp/locationservice.jar \
        -DgroupId=com.hopin \
        -DartifactId=locationservice \
        -Dversion=0.0.1-SNAPSHOT \
        -Dpackaging=jar && \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar


################################################################################
# Extract Spring Boot layers
################################################################################

FROM package AS extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted


################################################################################
# Final runtime image
################################################################################

FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "-cp", ".", "org.springframework.boot.loader.launch.JarLauncher"]
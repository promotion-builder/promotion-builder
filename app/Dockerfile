FROM bellsoft/liberica-openjdk-alpine:17 AS builder
WORKDIR /gradle
ARG SENTRY_AUTH_TOKEN
ENV SENTRY_AUTH_TOKEN ${SENTRY_AUTH_TOKEN}
COPY gradlew build.gradle settings.gradle ./
COPY gradle/ gradle/
RUN ./gradlew build -x test --parallel --continue 2> /dev/null || true
COPY . .
RUN ["./gradlew", "clean", "build", "--no-daemon"]

FROM bellsoft/liberica-openjre-alpine:17
EXPOSE 8080
WORKDIR /app
COPY --from=builder /gradle/build/libs/*.jar ./app.jar
CMD exec java -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -Dspring.profiles.active=prod $JAVA_OPTS -jar app.jar

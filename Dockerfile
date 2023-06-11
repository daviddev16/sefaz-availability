
FROM maven:3.6.0-jdk-11-slim AS build

COPY src /home/user/src
COPY pom.xml /home/user/pom.xml

RUN mvn -f /home/user/pom.xml clean package

FROM eclipse-temurin:11.0.12_7-jdk
COPY --from=build /home/user/target/disponibilidade-sefaz-latest.jar /usr/local/lib/disponibilidade-sefaz-latest.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/disponibilidade-sefaz-latest.jar", "--space-id=<space_id>", "--webhook-key=<webhook_key>", "--fetch-time=<fetch_time>"]
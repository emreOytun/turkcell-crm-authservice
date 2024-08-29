FROM maven:3.8.3-openjdk-17 as build
EXPOSE 8086
WORKDIR /app

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

RUN mkdir -p /root/.m2 && \
    echo "<?xml version='1.0' encoding='UTF-8'?><settings xmlns='http://maven.apache.org/SETTINGS/1.2.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd'><activeProfiles><activeProfile>github</activeProfile></activeProfiles><profiles><profile><id>github</id><repositories><repository><id>central</id><url>https://repo1.maven.org/maven2</url></repository><repository><id>github</id><url>https://maven.pkg.github.com/emreOytun/turkcell-crm-core</url><snapshots><enabled>true</enabled></snapshots></repository></repositories></profile></profiles><servers><server><id>github</id><username>${GITHUB_USERNAME}</username><password>${GITHUB_TOKEN}</password></server></servers></settings>" > /root/.m2/settings.xml

COPY . /app/

RUN mvn clean package -s /root/.m2/settings.xml -DskipTests
FROM openjdk:17-jdk
COPY --from=build /app/target/authservice-0.0.1-SNAPSHOT.jar /app/authservice.jar
CMD ["java", "-jar", "/app/authservice.jar"]
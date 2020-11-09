FROM adoptopenjdk/openjdk11:alpine
MAINTAINER jack jackdking <jackdking@foxmail.com>

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/jwt-spring-security-demo.jar

ENTRYPOINT ["java", "-jar", "/usr/share/jwt-spring-security-demo.jar"]

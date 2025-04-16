FROM openjdk
ARG JAR_FILE=target/*.jar
COPY ./target/receipt-processor-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY target/atencionesmedicas.jar atencionesmedicas.jar
COPY target/classes/walletcloud /walletcloud
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "atencionesmedicas.jar"]

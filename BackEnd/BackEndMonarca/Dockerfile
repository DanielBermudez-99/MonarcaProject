# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Copiar el archivo JAR de la aplicación Spring Boot al contenedor
COPY target/BackEndMonarca-0.0.1-SNAPSHOT.jar java-app.jar

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java","-jar","java-app.jar"]

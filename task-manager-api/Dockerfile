FROM openjdk:oracle
WORKDIR /app
COPY target/task-manager-1.0.0.jar task-manager.jar
EXPOSE 8080
CMD ["java", "-jar", "task-manager.jar"]
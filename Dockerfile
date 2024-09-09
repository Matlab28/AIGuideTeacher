# Use the official Gradle image as the build environment
FROM gradle:7.3.3-jdk17 as build

# Set working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and settings files
COPY gradlew ./
COPY gradle /gradle

# Copy build.gradle and settings.gradle
COPY build.gradle settings.gradle ./

# Copy project source code
COPY src ./src

# Run the build
RUN chmod +x gradlew && ./gradlew build --no-daemon

# Use the official Amazon Corretto image as the runtime environment
FROM amazoncorretto:17

# Set the working directory in the runtime container
WORKDIR /app

# Copy the built jar from the build stage to the runtime stage
COPY --from=build /app/build/libs/*.jar AITeacher-0.0.1-SNAPSHOT.jar

# Set the startup command to run the jar
ENTRYPOINT ["java", "-jar", "AITeacher-0.0.1-SNAPSHOT.jar"]



#FROM gradle:7.3.3-jdk17 AS build
#
#WORKDIR /app
#
#COPY build.gradle settings.gradle ./
#COPY gradlew ./
#COPY gradle /gradle
#
#RUN ./gradlew build --no-daemon
#
#COPY src ./src
#
#RUN ./gradlew build --no-daemon
#
#FROM amazoncorretto:17
#
#WORKDIR /app
#
#COPY --from=build /app/build/libs/*.jar AITeacher-0.0.1-SNAPSHOT.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "AITeacher-0.0.1-SNAPSHOT.jar"]

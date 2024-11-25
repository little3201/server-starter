# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Add Maintainer Info
LABEL maintainer="little3201@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8768 available to the world outside this container
EXPOSE 8768

# Add the application's jar to the container
ADD server-starter-0.1.0.jar .

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/server-starter-0.1.0.jar","--spring.profiles.active=dev"]
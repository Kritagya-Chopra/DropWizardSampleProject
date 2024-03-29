# Use a base image with Java 17 installed
FROM amazoncorretto:17-alpine-jdk
# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY ./target/ProductMicroservice-1.0-SNAPSHOT.jar /app

COPY ./config.yml /app

# Expose the port that your Dropwizard application listens on
EXPOSE 8080

# Command to run your Dropwizard application
CMD ["java","-jar","ProductMicroservice-1.0-SNAPSHOT.jar","server","config.yml"]


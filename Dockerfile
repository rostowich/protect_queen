#Clone the source code repository from Github
FROM alpine/git
WORKDIR /apps
RUN git clone https://github.com/rostowich/protect_queen.git 

#Install maven to build and install the application. It will generate a jar file. This jar file is the application 
FROM maven:3.5-jdk-8-alpine
WORKDIR /apps
COPY --from=0 /apps/protect_queen /apps 
RUN mvn install -DskipTests

#Install Java 8 to start the application
FROM openjdk:8-jre-alpine
WORKDIR /apps
COPY --from=1 /apps/target/protect_queen-0.0.1-SNAPSHOT.jar /apps
CMD java -jar protect_queen-0.0.1-SNAPSHOT.jar
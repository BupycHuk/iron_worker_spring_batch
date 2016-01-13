FROM iron/java

WORKDIR /app
ADD build/libs/ /app/jar/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","build/libs/gs-batch-processing-0.1.0.jar"]
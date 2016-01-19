FROM bupychuk/spring-batch

WORKDIR /app
ADD . /app

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","build/libs/gs-batch-processing-0.1.0.jar"]
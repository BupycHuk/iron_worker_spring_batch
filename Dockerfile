FROM iron/java

WORKDIR /app
ADD . /app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp","build/libs/*","io.iron.springbatchworker.IronWorkerJobRunner"]
# Spring batch worker

This library makes opportunity to use spring batch applications in iron worker

This library uses payload to get path to Job configuration file to read job identifier and job parameters.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Read more about [Spring batch](http://docs.spring.io/spring-batch/trunk/reference/html/index.html)

You can see examples [here](https://github.com/BupycHuk/iron_worker_spring_batch/tree/master/examples)

## Getting started

### 1. prepare local files

Edit payload file

Payload file has next structure:
```json
{
    "jobPath": "[PATH TO JOB CONFIG XML FILE]",
    "jobIdentifier": "[JOB IDENTIFIER]",
    "parameters": {
        "[FIRST JOB PARAMETER KEY]": "[FIRST JOB PARAMETER VALUE]",
        "[SECOND JOB PARAMETER KEY]": "[SECOND JOB PARAMETER KEY]",
        ...
        "[LAST JOB PARAMETER KEY]": "[LAST JOB PARAMETER KEY]"
    }
}
```

Import [Jar file](https://github.com/BupycHuk/iron_worker_spring_batch/releases)

### 2. Build including the dependencies:

You can run Spring batch app using IronWorkerJobRunner
```
java  -cp "JAR_FILE.jar:DEPENDENCIES" io.iron.springbatchworker.IronWorkerJobRunner
```

Or you can make changes to your current app

```
public class App {
	public static void main(String[] args) {
		...
		SpringWorkerHelper springWorkerHelper = SpringWorkerHelper.fromArgs(args);
        String[] springConfig = {springWorkerHelper.getPayload().getJobPath()};
        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
        JobExecution execution = springWorkerHelper.launchJob(context);
        ...
	}
}

```

### 3. Test locally

Now run it to test it out:

```sh
docker run --rm  -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" -v "$PWD":/worker -w /worker bupychuk/spring-batch java  -cp "JAR_FILE.jar:DEPENDENCY_FILES" io.iron.springbatchworker.IronWorkerJobRunner
```

or

```sh
docker run --rm  -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" -v "$PWD":/worker -w /worker bupychuk/spring-batch java  -jar "JAR_FILE.jar"
```


The PAYLOAD_FILE environment variable is passed in to your worker automatically and tells you
where the payload file is. Our [client libraries](http://dev.iron.io/worker/libraries/) help you load the special environment variables automatically.

The YOUR_ENV_VAR environment variable is your custom environment variable. There can
be any number of custom environment variables and they can be anything.

Now that it works, we know it will work on IronWorker.

### 4. Package your code

Let's package it up inside a Docker image and upload it to a Docker Registry. Copy the [Dockerfile](https://github.com/BupycHuk/iron_worker_spring_batch_example/blob/master/GithubRepositoryReader/Dockerfile) from this repository
and modify the ENTRYPOINT line to run your script:

```sh
docker build -t USERNAME/IMAGE_NAME:TAG .
```

That's just a standard `docker build` command. The 0.0.1 is the version which you can update
whenever you make changes to your code.

Test your image, just to be sure you created it correctly:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" USERNAME/IMAGE_NAME:TAG
```

### 5. Push it to Docker Hub

Push it to Docker Hub:

```sh
docker push USERNAME/IMAGE_NAME:TAG
```

### 6. Register your image with Iron

Ok, we're ready to run this on Iron now, but first we have to let Iron know about the
image you just pushed to Docker Hub. Also, you can optionally register environment variables here that will be passed into your container at runtime.

```sh
iron register -e "YOUR_ENV_VAR=ANYTHING" USERNAME/IMAGE_NAME:TAG
```

### 7. Queue / Schedule jobs for your image

Now you can start queuing jobs or schedule recurring jobs for your image. Let's quickly
queue up a job to try it out.

```sh
iron worker queue --payload-file hello.payload.json --wait USERNAME/IMAGE_NAME:TAG
```

Notice we don't use the image tag when queuing, this is so you can change versions
without having to update all your code that's queuing up jobs for the image.

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.

Read the API docs to see how to queue jobs from your code or how to schedule them:
http://dev.iron.io/worker/reference/api/

## Private images

If you want to keep your code private and use a [private Docker repository](https://docs.docker.com/docker-hub/repos/#private-repositories), you just need
to let Iron know how to access your private images:

```sh
iron docker login -e YOUR_DOCKER_HUB_EMAIL -u YOUR_DOCKER_HUB_USERNAME -p YOUR_DOCKER_HUB_PASSWORD
```

Then just do everything the same as above.

## If you don't want to package your code using Docker

You can package and send your code to Iron directly with the instructions below.
Start with steps 1 and 2 above, then continue at step 3 here.

### 4. Package your code

```sh
zip -r spring-batch.zip .
```

### 5. Upload your code

Then upload it:

```sh
iron worker upload --name TASK_NAME --zip spring-batch.zip bupychuk/spring-batch java -cp "JAR_FILE.jar:DEPENDENCY_FILES" io.iron.springbatchworker.IronWorkerJobRunner
```

or

```sh
iron worker upload --name TASK_NAME --zip spring-batch.zip bupychuk/spring-batch java -jar "JAR_FILE.jar"
```

### 6. Queue / Schedule jobs for your worker

Now you can start queuing jobs or schedule recurring jobs for your worker. Let's quickly
queue up a job to try it out.

```sh
iron worker queue -payload-file hello.payload.json --wait TASK_NAME
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.

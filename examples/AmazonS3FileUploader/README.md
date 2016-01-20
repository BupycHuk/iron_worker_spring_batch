## Quick Example for a Spring Batch Worker (3 minutes)

This example will show you how to build your Spring batch worker, test it locally, then upload it
to IronWorker for production.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Read more about [Spring batch](http://docs.spring.io/spring-batch/trunk/reference/html/index.html)

This example pulls urls from IronMQ and uploads files to AmazonS3 storage using spring batch and iron worker

App uses payload to get path to Job configuration file to read job identifier and job parameters.

In this example app uses `src/main/resources/spring/batch/jobs/job-upload-to-amazons3.xml` configuration file 
and runs job with id "amazonS3UploadJob" and five [parameters](http://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html#late-binding):                                                                                                   


`aws.accessKey`: AWS API access key
`aws.secretKey`: AWS API secret key
`aws.bucketName`: AWS S3 storage bucket name,
`aws.regionName`: AWS region name,
`ironmq.queue.name`: IronMQ queue name


amazonS3UploadJob consists of one step with id "step1". 

Step1 uses ironmqReader as reader and amazonS3Writer as writer. 

ironmqReader pull urls from IronMQ (queue name is specified by ironmq.queue.name parameter in payload).

amazonS3Writer load data from urls and upload data to Amazon S3 storage (bucket name is specified by aws.bucketName parameter in payload)


Read spring batch docs to configure [job](http://docs.spring.io/spring-batch/trunk/reference/html/configureJob.html) 
and [steps](http://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html)

### 1. prepare local files

Put iron.json to example root dir
Edit payload file

Payload file has next structure:
```json
{
    "jobPath": "spring/batch/jobs/job-upload-to-amazons3.xml",
    "jobIdentifier": "amazonS3UploadJob",
    "parameters": {
        "aws.accessKey": "[ACCESS KEY]",
        "aws.secretKey": "[SECRET KEY]",
        "aws.bucketName": "[BUCKET NAME]",
        "aws.regionName": "[REGION NAME]",
        "ironmq.queue.name": "[IRON MQ QUEUE NAME]"
    }
}
```

Setup AWS Credentials for Use with the AWS SDK for Java http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/set-up-creds.html

### 2. Build including the dependencies:

Gradle requires a Java JDK or JRE to be installed, version 6 or higher (to check, use java -version). 
Gradle uses whatever JDK it finds in your path. Alternatively, you can set the JAVA_HOME environment variable to point to the installation directory of the desired JDK.

```sh
./gradlew clean build
```

### 3-7 follow the base [getting started instructions on the top level README](https://github.com/BupycHuk/iron_worker_spring_batch).
## Quick Example for a Spring Batch Worker (3 minutes)

This example will show you how to build your Spring batch worker, test it locally, then upload it
to IronWorker for production.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Read more about [Spring batch](http://docs.spring.io/spring-batch/trunk/reference/html/index.html)

This example reads data from csv files and pushes them to IronMQ using spring batch and iron worker

App uses payload to get path to Job configuration file to read job identifier and job parameters.

In this example app uses `src/main/resources/spring/batch/jobs/job-read-files.xml` configuration file 
and runs job with id "readMultiFileWriteIronMQJob" and two [parameters](http://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html#late-binding):                                                                                                   

`input.file`: path to CSV files                                                                                                                                 

`ironmq.queue.name`: iron mq queue name


readMultiFileWriteIronMQJob consists of one step with id "step1". 

Step1 uses flatFileItemReader as reader and ironmqWriter as writer. 

FlatFileItemReader reads data from csv files (specified by input.file parameter in payload). 

IronmqWriter writes data to IronMQ (queue name is specified by ironmq.queue.name parameter in payload)


Read spring batch docs to configure [job](http://docs.spring.io/spring-batch/trunk/reference/html/configureJob.html) 
and [steps](http://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html)

### 1. prepare local files

Put iron.json to example root dir
Edit payload file

Payload file has next structure:
```json
{
    "jobPath": "spring/batch/jobs/job-read-files.xml",
    "jobIdentifier": "readMultiFileWriteIronMQJob",
    "parameters": {
        "input.file": "[PATH TO CSV FILES]",
        "ironmq.queue.name": "[IRON MQ QUEUE NAME]"
    }
}
```


### 2. Build including the dependencies:

Gradle requires a Java JDK or JRE to be installed, version 6 or higher (to check, use java -version). 
Gradle uses whatever JDK it finds in your path. Alternatively, you can set the JAVA_HOME environment variable to point to the installation directory of the desired JDK.

```sh
./gradlew clean build
```

### 3-7 follow the base [getting started instructions on the top level README](https://github.com/BupycHuk/iron_worker_spring_batch).
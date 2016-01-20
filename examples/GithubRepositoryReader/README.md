# Github repositories finder example for a Spring Batch Worker (3 minutes)

This example will show you how to build your Spring batch worker, test it locally, then upload it
to IronWorker for production.

This example finds github repositories by query and writes to console using spring batch and iron worker

App uses payload to get path to Job configuration file to read job identifier and job parameters.

In this example app uses `src/main/resources/spring/batch/jobs/job-find-github-repositories.xml` configuration file 
and runs job with id "findGithubRepositoriesJob" and one [parameter](http://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html#late-binding):                                                                                                   

`input.query`: search query text

findGithubRepositoriesJob consists of one step with id "step1". 

Step1 uses githubRepositoriesReader as reader and consoleWriter as writer. 

GithubRepositoriesReader sends request to api.github.com to find repositories by query(specified by input.query parameter in payload). 

ConsoleWriter writes results to console

Read spring batch docs to configure [job](http://docs.spring.io/spring-batch/trunk/reference/html/configureJob.html) 
and [steps](http://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html)

## Getting started

### 1. prepare local files

Put iron.json to example root dir
Edit payload file

Payload file has next structure:
```json
{
    "jobPath": "spring/batch/jobs/job-find-github-repositories.xml",
    "jobIdentifier": "findGithubRepositoriesJob",
    "parameters": {
        "input.query": "[SEARCH QUERY]"
    }
}
```

### 2. Build including the dependencies:

Gradle requires a Java JDK or JRE to be installed, version 6 or higher (to check, use java -version). 
Gradle uses whatever JDK it finds in your path. Alternatively, you can set the JAVA_HOME environment variable to point to the installation directory of the desired JDK.

```sh
./gradlew clean build
```

This script will build and put jar file to build/libs/gs-batch-processing-0.1.0.jar

### 3-7 follow the base [getting started instructions on the top level README](https://github.com/BupycHuk/iron_worker_spring_batch).
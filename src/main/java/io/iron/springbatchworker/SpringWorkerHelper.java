package io.iron.springbatchworker;

import io.iron.ironworker.client.helpers.WorkerHelper;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;

public class SpringWorkerHelper {
    private WorkerHelper helper;
    private Payload payload;

    public SpringWorkerHelper() {

    }

    public static SpringWorkerHelper fromArgs(String[] args) {
        SpringWorkerHelper springWorkerHelper = new SpringWorkerHelper();
        springWorkerHelper.helper = WorkerHelper.fromArgs(args);
        return springWorkerHelper;
    }

    public JobParameters getJobParameters() throws IOException {
        final Payload payload = getPayload();
        final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        for (Map.Entry<String, String> parameter : payload.getParameters().entrySet()) {
            jobParametersBuilder.addString(parameter.getKey(), parameter.getValue());
        }
        return jobParametersBuilder.toJobParameters();
    }

    Payload getPayload() throws IOException {
        if (payload != null) {
            return payload;
        }
        payload = helper.getPayload(Payload.class);
        return payload;
    }

    public String getJobName() throws IOException {
        return getPayload().getJobIdentifier();
    }

    public JobExecution launchJob(ApplicationContext context) throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Job job = (Job) context.getBean(getJobName());

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        return jobLauncher.run(job, getJobParameters());
    }
}

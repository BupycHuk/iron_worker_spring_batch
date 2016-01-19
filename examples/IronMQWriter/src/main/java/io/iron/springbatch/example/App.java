package io.iron.springbatch.example;

import io.iron.springbatchworker.SpringWorkerHelper;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		SpringWorkerHelper springWorkerHelper = SpringWorkerHelper.fromArgs(args);

		try {
			String[] springConfig = {springWorkerHelper.getPayload().getJobPath()};

			ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

			JobExecution execution = springWorkerHelper.launchJob(context);
			System.out.println("Exit Status : " + execution.getStatus());
			System.out.println("Exit Status : " + execution.getAllFailureExceptions());
		} catch (Exception e) {
			e.printStackTrace();

		}

		System.out.println("Done");
	}
}

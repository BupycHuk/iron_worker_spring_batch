package hello;

import io.iron.springbatchworker.SpringWorkerHelper;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		String[] springConfig = { "spring/batch/config/context.xml", "spring/batch/jobs/job-read-files.xml" };

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

		SpringWorkerHelper springWorkerHelper = SpringWorkerHelper.fromArgs(args);
		try {
			JobExecution execution = springWorkerHelper.launchJob(context);
			System.out.println("Exit Status : " + execution.getStatus());
			System.out.println("Exit Status : " + execution.getAllFailureExceptions());
		} catch (Exception e) {
			e.printStackTrace();

		}

		System.out.println("Done");
	}
}

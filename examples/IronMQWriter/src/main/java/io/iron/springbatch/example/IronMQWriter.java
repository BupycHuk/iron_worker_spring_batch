package io.iron.springbatch.example;

import io.iron.ironmq.Client;
import io.iron.ironmq.Queue;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class IronMQWriter implements ItemWriter<Domain> {

	private Client client = new Client();
	private Queue queue;
	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void write(List<? extends Domain> items) throws Exception {
		List<String> pushMessages = new ArrayList<String>();
		for (Domain item : items) {
			pushMessages.add(item.getId() + ":" + item.getDomain());
		}
		queue.pushMessages(pushMessages.toArray(new String[pushMessages.size()]));
		logger.debug("{} messages pushed to ironmq : {}", pushMessages.size(), pushMessages.toString());
	}

	public void setQueue(String queueName) {
		this.queue = client.queue(queueName);
	}
}
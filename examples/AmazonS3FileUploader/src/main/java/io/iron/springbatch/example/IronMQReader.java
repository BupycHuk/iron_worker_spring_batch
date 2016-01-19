package io.iron.springbatch.example;

import io.iron.ironmq.Client;
import io.iron.ironmq.EmptyQueueException;
import io.iron.ironmq.Message;
import io.iron.ironmq.Queue;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

import java.net.URL;

public class IronMQReader extends AbstractItemCountingItemStreamItemReader<URL> {

	private Client client = new Client();
	private Queue queue;
	private String queueName;

	public void setQueue(String queueName) {
		this.queueName = queueName;
	}

	@Override
	protected URL doRead() throws Exception {
		try {
			final Message message = queue.reserve();
			return new URL(message.getBody());
		} catch (EmptyQueueException e) {
			return null;
		}
	}

	@Override
	protected void doOpen() throws Exception {
		this.queue = client.queue(queueName);
	}

	@Override
	protected void doClose() throws Exception {

	}

}
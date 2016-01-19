package io.iron.springbatch.example;

import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleWriter implements ItemWriter<Repository> {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void write(List<? extends Repository> items) throws Exception {
		for (Repository repository : items) {
			logger.debug(repository.toString());
		}
		logger.debug("{} repositories wrote to console", items.size());
	}
}
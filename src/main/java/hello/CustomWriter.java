package hello;

import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomWriter<T> implements ItemWriter<T> {

	@Override
	public void write(List<? extends T> items) throws Exception {

		for (int i = 0; items.size() > i; i++) {
			Domain obj = (Domain) items.get(i);
			Logger.getLogger(getClass().getName()).log(Level.INFO, obj.getId() + ":" + obj.getDomain());
		}
		
	}
}
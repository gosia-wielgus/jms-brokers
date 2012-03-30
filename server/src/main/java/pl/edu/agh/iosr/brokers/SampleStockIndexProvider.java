package pl.edu.agh.iosr.brokers;

import java.math.BigDecimal;
import java.util.Date;


public class SampleStockIndexProvider extends AbstractStockIndexProvider {

	int interval;
	public SampleStockIndexProvider(int pollingInterval) {
		interval = pollingInterval;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				final long timestamp = new Date().getTime();
				dispatch(new StockIndex(){

					@Override
					public String getName() {
						return "SampleStockIndex";
					}

					@Override
					public BigDecimal getValue() {
						return new BigDecimal("1234.5678");
					}

					@Override
					public long getTimestamp() {
						return timestamp;
					}});
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			// OK
		}
	}

	
}

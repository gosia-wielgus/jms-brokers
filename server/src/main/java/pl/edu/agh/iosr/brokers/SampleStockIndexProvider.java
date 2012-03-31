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
				dispatch(new StockIndex("SampleStockIndex", new BigDecimal("1234.5678"), timestamp));
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			// OK
		}
	}

	
}

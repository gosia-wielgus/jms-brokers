package pl.edu.agh.iosr.brokers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;
import java.util.Random;



public class SampleStockIndexProvider extends AbstractStockIndexProvider {

	int interval;
	List<StockIndex> indices;
	public SampleStockIndexProvider(int pollingInterval, List<StockIndex> indices) {
		this.interval = pollingInterval;
		this.indices = indices;
	}
	Random random = new Random();
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				dispatchAll();
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			// OK
		}
	}
	
	private void dispatchAll() {
		final long timestamp = new Date().getTime();
		for (int i=0; i<indices.size(); i++) {
			StockIndex index = indices.get(i);
			index = updateStockIndex(index);
			dispatch(index);
			indices.set(i, index);
		}
	}
	
	private StockIndex updateStockIndex(StockIndex index) {
		BigDecimal change = new BigDecimal(random.nextGaussian());
		BigDecimal value = index.getValue().add(change);
		value = value.abs();
		value = value.round(new MathContext(8));
		change = value.subtract(index.getValue()).add(index.getChange());
		return new StockIndex(index.getKey(), index.getName(), value, change, new Date().getTime());
	}
}
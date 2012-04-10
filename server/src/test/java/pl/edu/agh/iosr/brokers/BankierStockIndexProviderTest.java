package pl.edu.agh.iosr.brokers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class BankierStockIndexProviderTest {

	private static final int INTERVAL = 1000;
	AbstractStockIndexProvider provider;
	List<StockIndex> indices = new ArrayList<StockIndex>();
	
	@Before
	public void setUp() {
		indices.add(new StockIndex("pl.wig", "WIG", "10000", "0", 0));
		indices.add(new StockIndex("pl.wig20", "WIG20", "1000", "0", 0));
		provider = new BankierStockIndexProvider(INTERVAL, indices);
	}
		
	@Test
	public void parsingTest() throws InterruptedException {
		final Semaphore semaphore = new Semaphore(1);
		semaphore.acquire();
		final List<StockIndex> indexList = Collections.synchronizedList(new ArrayList<StockIndex>());
		
		provider.setOnStockIndex(new StockIndexListener() {
			@Override
			public void onStockIndex(StockIndex index) {
				indexList.add(index);
				if (indexList.size() == indices.size()) {
					semaphore.release();
				}
			}
		});
		provider.start();
		assertTrue(semaphore.tryAcquire(1, 10000, TimeUnit.MILLISECONDS));
		provider.stop();		
		assertEquals(indices.size(), indexList.size());
	}	
}

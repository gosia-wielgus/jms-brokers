package pl.edu.agh.iosr.brokers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

public class SampleStockIndexProviderTest {
	private static final int INTERVAL = 10;
	SampleStockIndexProvider provider;
	
	@Before
	public void setUp() {
		provider = new SampleStockIndexProvider(INTERVAL);
	}
	
	@Test
	public void testDispatch() throws Exception{
		final int events = 3;
		final Semaphore semaphore = new Semaphore(1);
		semaphore.acquire();
		final List<StockIndex> indexList = Collections.synchronizedList(new ArrayList<StockIndex>());
		
		provider.setOnStockIndex(new StockIndexListener() {
			@Override
			public void onStockIndex(StockIndex index) {
				indexList.add(index);
				if (indexList.size() == events) {
					semaphore.release();
				}
			}
		});
		
		long startTime = new Date().getTime();
		provider.start();
		assertTrue(semaphore.tryAcquire(1, 1000, TimeUnit.MILLISECONDS));
		provider.stop();
		long endTime = new Date().getTime();
		
		assertEquals(3, indexList.size());
		for (StockIndex index : indexList) {

			assertEquals("SampleStockIndex", index.getName());
			assertEquals(new BigDecimal("1234.5678"), index.getValue());
			long time = index.getTimestamp();
			assertTrue(startTime <= time && time <= endTime);
		}
	}
}

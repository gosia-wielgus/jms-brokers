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


import static org.junit.Assert.*;

public class SampleStockIndexProviderTest {
	private static final int INTERVAL = 10;
	private static final int EVENTS = 3;
	SampleStockIndexProvider provider;
	List<StockIndex> indices = new ArrayList<StockIndex>();
	
	@Before
	public void setUp() {
		indices.add(new StockIndex("pl.wig", "WIG", "10000", 0));
		indices.add(new StockIndex("pl.wig20", "WIG 20", "1000", 0));
		provider = new SampleStockIndexProvider(INTERVAL, indices);
	}
	
	@Test
	public void testDispatch() throws Exception{
		final Semaphore semaphore = new Semaphore(1);
		semaphore.acquire();
		final List<StockIndex> indexList = Collections.synchronizedList(new ArrayList<StockIndex>());
		
		provider.setOnStockIndex(new StockIndexListener() {
			@Override
			public void onStockIndex(StockIndex index) {
				indexList.add(index);
				if (indexList.size() == EVENTS*indices.size()) {
					semaphore.release();
				}
			}
		});
		
		long startTime = new Date().getTime();
		provider.start();
		assertTrue(semaphore.tryAcquire(1, 1000, TimeUnit.MILLISECONDS));
		provider.stop();
		long endTime = new Date().getTime();
		
		assertEquals(EVENTS*indices.size(), indexList.size());
		for (int i=0; i<indexList.size(); i++) {
			StockIndex index = indexList.get(i);
			assertEquals(indices.get(i%indices.size()).getKey(), index.getKey());
			//assertEquals(new BigDecimal("1234.5678"), index.getValue());
			long time = index.getTimestamp();
			assertTrue(startTime <= time && time <= endTime);
		}
	}
}

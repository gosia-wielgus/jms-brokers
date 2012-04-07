package pl.edu.agh.iosr.brokers;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class MemoryStockIndexDaoTest {
	MemoryStockIndexDao dao;
	@Before
	public void setUp() {
		dao = new MemoryStockIndexDao();
	}
	@Test
	public void testEmpty() {
		assertNull(dao.getLatest("pl.wig20"));
	}
	
	@Test
	public void testLatest() {
		StockIndex older = new StockIndex("pl.wig20", "WIG 20", "123", 5);
		StockIndex newer = new StockIndex("pl.wig20", "WIG 20", "124", 7);
		StockIndex other = new StockIndex("pl.wig", "WIG", "125", 7);

		Set<StockIndex> latestSet = new HashSet<StockIndex>();
		latestSet.add(newer);
		latestSet.add(other);
		
		dao.saveStockIndex(newer);
		dao.saveStockIndex(older);
		dao.saveStockIndex(other);

		assertEquals(newer, dao.getLatest("pl.wig20"));
		assertEquals(latestSet, dao.getAllLatest());
	}

}

package pl.edu.agh.iosr.brokers;

import java.util.Set;

public interface StockIndexDao {

	public void saveStockIndex(StockIndex index);
	public StockIndex getLatest(String name);
	public Set<StockIndex> getAllLatest();

}
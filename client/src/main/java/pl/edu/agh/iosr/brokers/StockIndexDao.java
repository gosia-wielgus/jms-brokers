package pl.edu.agh.iosr.brokers;

public interface StockIndexDao {

	public abstract void saveStockIndex(StockIndex index);

	public abstract StockIndex getLatest();

}
package pl.edu.agh.iosr.brokers;

public class MemoryStockIndexDao implements StockIndexDao {
	private StockIndex index;
	public void saveStockIndex(StockIndex index) {
		this.index = index;
	}
	
	@Override
	public StockIndex getLatest() {
		return index;
	}
}

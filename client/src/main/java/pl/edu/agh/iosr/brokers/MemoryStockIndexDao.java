package pl.edu.agh.iosr.brokers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Simple StockIndex dao implementation, saving indices in memory */
public class MemoryStockIndexDao implements StockIndexDao {
	private Map<String, StockIndex> indexMap = new HashMap<String, StockIndex>();
	public void saveStockIndex(StockIndex index) {
		StockIndex oldIndex = indexMap.get(index.getKey());
		if (oldIndex != null && oldIndex.getTimestamp() > index.getTimestamp())
			return;
		
		indexMap.put(index.getKey(), index);
	}
	
	@Override
	public StockIndex getLatest(String name) {
		return indexMap.get(name);
	}

	@Override
	public Set<StockIndex> getAllLatest() {
		return new HashSet<StockIndex>(indexMap.values());
	}
}

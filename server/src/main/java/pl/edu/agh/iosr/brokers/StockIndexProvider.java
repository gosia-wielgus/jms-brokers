package pl.edu.agh.iosr.brokers;

/** An interface for objects which provide StockIndex instances. 
 * 
 * It can be run synchronously (run method) or asynchronously 
 * (start, stop methods). StockIndexListener instance set up by setOnStockIndex
 * will receive notifications upon arrival of a new StockIndex. 
 */
public interface StockIndexProvider extends Runnable {
	void start();
	void stop();
	void setOnStockIndex(StockIndexListener listener);
}

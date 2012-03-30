package pl.edu.agh.iosr.brokers;

public interface StockIndexProvider extends Runnable {
	void start();
	void stop();
	void setOnStockIndex(StockIndexListener listener);
}

package pl.edu.agh.iosr.brokers;

public abstract class AbstractStockIndexProvider implements StockIndexProvider {
	StockIndexListener listener;
	@Override
	public void setOnStockIndex(StockIndexListener newListener) {
		listener = newListener;
	}

	protected void dispatch(StockIndex index) {
		listener.onStockIndex(index);
	}

	Thread thread;
	@Override
	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		thread.interrupt();
	}
}

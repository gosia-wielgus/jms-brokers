package pl.edu.agh.iosr.brokers;

import java.math.BigDecimal;

public interface StockIndex {
	String getName();
	BigDecimal getValue();
	long getTimestamp();
}

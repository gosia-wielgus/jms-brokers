package pl.edu.agh.iosr.brokers;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockIndex implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private BigDecimal value;
	private long timestamp;
	
	public StockIndex(String name, BigDecimal value, long timestamp) {
		this.name = name;
		this.value = value;
		this.timestamp = timestamp;
	}
	String getName() {
		return name;
	}
	BigDecimal getValue() {
		return value;
	}
	long getTimestamp() {
		return timestamp;
	}
	
	public String toString() {
		return "StockIndex[name="+name+",value="+value+",timestamp="+timestamp+"]";
	}
}

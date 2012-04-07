package pl.edu.agh.iosr.brokers;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key;
	private String name;
	
	private BigDecimal value;
	private long timestamp;
	
	public StockIndex(String key, String name, BigDecimal value, long timestamp) {
		this.key = key;
		this.name = name;
		this.value = value;
		this.timestamp = timestamp;
	}
	public StockIndex(String key, String name, String value, long timestamp) {
		this(key, name, new BigDecimal(value), timestamp);
	}
	public String getKey() {
		return key;
	}
	public String getName() {
		return name;
	}
	public BigDecimal getValue() {
		return value;
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	public String toString() {
		return "StockIndex[name="+key+",value="+value+",timestamp="+timestamp+"]";
	}
}

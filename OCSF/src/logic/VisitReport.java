package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VisitReport implements Serializable{
	private int sum;
	private String data;
	
	public VisitReport(int sum, String data) {
		this.sum = sum;
		this.data = data;
	}
	
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}

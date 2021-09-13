package id.co.map.mapwebreportapplication.model.chart;

import java.util.ArrayList;

public class TransactionByMonthLineChart {

	private ArrayList<Long> data;
	private String label;
	private String borderColor;
	
	public TransactionByMonthLineChart() {
		
	}

	public ArrayList<Long> getData() {
		return data;
	}

	public void setData(ArrayList<Long> data) {
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
	
}

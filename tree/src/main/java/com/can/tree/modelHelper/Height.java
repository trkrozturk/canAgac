package com.can.tree.modelHelper;

import com.google.gson.JsonObject;

public class Height {

	private int minVal;
	private int maxVal;
	private Object display;
	

	@Override
	public String  toString() {
		JsonObject returnVal = new JsonObject();
		returnVal.addProperty("minVal", minVal);
		returnVal.addProperty("maxVal", maxVal);
		returnVal.addProperty("display", display.toString());
		return returnVal.toString();
	}


	public int getMinVal() {
		return minVal;
	}


	public void setMinVal(int minVal) {
		this.minVal = minVal;
	}


	public int getMaxVal() {
		return maxVal;
	}


	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}


	public Object getDisplay() {
		return display;
	}


	public void setDisplay(Object display) {
		this.display = display;
	}
}

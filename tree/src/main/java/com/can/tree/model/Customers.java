package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.google.gson.JsonObject;

@Document(indexName = "customers", type = "customers")
public class Customers {
	@Id
	private String id;
	private String customerName;
	private String customerShortName;
	private String phoneNum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerShortName() {
		return customerShortName;
	}
	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	@Override
    public String toString() {
    	JsonObject customer = new JsonObject();
    	customer.addProperty("id", id);
    	customer.addProperty("customerName", customerName);
    	customer.addProperty("customerShortName", customerShortName);
    	customer.addProperty("phoneNum", phoneNum);
        return customer.toString();
    }
	 public JsonObject toJson() {
		 JsonObject customer = new JsonObject();
	    	customer.addProperty("id", id);
	    	customer.addProperty("customerName", customerName);
	    	customer.addProperty("customerShortName", customerShortName);
	    	customer.addProperty("phoneNum", phoneNum);
	        return customer;
		}
}

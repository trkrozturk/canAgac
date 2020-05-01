package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.gson.JsonObject;

@Document(indexName = "occuredpayments", type = "occuredpayments")
public class OccuredPayments {

	@Id
	private String id;
	private String salesId;
	private String paymentType;
	private int no;
	@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm")
	private String createDate;
	private int price;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	@Override
	public String toString() {
		JsonObject occuredPayment = new JsonObject();
		occuredPayment.addProperty("id", id);
		occuredPayment.addProperty("salesId", salesId);
		occuredPayment.addProperty("createDate", createDate);
		occuredPayment.addProperty("paymentType", paymentType);
		occuredPayment.addProperty("price", price);
		occuredPayment.addProperty("no", no);
		return occuredPayment.toString();
	}
	public JsonObject toJson() {
		JsonObject occuredPayment = new JsonObject();
		occuredPayment.addProperty("id", id);
		occuredPayment.addProperty("salesId", salesId);
		occuredPayment.addProperty("createDate", createDate);
		occuredPayment.addProperty("paymentType", paymentType);
		occuredPayment.addProperty("price", price);
		occuredPayment.addProperty("no", no);
		return occuredPayment;
	}
	
}

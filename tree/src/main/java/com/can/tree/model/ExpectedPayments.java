package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.gson.JsonObject;

@Document(indexName = "expectedpayments", type = "expectedpayments")

public class ExpectedPayments {

	@Id
	private String id;
	@Field(type=FieldType.Keyword)
	private String salesId;
	@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy")
	private String predictedPaymentDate;
	@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm")
	private String createDate;
	private String paymentType;
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

	public String getPredictedPaymentDate() {
		return predictedPaymentDate;
	}
	public void setPredictedPaymentDate(String predictedPaymentDate) {
		this.predictedPaymentDate = predictedPaymentDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		JsonObject expectedPayment = new JsonObject();
		expectedPayment.addProperty("id", id);
		expectedPayment.addProperty("salesId", salesId);
		expectedPayment.addProperty("predictedPaymentDate", predictedPaymentDate);
		expectedPayment.addProperty("createDate", createDate);
		expectedPayment.addProperty("paymentType", paymentType);
		expectedPayment.addProperty("price", price);
		return expectedPayment.toString();
	}
	
	public JsonObject toJson() {
		JsonObject expectedPayment = new JsonObject();
		expectedPayment.addProperty("id", id);
		expectedPayment.addProperty("salesId", salesId);
		expectedPayment.addProperty("predictedPaymentDate", predictedPaymentDate);
		expectedPayment.addProperty("createDate", createDate);
		expectedPayment.addProperty("paymentType", paymentType);
		expectedPayment.addProperty("price", price);
		return expectedPayment;
	}
}

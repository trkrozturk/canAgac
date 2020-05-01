package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.gson.JsonObject;

@Document(indexName = "sales", type = "sales")
public class Sales {
	@Id
    private String id;
    private String salesCode;
    @Field(type=FieldType.Keyword)
    private String customerId;
    private String creator;
    private String phoneNum1;
    private String phoneNum2;
    private String deliveryAddress;
    @Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy")
    private String openDate;
    @Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy")
    private String closeDate;
    @Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy")
    private String billingDate;
    private int totalPrice;
    private int kdvRatio;
    private int discountRatio;
    private int totalVariableCosts;
    private boolean deletedSales;
    private boolean status;
    
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPhoneNum1() {
		return phoneNum1;
	}

	public void setPhoneNum1(String phoneNum1) {
		this.phoneNum1 = phoneNum1;
	}

	public String getPhoneNum2() {
		return phoneNum2;
	}

	public void setPhoneNum2(String phoneNum2) {
		this.phoneNum2 = phoneNum2;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public int getKdvRatio() {
		return kdvRatio;
	}

	public void setKdvRatio(int kdvRatio) {
		this.kdvRatio = kdvRatio;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getDiscountRatio() {
		return discountRatio;
	}

	public void setDiscountRatio(int discountRatio) {
		this.discountRatio = discountRatio;
	}

	public int getTotalVariableCosts() {
		return totalVariableCosts;
	}

	public void setTotalVariableCosts(int totalVariableCosts) {
		this.totalVariableCosts = totalVariableCosts;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}


	public boolean isDeletedSales() {
		return deletedSales;
	}

	public void setDeletedSales(boolean deletedSales) {
		this.deletedSales = deletedSales;
	}

	@Override
    public String toString() {
    	JsonObject salesJson = new JsonObject();
    	salesJson.addProperty("id", id);
    	salesJson.addProperty("salesCode", salesCode);
    	salesJson.addProperty("customerId", customerId);
    	salesJson.addProperty("phoneNum1", phoneNum1);
    	salesJson.addProperty("phoneNum2", phoneNum2);
    	salesJson.addProperty("deliveryAddress", deliveryAddress);
    	salesJson.addProperty("openDate", openDate);
    	salesJson.addProperty("status", status);
    	salesJson.addProperty("isDeletedSales", deletedSales);
       

        return salesJson.toString();
    }
    public JsonObject toDetails() {
    	JsonObject salesJson = new JsonObject();
    	salesJson.addProperty("id", id);
    	salesJson.addProperty("salesCode", salesCode);
    	salesJson.addProperty("customerId", customerId);
    	salesJson.addProperty("phoneNum1", phoneNum1);
    	salesJson.addProperty("phoneNum2", phoneNum2);
    	salesJson.addProperty("deliveryAddress", deliveryAddress);
    	salesJson.addProperty("openDate", openDate);
    	salesJson.addProperty("closeDate", closeDate);
    	salesJson.addProperty("billingDate", billingDate);
    	salesJson.addProperty("kdvRatio", kdvRatio);
    	salesJson.addProperty("totalPrice", totalPrice);
    	salesJson.addProperty("discountRatio", discountRatio);
    	salesJson.addProperty("totalVariableCosts", totalVariableCosts);
    	salesJson.addProperty("creator", creator);
    	salesJson.addProperty("isDeletedSales", deletedSales);
    	//salesJson.addProperty("notes", notes);
    	salesJson.addProperty("status", status);
        return salesJson;
    }
	
	public JsonObject toJson() {
		JsonObject salesJson = new JsonObject();
    	salesJson.addProperty("id", id);
    	salesJson.addProperty("salesCode", salesCode);
    	salesJson.addProperty("customerId", customerId);
    	salesJson.addProperty("phoneNum1", phoneNum1);
    	salesJson.addProperty("phoneNum2", phoneNum2);
    	salesJson.addProperty("deliveryAddress", deliveryAddress);
    	salesJson.addProperty("openDate", openDate);
    	salesJson.addProperty("closeDate", closeDate);
    	salesJson.addProperty("billingDate", billingDate);
    	salesJson.addProperty("kdvRatio", kdvRatio);
    	salesJson.addProperty("totalPrice", totalPrice);
    	salesJson.addProperty("discountRatio", discountRatio);
    	salesJson.addProperty("totalVariableCosts", totalVariableCosts);
    	//salesJson.addProperty("notes", notes);
    	salesJson.addProperty("status", status);
        return salesJson;
	}


}

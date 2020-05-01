package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.gson.JsonObject;
@Document(indexName = "notes", type = "notes")
public class Notes {
	@Id
	private String id;
	private String salesId;
	private String fullName;
	private String username;
	@Field(type=FieldType.Keyword)
	private String userId;
	 @Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy")
	private String createDate;
	private String note;
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString()
	{
		JsonObject noteObj = new JsonObject();
		noteObj.addProperty("id", id);
		noteObj.addProperty("username", username);
		noteObj.addProperty("fullName", fullName);
		noteObj.addProperty("note", note);
		noteObj.addProperty("salesId", salesId);
		noteObj.addProperty("createDate", createDate);
		return noteObj.toString();
	}
	
	public JsonObject toJson()
	{
		JsonObject noteObj = new JsonObject();
		noteObj.addProperty("id", id);
		noteObj.addProperty("username", username);
		noteObj.addProperty("fullName", fullName);
		noteObj.addProperty("note", note);
		noteObj.addProperty("salesId", salesId);
		noteObj.addProperty("createDate", createDate);
		return noteObj;
	}

}

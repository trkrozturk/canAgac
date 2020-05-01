package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.gson.JsonObject;

@Document(indexName = "logs", type = "logs")
public class Logs {

	@Id
	private String id;
	private String type;
	private String processType;
	private String about;
	private String username;
	private String text;

	@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm")
	private String dateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
    public String toString() {
    	JsonObject log = new JsonObject();
    	log.addProperty("id", id);
    	log.addProperty("username", username);
    	log.addProperty("type", type);
    	log.addProperty("text", text);
    	log.addProperty("about", about);
    	log.addProperty("dateTime", dateTime);
    	log.addProperty("processType", processType);
        return log.toString();
    }

}

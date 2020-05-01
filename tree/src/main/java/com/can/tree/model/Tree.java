package com.can.tree.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Document(indexName = "tree", type = "tree")
public class Tree {
	@Id
    private String id;
    private String type;
    private String typeLatin;
    private List<Object> height;
    private List<Object> bodyHeight;
	private List<Object> bodyPerimeter;
    private List<String> disease;
    private List<String> form;
    private List<String> pot;

    JsonParser parser = new JsonParser();
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
	public List<String> getDisease() {
		return disease;
	}
	public void setDisease(List<String> disease) {
		this.disease = disease;
	}
	public List<String> getForm() {
		return form;
	}
	public void setForm(List<String> form) {
		this.form = form;
	}
	public List<String> getPot() {
		return pot;
	}
	public void setPot(List<String> pot) {
		this.pot = pot;
	}
    public String getTypeLatin() {
		return typeLatin;
	}
	public void setTypeLatin(String typeLatin) {
		this.typeLatin = typeLatin;
	}
	public List<Object> getHeight() {
		return height;
	}
	public void setHeight(List<Object> height) {
		this.height = height;
	}
	public List<Object> getBodyHeight() {
		return bodyHeight;
	}
	public void setBodyHeight(List<Object> bodyHeight) {
		this.bodyHeight = bodyHeight;
	}
	public List<Object> getBodyPerimeter() {
		return bodyPerimeter;
	}
	public void setBodyPerimeter(List<Object> bodyPerimeter) {
		this.bodyPerimeter = bodyPerimeter;
	}

	 @Override
	    public String toString() {

	    	JsonObject treeJson = new JsonObject();
	    	treeJson.addProperty("id", id);
	    	treeJson.addProperty("type", type);
	    	treeJson.addProperty("typeLatin", typeLatin!=null?typeLatin:"");
	    	if(disease!=null && !disease.isEmpty()) {
	    		Gson gson = new GsonBuilder().create();
		        JsonArray myCustomArray = gson.toJsonTree(disease).getAsJsonArray();
		    	treeJson.addProperty("disease", myCustomArray.toString());
	    	}
	    	
	    	if(height!=null && !height.isEmpty())
	    		treeJson.addProperty("height", new Gson().toJson(height));
	    	if(pot!=null  && !pot.isEmpty()) {
	    		Gson gson = new GsonBuilder().create();
		        JsonArray myCustomArray = gson.toJsonTree(pot).getAsJsonArray();
	    		treeJson.addProperty("pot",myCustomArray.toString());
	    	}
	    	if(form!=null && !form.isEmpty()) {
	    		Gson gson = new GsonBuilder().create();
		        JsonArray myCustomArray = gson.toJsonTree(form).getAsJsonArray();
	    		treeJson.addProperty("form",myCustomArray.toString());
	    	}
	    	if(bodyHeight!=null && !bodyHeight.isEmpty())
	    		treeJson.addProperty("bodyHeight", new Gson().toJson(bodyHeight));
	    	if(bodyPerimeter!=null && !bodyPerimeter.isEmpty())
	    		treeJson.addProperty("bodyPerimeter", new Gson().toJson(bodyPerimeter));
	        return treeJson.toString();
	    }

	    public JsonObject toJson() {
	    	JsonObject treeJson = new JsonObject();
	    	treeJson.addProperty("id", id);
	    	treeJson.addProperty("type", type);
	    	treeJson.addProperty("typeLatin", typeLatin!=null?typeLatin:"");
	    	if(disease!=null && !disease.isEmpty()) {
	    		Gson gson = new GsonBuilder().create();
		        JsonArray myCustomArray = gson.toJsonTree(disease).getAsJsonArray();
		    	treeJson.addProperty("disease", myCustomArray.toString());
	    	}
	    	else {
	    		treeJson.addProperty("disease","[]");
	    	}
	    	
	    	if(height!=null && !height.isEmpty()) {

	    		treeJson.addProperty("height", new Gson().toJson(height));
	    	}

	    	else {
	    		treeJson.addProperty("height","[]");
	    	}
	    	if(pot!=null  && !pot.isEmpty()) {
	    		Gson gson = new GsonBuilder().create();
		        JsonArray myCustomArray = gson.toJsonTree(pot).getAsJsonArray();
	    		treeJson.addProperty("pot",myCustomArray.toString());
	    	}
	    	else {
	    		treeJson.addProperty("pot","[]");
	    	}
	    	if(form!=null && !form.isEmpty()) {
	    		Gson gson = new GsonBuilder().create();
		        JsonArray myCustomArray = gson.toJsonTree(form).getAsJsonArray();
	    		treeJson.addProperty("form",myCustomArray.toString());
	    	}
	    	else {
	    		treeJson.addProperty("form","[]");
	    	}
	    	if(bodyHeight!=null && !bodyHeight.isEmpty()) {

	    		treeJson.addProperty("bodyHeight", new Gson().toJson(bodyHeight));
	    	}
	    	else {
	    		treeJson.addProperty("bodyHeight","[]");
	    	}
	    	if(bodyPerimeter!=null && !bodyPerimeter.isEmpty()) {

	    		treeJson.addProperty("bodyPerimeter", new Gson().toJson(bodyPerimeter));
	    	}
	    	else {
	    		treeJson.addProperty("bodyPerimeter","[]");
	    	}
	    	
	    	return treeJson;
		}
}

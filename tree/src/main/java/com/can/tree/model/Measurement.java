package com.can.tree.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.can.tree.modelHelper.BodyHeight;
import com.can.tree.modelHelper.BodyPerimeter;
import com.can.tree.modelHelper.Height;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Document(indexName = "measurement", type = "measurement")
public class Measurement {

	@Id
    private String id;
	@Field(type = FieldType.Keyword)
    private String treeObjectId;
    private Height height;
    private BodyHeight bodyHeight;
    private BodyPerimeter bodyPerimeter;
    private String pot;
	@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm")
	private String createDate;
	private boolean isEdit;
	private String form;
    private String editReason;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTreeObjectId() {
		return treeObjectId;
	}
	public void setTreeObjectId(String treeObjectId) {
		this.treeObjectId = treeObjectId;
	}
	public Height getHeight() {
		return height;
	}
	public void setHeight(Height height) {
		this.height = height;
	}
	public BodyHeight getBodyHeight() {
		return bodyHeight;
	}
	public void setBodyHeight(BodyHeight bodyHeight) {
		this.bodyHeight = bodyHeight;
	}
	public BodyPerimeter getBodyPerimeter() {
		return bodyPerimeter;
	}
	public void setBodyPerimeter(BodyPerimeter bodyPerimeter) {
		this.bodyPerimeter = bodyPerimeter;
	}
	public String getPot() {
		return pot;
	}
	public void setPot(String pot) {
		this.pot = pot;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public boolean isEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public String getEditReason() {
		return editReason;
	}
	public void setEditReason(String editReason) {
		this.editReason = editReason;
	}

	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	 @Override
	    public String toString() {
	    	JsonObject measurementJson = new JsonObject();
	    	measurementJson.addProperty("id", id);
	    	measurementJson.addProperty("treeObjectId", treeObjectId);
	    	measurementJson.addProperty("createDate", createDate);
	    	measurementJson.addProperty("height", new Gson().toJson(height));
	    	measurementJson.addProperty("bodyHeight", new Gson().toJson(bodyHeight));
	    	measurementJson.addProperty("bodyPerimeter", new Gson().toJson(bodyPerimeter));
	    	measurementJson.addProperty("pot", pot);
	    	measurementJson.addProperty("form", form);
	    	measurementJson.addProperty("editReason", editReason);
	    	measurementJson.addProperty("isEdit", isEdit);
	        return measurementJson.toString();
	    }

	    public JsonObject toJson() {
	    	JsonObject measurementJson = new JsonObject();
	    	measurementJson.addProperty("id", id);
	    	measurementJson.addProperty("treeObjectId", treeObjectId);
	    	measurementJson.addProperty("createDate", createDate);
	    	measurementJson.addProperty("height", new Gson().toJson(height));
	    	measurementJson.addProperty("bodyHeight", new Gson().toJson(bodyHeight));
	    	measurementJson.addProperty("bodyPerimeter", new Gson().toJson(bodyPerimeter));
	    	measurementJson.addProperty("pot", pot);
	    	measurementJson.addProperty("form", form);
	    	measurementJson.addProperty("editReason", editReason);
	    	measurementJson.addProperty("isEdit", isEdit);
	        return measurementJson;
		}
    
}

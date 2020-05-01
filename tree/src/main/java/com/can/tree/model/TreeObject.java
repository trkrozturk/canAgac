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

@Document(indexName = "treeobject", type = "treeobject")
public class TreeObject {
	
		@Id
	    private String id;
	    private String type;
	    @Field(type = FieldType.Keyword)
	    private String treeId;
		@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm")
		private String lastEditDate;
	    private String salesId;
	    private boolean salesStatus;
		private boolean disease;
	    private String diseaseType;
	    private int price;
		private boolean destroy;
		private Height height;
	    private BodyHeight bodyHeight;
	    private BodyPerimeter bodyPerimeter;
	    private String pot;
		@Field(type =FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm")
		private String createDate;
		private String form;
	    
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLastEditDate() {
			return lastEditDate;
		}
		public void setLastEditDate(String lastEditDate) {
			this.lastEditDate = lastEditDate;
		}
		public String getTreeId() {
			return treeId;
		}
		public void setTreeId(String treeId) {
			this.treeId = treeId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getSalesId() {
			return salesId;
		}
		public void setSalesId(String salesId) {
			this.salesId = salesId;
		}
		public boolean isSalesStatus() {
			return salesStatus;
		}
		public void setSalesStatus(boolean salesStatus) {
			this.salesStatus = salesStatus;
		}
		public boolean isDisease() {
			return disease;
		}
		public void setDisease(boolean disease) {
			this.disease = disease;
		}
		public String getDiseaseType() {
			return diseaseType;
		}
		public void setDiseaseType(String diseaseType) {
			this.diseaseType = diseaseType;
		}

	    public boolean isDestroy() {
			return destroy;
		}
		public void setDestroy(boolean destroy) {
			this.destroy = destroy;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
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
		public String getForm() {
			return form;
		}
		public void setForm(String form) {
			this.form = form;
		}
		 @Override
		    public String toString() {
		    	JsonObject treeObjectJson = new JsonObject();
		    	treeObjectJson.addProperty("id", id);
		    	treeObjectJson.addProperty("treeId", treeId);
		    	treeObjectJson.addProperty("lastEditDate", lastEditDate.toString());
		    	treeObjectJson.addProperty("type", type);
		    	treeObjectJson.addProperty("salesId", salesId);
		    	treeObjectJson.addProperty("salesStatus", salesStatus);
		    	treeObjectJson.addProperty("disease", disease);
		    	treeObjectJson.addProperty("destroy", destroy);
		    	treeObjectJson.addProperty("diseaseType", diseaseType);
		    	treeObjectJson.addProperty("price", price);
		    	treeObjectJson.addProperty("height", new Gson().toJson(height));
		    	treeObjectJson.addProperty("createDate", createDate);
		    	treeObjectJson.addProperty("bodyHeight", new Gson().toJson(bodyHeight));
		    	treeObjectJson.addProperty("bodyPerimeter", new Gson().toJson(bodyPerimeter));
		    	treeObjectJson.addProperty("pot", pot);
		    	treeObjectJson.addProperty("form", form);
		        return treeObjectJson.toString();
		    }

		    public JsonObject toJson() {
		    	JsonObject treeObjectJson = new JsonObject();
		    	treeObjectJson.addProperty("id", id);
		    	treeObjectJson.addProperty("treeId", treeId);
		    	treeObjectJson.addProperty("lastEditDate", lastEditDate.toString());
		    	treeObjectJson.addProperty("type", type);
		    	treeObjectJson.addProperty("salesId", salesId);
		    	treeObjectJson.addProperty("salesStatus", salesStatus);
		    	treeObjectJson.addProperty("disease", disease);
		    	treeObjectJson.addProperty("destroy", destroy);
		    	treeObjectJson.addProperty("diseaseType", diseaseType);
		    	treeObjectJson.addProperty("price", price);
		    	treeObjectJson.addProperty("height", new Gson().toJson(height));
		    	treeObjectJson.addProperty("createDate", createDate);
		    	treeObjectJson.addProperty("bodyHeight", new Gson().toJson(bodyHeight));
		    	treeObjectJson.addProperty("bodyPerimeter", new Gson().toJson(bodyPerimeter));
		    	treeObjectJson.addProperty("pot", pot);
		    	treeObjectJson.addProperty("form", form);
		    	return treeObjectJson;
			}
		    public JsonObject toJsonForDetails() {
		    	JsonObject treeObjectJson = new JsonObject();
		    	treeObjectJson.addProperty("id", id);
		    	treeObjectJson.addProperty("type", type);
		    	treeObjectJson.addProperty("price", price);
		    	return treeObjectJson;
			}
			
}

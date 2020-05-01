package com.can.tree.model;

import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ResponseObject {
	
	private int status;
	private String msg;
	private JsonArray data;
	
	public static final int OK = 200;
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int INTERNAL_SERVER_ERROR = 500;

	public ResponseObject() {
		// TODO Auto-generated constructor stub
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JsonArray getData() {
		return data;
	}

	public void setData(JsonArray data) {
		this.data = data;
	}
	
	public void addData(JsonObject obj) {
		if(this.data == null) {
			this.data = new JsonArray();
		}
		this.data.add(obj);
	}
	
	public JsonObject toJson() {
		JsonObject res = new JsonObject();
		res.addProperty("status", status!=0?status:BAD_REQUEST);
		res.addProperty("message", msg!=null?msg:"");
		res.add("data", data!=null?data:new JsonArray());
		res.addProperty("timestamp", new Date().getTime());
		return res;
	}
	
	@Override
	public String toString() {
		return this.toJson().toString();
	}
	
	
}

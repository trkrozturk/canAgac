package com.can.tree.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.google.gson.JsonObject;


@Document(indexName = "users", type = "users")
public class User {
	
	
    @Id
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String type;
    
    

	public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    
    @Override
    public String toString() {
    	JsonObject userJson = new JsonObject();
    	userJson.addProperty("id", id);
    	userJson.addProperty("username", username);
    	userJson.addProperty("fullName", fullName);
    	userJson.addProperty("type", type);
    
//    	userJson.addProperty("password", password);
        return userJson.toString();
    }

    public JsonObject toJson() {
		JsonObject userJson = new JsonObject();
    	userJson.addProperty("id", id);
    	userJson.addProperty("username", username);
    	userJson.addProperty("fullName", fullName);
    	userJson.addProperty("type", type);
    	
    	return userJson;
	}
	

	
}
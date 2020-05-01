package com.can.tree.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.google.gson.JsonObject;

@Document(indexName = "qr", type = "qr")
public class Qr {
		
		
	    @Id
	    private String id;
	    private String lastQrNumber;
	    private String totalQr;
	    private int occuredNo;
	    private int expectedNo;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLastQrNumber() {
			return lastQrNumber;
		}
		public void setLastQrNumber(String lastQrNumber) {
			this.lastQrNumber = lastQrNumber;
		}
		public void addNumberToLastNumber(String printCount) {
			BigInteger number = new BigInteger(this.lastQrNumber);
			int tempNum = 0;
			tempNum = new Integer(printCount);
			if(tempNum%3!=0)
				tempNum += (3-tempNum%3);
			number = number.add(new BigInteger(tempNum+""));
			
			this.lastQrNumber= number.toString();
		}
		public void updateTotalCount(String printCount) {
			int currentNumber = new Integer(this.totalQr);
			int val = new Integer(printCount);
			if(val%3!=0) {
				val += (3-val%3);
			}
			currentNumber = currentNumber+ val;
			
			this.totalQr= currentNumber+"";
		}

		public String getTotalQr() {
			return totalQr;
		}
		public void setTotalQr(String totalQr) {
			this.totalQr = totalQr;
		}
		public int getOccuredNo() {
			return occuredNo;
		}
		public void setOccuredNo(int occuredNo) {
			this.occuredNo = occuredNo;
		}
		public int getExpectedNo() {
			return expectedNo;
		}
		public void setExpectedNo(int expectedNo) {
			this.expectedNo = expectedNo;
		}
		
		 @Override
		    public String toString() {
		    	JsonObject qrJson = new JsonObject();
		    	qrJson.addProperty("id", id);
		    	qrJson.addProperty("lastQrNumber", lastQrNumber);
		    	qrJson.addProperty("totalQr", totalQr);
		    	qrJson.addProperty("occuredNo", occuredNo);
		    	qrJson.addProperty("expectedNo", expectedNo);
		        return qrJson.toString();
		    }

		    public JsonObject toJson() {
				JsonObject qrJson = new JsonObject();
				qrJson.addProperty("id", id);
				qrJson.addProperty("totalQr", totalQr);
				qrJson.addProperty("lastQrNumber", lastQrNumber);
		    	qrJson.addProperty("occuredNo", occuredNo);
		    	qrJson.addProperty("expectedNo", expectedNo);
		    	
		    	return qrJson;
			}

}

package com.can.tree.query;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;

import com.can.tree.modelHelper.BodyHeight;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FilterHelper {
	private QueryBuilder type;
    private QueryBuilder salesStatus;
	private QueryBuilder disease;
	private QueryBuilder destroy ;
	private QueryBuilder height;
    private QueryBuilder bodyHeight;
    private QueryBuilder bodyPerimeter;
    private QueryBuilder pot ;
	private QueryBuilder form;
	private String response;
	private SortBuilder sortField;
	private int pageNumber;
	private int elementCount;
	@Value("${elasticsearch.home:D:\\Program Files\\ElasticSearch\\6.5.4}")
    private static String elasticsearchHome;

    @Value("${elasticsearch.cluster.name:elasticsearch}")
    private static String clusterName;
    final Settings elasticsearchSettings = Settings.builder()
            .put("client.transport.sniff", true)
            .put("path.home", elasticsearchHome)
            .put("cluster.name", clusterName).build();
    
    private static TransportClient client = null;
    
    static JsonParser parser = new JsonParser();
    
	public FilterHelper(JsonObject filterJson) {
		// TODO Auto-generated constructor stub
		filterJsonParser(filterJson);
		setSortField(filterJson.get("sortField").getAsJsonObject());
		filterAddQuery();
		
	}
	
	private void filterAddQuery() {
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.boolQuery()
				.must(QueryBuilders.boolQuery().should(this.type))
				.must(QueryBuilders.boolQuery().should(this.destroy))
				.must(QueryBuilders.boolQuery().should(this.height))
				.must(QueryBuilders.boolQuery().should(this.salesStatus))
				.must(QueryBuilders.boolQuery().should(this.bodyHeight))
				.must(QueryBuilders.boolQuery().should(this.bodyPerimeter))
				.must(QueryBuilders.boolQuery().should(this.disease))
				.must(QueryBuilders.boolQuery().should(this.pot))
				.must(QueryBuilders.boolQuery().should(this.form))
				).sort(this.sortField);
		openClient(); 	
		SearchResponse response =this.client.prepareSearch("treeobject").setTypes("treeobject").setSource(sourceBuilder).setFrom(this.pageNumber*this.elementCount).setSize(this.elementCount).execute().actionGet();
		closeClient();
		JsonArray responseArray = new JsonArray();
		for (SearchHit hits : response.getHits()) {
			JsonObject parResp = new JsonObject();
			parResp = parser.parse(hits.getSourceAsString()).getAsJsonObject();
			responseArray.add(parResp);
		}
		System.out.println(responseArray.size());
		System.out.println(response.getHits().totalHits);
		setResponse(responseArray.toString());
	}
	private void filterJsonParser(JsonObject filterJson) {
		
		if(filterJson.get("type").getAsJsonArray().size() != 0) 
		{
			List<String> typeList = new ArrayList<String>();
			JsonArray type = new  JsonArray();
			type = filterJson.get("type").getAsJsonArray();
			String temp;
			for (int i = 0 ; i<type.size();i++) {
				temp = type.get(i).getAsString();
				typeList.add(temp);
			}
			this.type=QueryBuilders.termsQuery("type.keyword", typeList);
		}
		
		else
		{
			this.type=QueryBuilders.matchAllQuery();
		}
		
		if(filterJson.get("disease").getAsString().equals("true") || filterJson.get("disease").getAsString().equals("false")) 
		{
			boolean disease = filterJson.get("disease").getAsBoolean();			
			this.disease= QueryBuilders.termQuery("disease", disease);
			
		}
		else
		{
			this.disease=QueryBuilders.matchAllQuery();
		}
		if(filterJson.get("salesStatus").getAsString().equals("true") || filterJson.get("salesStatus").getAsString().equals("false")) 
		{
			boolean salesStatus = filterJson.get("salesStatus").getAsBoolean();
			this.salesStatus= QueryBuilders.termQuery("salesStatus", salesStatus);
			
		}
		else
		{
			this.salesStatus=QueryBuilders.matchAllQuery();
		}
		if(filterJson.get("destroy").getAsJsonArray().size() != 0) 
		{
			JsonArray destroy = new  JsonArray();
			destroy = filterJson.get("destroy").getAsJsonArray();
			Boolean temp;
			
			for (int i = 0 ; i<destroy.size();i++) {
				temp = destroy.get(i).getAsBoolean();
				this.destroy= QueryBuilders.termQuery("destroy", temp);
			}
			
		}
		
		else
		{
			this.destroy= QueryBuilders.termQuery("destroy", false);
		}
		
		if(filterJson.get("height").getAsJsonArray().size() != 0) 
		{
			List<String> heightList = new ArrayList<String>();
			JsonArray height = new  JsonArray();
			height = filterJson.get("height").getAsJsonArray();
			String temp;
			
			for (int i = 0 ; i<height.size();i++) {
				temp = height.get(i).getAsString();
				heightList.add(temp);
			}
			this.height= QueryBuilders.termsQuery("height.display.keyword", heightList);
		}
		else 
		{
			this.height= QueryBuilders.matchAllQuery();
		}
		if(filterJson.get("bodyHeight").getAsJsonArray().size() != 0) 
		{
			List<String> bodyHeightList = new ArrayList<String>();
			JsonArray bodyHeight = new  JsonArray();
			bodyHeight = filterJson.get("bodyHeight").getAsJsonArray();
			String temp;
			
			for (int i = 0 ; i<bodyHeight.size();i++) {
				temp = bodyHeight.get(i).getAsString();
				bodyHeightList.add(temp);
			}
			this.bodyHeight = QueryBuilders.termsQuery("bodyHeight.display.keyword", bodyHeightList);
		}
		
		else 
		{
			this.bodyHeight = QueryBuilders.matchAllQuery();
		}
		
		if(filterJson.get("bodyPerimeter").getAsJsonArray().size() != 0) 
		{
			List<String> bodyPerimeterList = new ArrayList<String>();
			JsonArray bodyPerimeter = new  JsonArray();
			bodyPerimeter = filterJson.get("bodyPerimeter").getAsJsonArray();
			
			String temp;
			for (int i = 0 ; i<bodyPerimeter.size();i++) {
				temp = bodyPerimeter.get(i).getAsString();
				bodyPerimeterList.add(temp);
			}
			this.bodyPerimeter=QueryBuilders.termsQuery("bodyPerimeter.display.keyword", bodyPerimeterList);
		}
		
		else 
		{
			this.bodyPerimeter=QueryBuilders.matchAllQuery();
		}
		
		if(filterJson.get("pot").getAsJsonArray().size() != 0) 
		{
			List<String> potList = new ArrayList<String>();
			JsonArray pot = new  JsonArray();
			pot = filterJson.get("pot").getAsJsonArray();
			
			String temp;
			for (int i = 0 ; i<pot.size();i++) {
				temp = pot.get(i).getAsString();
				potList.add(temp);
			}
			this.pot= QueryBuilders.termsQuery("pot.keyword", potList);
		}
		
		else 
		{
			this.pot= QueryBuilders.matchAllQuery();
		}
		
		if(filterJson.get("form").getAsJsonArray().size() != 0) 
		{
			List<String> formList = new ArrayList<String>();
			JsonArray form = new  JsonArray();
			form = filterJson.get("form").getAsJsonArray();
			
			String temp;
			for (int i = 0 ; i<form.size();i++) {
				temp = form.get(i).getAsString();
				formList.add(temp);
			}
			this.form=QueryBuilders.termsQuery("form.keyword", formList);
		}
		
		else 
		{
			this.form=QueryBuilders.matchAllQuery();
		}
		
		if(filterJson.has("pageNumber")) {
			this.pageNumber=filterJson.get("pageNumber").getAsInt();
		}
		else {
			this.pageNumber=1;
		}
		if(filterJson.has("elementCount")) {
			this.elementCount = filterJson.get("elementCount").getAsInt();
		}
		else {
			this.elementCount = 5;
		}
	}
	private void setSortField(JsonObject sortField) {
		if (!sortField.get("sortField").getAsString().isEmpty()) {
			if (sortField.get("orderBy").getAsString().equals("ASC")) {
				this.sortField =  SortBuilders.fieldSort(sortField.get("sortField").getAsString()).order(SortOrder.ASC);
			}
			else {
				this.sortField =  SortBuilders.fieldSort(sortField.get("sortField").getAsString()).order(SortOrder.DESC);
			}
		}
		else {
			this.sortField = SortBuilders.fieldSort("_id").order(SortOrder.ASC);
		}
	}
    private static void openClient() {
    	try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
			        .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private static void closeClient() {
		client.close();
    }
    
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getElementCount() {
		return elementCount;
	}

	public void setElementCount(int elementCount) {
		this.elementCount = elementCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}

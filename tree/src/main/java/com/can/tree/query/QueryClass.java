package com.can.tree.query;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.persistent.PersistentTaskResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.can.tree.model.Measurement;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class QueryClass {

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
	public static JsonObject lastMeasurement (String treeObjectId) {
		openClient();		
		QueryBuilder qb = QueryBuilders.termQuery("treeObjectId", treeObjectId); 
		
		SearchResponse response = client.prepareSearch("measurement").setTypes("measurement").addSort(SortBuilders.fieldSort("createDate") .order(SortOrder.DESC)).setQuery(qb) .setSize(1).execute().actionGet(); 
		closeClient();
		for(SearchHit hits : response.getHits()) 
		{ 
//			System.out.print("id = " + hits.getId()); 
//			System.out.println(hits.getSourceAsString());
			JsonObject classObj = new JsonObject();
			classObj = parser.parse(hits.getSourceAsString()).getAsJsonObject();
			Measurement responseObj = new Gson().fromJson(classObj, Measurement.class);
			responseObj.setId(hits.getId());
			return responseObj.toJson();
			}
		return null;
	}
	
	public static JsonArray fetchSales() {
		openClient();
		JsonArray resp = new JsonArray();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");		 
		Date qDate = new Date();
		QueryBuilder qb = QueryBuilders.rangeQuery("deliveryDate").gte(formatter.format(qDate));
		SearchResponse response = client.prepareSearch("sales").setTypes("sales").setQuery(qb).execute().actionGet();
		closeClient();
		for (SearchHit hits : response.getHits()) {
			JsonObject parResp = new JsonObject();
			parResp = parser.parse(hits.getSourceAsString()).getAsJsonObject();
			parResp.addProperty("id", hits.getId());
			resp.add(parResp);
		}
		return resp;
	}
	
	public static JsonObject treeCount() throws InterruptedException, ExecutionException {
		openClient();
		JsonObject resp = new JsonObject(); 	 	
		QueryBuilder qb = QueryBuilders.termQuery("destroy", false);
		SearchHits response = client.prepareSearch("treeobject").setTypes("treeobject").setQuery(qb).execute().get().getHits();
		closeClient();
		resp.addProperty("count", response.getTotalHits());
		
		return resp;
	}
	
	public static long salesNoteCount(String salesId) throws InterruptedException, ExecutionException {
		openClient();	
		QueryBuilder qb = QueryBuilders.termQuery("salesId"+".keyword", salesId);
		SearchHits response = client.prepareSearch("notes").setTypes("notes").setQuery(qb).execute().get().getHits();
		closeClient();
		
		
		return response.getTotalHits();
	}
}

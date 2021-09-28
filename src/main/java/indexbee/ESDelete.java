package indexbee;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

//ElasticSearch Delete class
class ESDelete {
	public static void Delete(File dataDir, String uname) {
		String dir = dataDir.toString();
		System.out.println(dir);
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http")));

		GetIndexRequest getRequest = new GetIndexRequest(uname);
		boolean exists;
		try {
			exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
			if(exists) {
			       SearchRequest searchRequest = new SearchRequest(uname);
			       SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
			       searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
			       searchRequest.source(searchSourceBuilder);
			       String map=null;
			         
			       try {
			           SearchResponse searchResponse = null;
			           searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
			           if (searchResponse.getHits().getTotalHits().value > 0) {
			               SearchHit[] searchHit = searchResponse.getHits().getHits();
			               for (SearchHit hit : searchHit) {
			                   map = hit.getId();
			                     File f = new File(map);
			                     if(f.getParent().equals(dir)) {
			         				DeleteRequest deleteRequest = new DeleteRequest(uname, map);
			         		        DeleteResponse deleteResponse = null;
			         				try {
			         					deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
			         					System.out.println(dataDir.toString() + " deleted");
			         				} catch (IOException e) {
			         					// TODO Auto-generated catch block
			         					e.printStackTrace();
			         				}
			                     }
			               }
			           }
			       } catch (IOException e) {
			           e.printStackTrace();
			       }
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
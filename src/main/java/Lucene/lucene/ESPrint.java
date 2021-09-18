package Lucene.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

class ESPrint {
	public static void Print(String uname) {
		ArrayList<String> list = new ArrayList<>();
		System.out.println("Print in esapp");		// TODO Auto-generated method stub
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		GetIndexRequest getRequest = new GetIndexRequest(uname);
		boolean exists;
		
		try {
			exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
			if(exists) {
				SearchRequest searchRequest = new SearchRequest();
			    searchRequest.indices(uname);
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
			                File Directories = new File(map);
			                String DirectoryName = Directories.getParent();
				    	    if(!list.contains(DirectoryName)) {
				    	    	list.add(DirectoryName);
				    	    }
			            }
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			} else {
				System.out.println("Index does not exists");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    	for(String print : list) {
	    		System.out.println(print);
	    	}
		}
	}
package Lucene.lucene;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;

class ESIndexing {
	@SuppressWarnings("deprecation")
	public static void main(){
		System.out.println("Indexing in ESApp");
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		GetIndexRequest getRequest = new GetIndexRequest("es");
		boolean exists;
		try {
			exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
			if(!exists) {
				CreateIndexRequest request = new CreateIndexRequest("es");
		        request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
				CreateIndexResponse createIndexResponse = null;
				try {
					createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("response id: " + createIndexResponse.index());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("path", "c:/skdankj//a.txt"); 
		map.put("content", "Ada vaapa ansari...");
		
		IndexRequest indexRequest = new IndexRequest("es");
		indexRequest.id("101");
		indexRequest.source(map);
		IndexResponse indexResponse = null;
		try {
			indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response id: "+indexResponse.getId());
		System.out.println("response name: "+indexResponse.getResult().name());
	}
}


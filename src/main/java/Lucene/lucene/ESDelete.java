package Lucene.lucene;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

class ESDelete {
	public static void main() {
		System.out.println("Deleting in ESApp");
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http")));

		GetIndexRequest getRequest = new GetIndexRequest("es");
		boolean exists;
		
		try {
			exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
			if(exists) {
				DeleteRequest deleteRequest = new DeleteRequest("es","001");
		        DeleteResponse deleteResponse = null;
				try {
					deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    System.out.println("response id: "+deleteResponse.getId());
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
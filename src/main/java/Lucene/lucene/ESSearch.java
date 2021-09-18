package Lucene.lucene;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

class ESSearch {
	public static void main() {
		System.out.println("Searching in ESApp");
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http")));

		
	}
}
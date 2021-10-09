package indexbee;

import java.io.File;
import java.io.IOException;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

//ElasticSearch Delete class
class ESDelete {
	public static void delete(File dataDir, String uname, RestHighLevelClient client) {
		String dir = dataDir.toString();
		GetIndexRequest getRequest = new GetIndexRequest(uname);
		boolean exists;
		try {
			exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
			if (exists) {
				SearchRequest searchRequest = new SearchRequest(uname);
				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
				searchSourceBuilder.query(QueryBuilders.matchAllQuery());
				searchRequest.source(searchSourceBuilder);
				String map = null;
				SearchResponse searchResponse = null;
				searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				if (searchResponse.getHits().getTotalHits().value > 0) {
					SearchHit[] searchHit = searchResponse.getHits().getHits();
					for (SearchHit hit : searchHit) {
						map = hit.getId();
						File file = new File(map);
						if (file.getParent().equals(dir)) {
							DeleteRequest deleteRequest = new DeleteRequest(uname, map);
							DeleteResponse deleteResponse = null;
							deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
						}
					}
				}
			}
			System.out.println(dataDir.toString() + " deleted");
		} catch (IOException e) {
			System.out.println("Can not delete the folder " + dataDir.toString());
		}
	}
}
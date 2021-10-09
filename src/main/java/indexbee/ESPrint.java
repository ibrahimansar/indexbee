package indexbee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

//ElasticSearch List class
class ESPrint {
	public static void print(String uname, RestHighLevelClient client) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		GetIndexRequest getRequest = new GetIndexRequest(uname);
		boolean exists;
		exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
		if (exists) {
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(uname);
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
					File Directories = new File(map);
					String DirectoryName = Directories.getParent();
					if (!list.contains(DirectoryName)) {
						list.add(DirectoryName);
					}
				}
			}
		} else {
			System.out.println("Index does not exists");
		}

		for (String print : list) {
			System.out.println(print);
		}
	}
}
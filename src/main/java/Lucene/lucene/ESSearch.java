package Lucene.lucene;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

//ElasticSearch Search class
class ESSearch {
	public static void Search(String Word, String uname) {
        
       RestHighLevelClient client = new RestHighLevelClient(
               RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
       SearchRequest searchRequest = new SearchRequest(uname);
       SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
       MatchQueryBuilder qb = new MatchQueryBuilder("content",Word);
       searchSourceBuilder.query(qb);
       searchRequest.source(searchSourceBuilder);
       String map=null;
         
       try {
           SearchResponse searchResponse = null;
           searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
           if (searchResponse.getHits().getTotalHits().value > 0) {
               SearchHit[] searchHit = searchResponse.getHits().getHits();
               for (SearchHit hit : searchHit) {
                   map = hit.getId();
                     System.out.println(map);
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
	}
}
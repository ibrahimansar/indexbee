package indexbee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

//ElasticSearch Index class
class ESIndexing {
	@SuppressWarnings("deprecation")
	public static void Index(File dataDir, String uname){
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
		GetIndexRequest getRequest = new GetIndexRequest(uname);
		boolean exists;
		
		//creating new index if does not exists
		try {
			exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
			if(!exists) {
				CreateIndexRequest request = new CreateIndexRequest(uname);
		        request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
				CreateIndexResponse createIndexResponse = null;
				try {
					createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//indexing all files in the directory
        File[] files = dataDir.listFiles();
        String dirName = dataDir.toString();
        
        int count = 0;
        
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if(f.isDirectory()) {
            	Index(f, uname);
            } else {
            	addData(f, client, uname, dirName);
                count = count+ 1;
            }
        }
        System.out.println(count + " files indexed");
	}
	
	public static void addData(File f, RestHighLevelClient client, String uname, String dirName) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		String filePath = f.toString();
		try {
			String content = new String(Files.readAllBytes(Paths.get(filePath)));
			map.put("file", filePath); 
			map.put("content", content);
			map.put("dir", dirName);
			
			IndexRequest indexRequest = new IndexRequest(uname);
			indexRequest.id(filePath);
			indexRequest.source(map);
			IndexResponse indexResponse = null;
			try {
				indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}


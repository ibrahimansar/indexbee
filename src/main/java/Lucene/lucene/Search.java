package Lucene.lucene;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Search {
    static void searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {    
    	try( Directory directory = FSDirectory.open(indexDir)){
        @SuppressWarnings({ "deprecation", "resource" })
		IndexSearcher searcher = new IndexSearcher(directory);
        @SuppressWarnings("deprecation")
		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new SimpleAnalyzer());
        Query query = parser.parse(queryStr);        
        TopDocs topDocs = searcher.search(query, maxHits);    	
        ScoreDoc[] hits = topDocs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("filename"));
        }        
        System.out.println("Found " + hits.length);        
      }
      catch (FileNotFoundException e) {
    	  System.out.println("Indexing directory not found");
      }
    } 
}

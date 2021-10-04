package indexbee;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

//Lucene Search class
public class LuceneSearch {
	static void searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {
		ArrayList<String> list = new ArrayList<>();
		for (File subfile : indexDir.listFiles()) {
			if (subfile.isDirectory()) {
				searchIndex(subfile, queryStr, maxHits);
				continue;
			}
			File file = indexDir;
			Path path = file.toPath();
			Directory directory = FSDirectory.open(path);
			IndexReader idxReader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(idxReader);
			QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
			Query query = parser.parse(queryStr);
			TopDocs topDocs = searcher.search(query, maxHits);
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				if (!list.contains(d.get("path"))) {
					list.add(d.get("path"));
				}
			}

		}
		for (String print : list) {
			System.out.println(print);
		}
	}
}

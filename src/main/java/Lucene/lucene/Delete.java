package Lucene.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

public class Delete {
	public static void delDocuments(File indexDir, String field, String termText) throws IOException {
		Term term = new Term(field, termText);
		@SuppressWarnings("deprecation")
		IndexWriter indexWriter = new IndexWriter( FSDirectory.open(indexDir), new SimpleAnalyzer(), true, IndexWriter.MaxFieldLength.LIMITED);
		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}
}

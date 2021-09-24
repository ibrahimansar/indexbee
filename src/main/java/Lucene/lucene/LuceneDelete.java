package Lucene.lucene;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

public class LuceneDelete {
	public static void delDocuments(File indexDir, String field, File termText) throws IOException {
    	String DirectoryName = termText.getName();
    	File Dir = new File(indexDir.getAbsolutePath() + "\\" + DirectoryName); 
//    	System.out.println(Dir.toString());
    	String dirName = termText.getAbsolutePath();
		Term term = new Term(field, dirName);
		IndexWriterConfig conf = new IndexWriterConfig( new StandardAnalyzer());
		IndexWriter indexWriter = new IndexWriter( FSDirectory.open(Dir.toPath()), conf);
		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}
}

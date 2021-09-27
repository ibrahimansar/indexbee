package Lucene.lucene;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

//Lucene Delete class
public class LuceneDelete {
	public static void delDocuments(File indexDir, String field, File termText) throws IOException {
    	String DirectoryName = termText.getName();
    	File Dir = new File(indexDir.getAbsolutePath() + "\\" + DirectoryName); 
    	String dirName = termText.getAbsolutePath();
		Term term = new Term(field, dirName);
		IndexWriterConfig conf = new IndexWriterConfig( new StandardAnalyzer());
		IndexWriter indexWriter = new IndexWriter( FSDirectory.open(Dir.toPath()), conf);
		try {
			indexWriter.deleteDocuments(term);
			System.out.println(termText.toString() + " deleted");
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		indexWriter.close();
	}
}

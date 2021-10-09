package indexbee;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

//Lucene Delete class
public class LuceneDelete implements AutoCloseable{
	public void delDocuments(File indexDir, String field, File termText) throws IOException {
		String directoryName = termText.getName();
		File dir = new File(indexDir.getAbsolutePath() + "\\" + directoryName);
		String dirName = termText.getAbsolutePath();
		Term term = new Term(field, dirName);
		IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(dir.toPath()), conf);
		try {
			indexWriter.deleteDocuments(term);
			System.out.println(termText.toString() + " deleted");
		} catch (IOException e) {
			System.out.println("Failed to delete");
		}
		indexWriter.close();
	}
	public void close() {
	}
}

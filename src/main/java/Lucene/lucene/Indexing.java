package Lucene.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

class Indexing implements AutoCloseable{
	public void index(File indexDir, File dataDir, String suffix) throws IOException {
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_20, new SimpleAnalyzer());
		conf.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
       IndexWriter indexWriter = new IndexWriter( FSDirectory.open(indexDir), conf);
       indexWriter.setUseCompoundFile(false);     
       IndexDir indexDirectory = new IndexDir();
       indexDirectory.indexDirectory(indexWriter, dataDir, suffix);        
       int numIndexed = indexWriter.maxDoc();
       indexWriter.optimize();
       indexWriter.close();        
//       System.out.println("Numer of total files indexed:  " + numIndexed);
	}
		public void close() {}
}
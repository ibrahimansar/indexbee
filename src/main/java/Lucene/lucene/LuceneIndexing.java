package Lucene.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

class LuceneIndexing implements AutoCloseable{
	public void index(File indexDir, File dataDir, String suffix) throws IOException {       
        indexDirectory(indexDir, dataDir, suffix);            
	}
	
    public void indexDirectory(File indexDir, File dataDir, String suffix) throws IOException {    	
    	String DirectoryName = dataDir.getName();    	
    	File Dir = new File(indexDir.getAbsolutePath() + "\\" + DirectoryName);    	
    	if(!Dir.exists()) {
    		Dir.mkdir();
    	}    	
		IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
		conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter( FSDirectory.open(Dir.toPath()), conf);
//        indexWriter.setUseCompoundFile(false);
 
        File[] files = dataDir.listFiles();
        int count = 0;
        
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(indexDir, f, suffix);
            }
            else {
                indexFileWithIndexWriter(indexWriter, f, suffix);
                count = count+ 1;
            }
        }
        
//        indexWriter.optimize();
        indexWriter.close(); 
        System.out.println(count + " files indexed");
    }
    
    private void indexFileWithIndexWriter(IndexWriter indexWriter, File f, String suffix) throws IOException {
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }        
        if (suffix!=null && !f.getName().endsWith(suffix)) {
            return;
        }        
        System.out.println("Indexing file:... " + f.getAbsolutePath());        
        Document doc = new Document();
        doc.add(new TextField("contents", new FileReader(f)));       
        doc.add(new StringField("filename", f.getName(), Field.Store.YES));
        doc.add(new StringField("path", f.getAbsolutePath(), Field.Store.YES));  
        doc.add(new StringField("folder", f.getParent(), Field.Store.YES));
        indexWriter.addDocument(doc);
    }
		public void close() {}
}
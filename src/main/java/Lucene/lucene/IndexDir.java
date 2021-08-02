package Lucene.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

public class IndexDir {
    public void indexDirectory(IndexWriter indexWriter, File dataDir, String suffix) throws IOException {
        File[] files = dataDir.listFiles();
        int count = 0;
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(indexWriter, f, suffix);
            }
            else {
                indexFileWithIndexWriter(indexWriter, f, suffix);
                count = count+ 1;
            }
        }
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
        doc.add(new Field("contents", new FileReader(f)));       
        doc.add(new Field("filename", f.getName(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("path", f.getAbsolutePath(), Field.Store.YES, Field.Index.ANALYZED));  
        doc.add(new Field("folder", f.getParent(), Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(doc);
    }
}

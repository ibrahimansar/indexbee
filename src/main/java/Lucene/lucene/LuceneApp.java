package Lucene.lucene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;



@Command(name = "LuceneApp", description = "App to perform Lucene indexing and searching operations", mixinStandardHelpOptions = true, version = "LuceneApp 1.0")
public abstract class LuceneApp extends Indexing{
	
	@Option(names = "-path", description = "folder to be indexed; [usage: 'LuceneApp -path 'C:/User/data/' '] ")
	static String path;
	
    @Option(names = "-search", description = "Searches given name; [usage: 'LuceneApp -search 'Lucene' '] ")
	static String Word;
    
    @Option(names = "-list", description = "Lists all indexed folder; [usage: 'LuceneApp -list 'show' '] ")
	static String List;
	
    public static void main(String[] args) throws Exception {  
    	
    	File indexDir = new File("C:/Lucene/Index/");    
    	
    	//--indexing--//
    	try{
        	if(path !=null && !path.isEmpty()) {
                File dataDir = new File(path);
//                File dataDir = new File("C:/Lucene/Data/");
                String suffix = "txt";        
                close(indexDir, dataDir, suffix);        
                addPath(path);
            	}
    	} catch (Exception e) {
    		System.out.println("Directory not found");
    	}
        
        //--searching--//
    	if(Word !=null && !Word.isEmpty()) {
        String query = Word;
//        String query = "lucene";
        int hits = 100;        
        searchIndex(indexDir, query, hits);
    	}
    	
    	//--List--//
    	if(List == "show") {
    		printPaths();
    	}

    } 
    
    //searching method    
    private static void searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {    
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
    
    //List methods   
    	//Adding path to list
    private static void addPath(String path) {
    	String fileName = "C:\\Lucene\\List.txt";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(path + "\n");
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }
    
    	//printing list
    private static void printPaths() {
    	String fileName = "C:\\Lucene\\List.txt";
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              System.out.println(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

abstract class Indexing implements AutoCloseable{
	public static void close(File indexDir, File dataDir, String suffix) throws Exception {
		// TODO Auto-generated method stub
        IndexWriter indexWriter = new IndexWriter( FSDirectory.open(indexDir), new SimpleAnalyzer(), true, IndexWriter.MaxFieldLength.LIMITED);
        indexWriter.setUseCompoundFile(false);     
        IndexDir indexDirectory = new IndexDir();
        indexDirectory.indexDirectory(indexWriter, dataDir, suffix);        
        int numIndexed = indexWriter.maxDoc();
        indexWriter.optimize();
        indexWriter.close();        
        System.out.println("Numer of total files indexed:  " + numIndexed);
	}
}


class IndexDir {
    public void indexDirectory(IndexWriter indexWriter, File dataDir, String suffix) throws IOException {
        File[] files = dataDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(indexWriter, f, suffix);
            }
            else {
                indexFileWithIndexWriter(indexWriter, f, suffix);
            }
        }

    }
    
    private void indexFileWithIndexWriter(IndexWriter indexWriter, File f, String suffix) throws IOException {
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }

        
        if (suffix!=null && !f.getName().endsWith(suffix)) {
            return;
        }
        
        System.out.println("Indexing file:... " + f.getCanonicalPath());
        
        Document doc = new Document();
        doc.add(new Field("contents", new FileReader(f)));        
        doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES, Field.Index.ANALYZED));
        
        indexWriter.addDocument(doc);
    }
}

package Lucene.lucene;

import java.io.File;
import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;

@Command(name = "LuceneApp", description = "App to perform Lucene indexing and searching operations", mixinStandardHelpOptions = true, version = "LuceneApp 1.0")
public abstract class LuceneApp extends Indexing{	
	
	
	@Option(names = "-path", description = "folder to be indexed; [usage: 'LuceneApp -path 'C:/User/data/' '] ")
//	static String path;
	static String path = "C:/Lucene/Data";
	
    @Option(names = "-search", description = "Searches given name; [usage: 'LuceneApp -search 'Lucene' '] ")
	static String Word;
//    static String Word = "Lucene";
    
    @Option(names = "-list", description = "Lists all indexed folder; [usage: 'LuceneApp -list 'show' '] ")
	static String List = "show";
//    static String List;
	
    @Option(names = "-delete", description = "delete indexed folder; [usage: 'LuceneApp -Del 'C:/User/data/' '] ")
    static String Del;    
//	static String Del = "C:/Lucene/Data1";
    
    public static void main(String[] args) throws Exception {  
    	
    	File indexDir = new File("C:/Lucene/Index");    	
    	
    	//--indexing--//
    	if(path !=null && !path.isEmpty()) {
    		try(Indexing i = new Indexing()){
                File dataDir = new File(path);
                String suffix = "txt"; 
    			i.index(indexDir, dataDir, suffix);
    		} catch(Exception e) {
    			System.out.println(e);
    		}
        }
        
        //--searching--//
    	if(Word !=null && !Word.isEmpty()) {
        String query = Word;
        int hits = 100;        
        Search.searchIndex(indexDir, query, hits);
    	}
    	
    	//--List--//
    	if(List == "show") {
    		Print.PrintIndex(indexDir);
    	}
    	
    	//--Deleting path from list--//
    	if(Del !=null && !Del.isEmpty()) {    		
    		Delete.delDocuments(indexDir, "folder", Del);
    	}
    } 	
}
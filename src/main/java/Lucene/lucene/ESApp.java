package Lucene.lucene;

import java.io.File;

import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;

@Command(name = "ElasticSearch", description = "App to perform Lucene indexing and searching operations using ES framework", mixinStandardHelpOptions = true, version = "1.0")
class ESApp extends ESIndexing implements Runnable { 

	@Option(names = "-path", description = "folder to be indexed; [usage: 'Lucene -path 'C:/Lucene/data' '] ")
	private String path;
	
    @Option(names = "-search", description = "Searches given name; [usage: 'Lucene -search 'Lucene' '] ")
    private String Word ;
    
    @Option(names = "-list", description = "Lists all indexed folder; [usage: 'Lucene -list 'show' '] ")
	private String List = "list";
	
    @Option(names = "-delete", description = "delete indexed folder; [usage: 'Lucene -delete 'C:/Lucene/data' '] ")
	private String Del;
    
    @Override
    public void run() {
    	String uname = System.getProperty("user.name");
    	
    	//--indexing--//
    	if(path !=null && !path.isEmpty()) {
    		File dataDir = new File(path);
    		if(dataDir.exists()) {
    			ESIndexing.Index(dataDir, uname);
    		} else {
    			System.out.println("Directory or file does not exist");
    		}
        }
        
        //--searching--//
    	if(Word !=null && !Word.isEmpty()) {
    		ESSearch.Search(Word, uname);
    	}
    	
    	//--List--//
    	if(!List.isEmpty() && List.equals("show")) {
    		ESPrint.Print(uname);
    	}
    	
    	//--Deleting path from list--//
    	if(Del !=null && !Del.isEmpty()) {
    		File dataDir = new File(Del);
    		if(dataDir.exists()) {
    			ESDelete.Delete(dataDir, uname);
    		} else {
    			System.out.println("Directory or file does not exist");
    		}
    	}
    }
}

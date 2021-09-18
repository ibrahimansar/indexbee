package Lucene.lucene;

import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;

@Command(name = "ElasticSearch", description = "App to perform Lucene indexing and searching operations using ES framework", mixinStandardHelpOptions = true, version = "1.0")
class ESApp extends ESIndexing implements Runnable { 

	@Option(names = "-path", description = "folder to be indexed; [usage: 'Lucene -path 'C:/Lucene/data' '] ")
	private String path = "h";
	
    @Option(names = "-search", description = "Searches given name; [usage: 'Lucene -search 'Lucene' '] ")
    private String Word;
    
    @Option(names = "-list", description = "Lists all indexed folder; [usage: 'Lucene -list 'show' '] ")
	private String List = "shw";
	
    @Option(names = "-delete", description = "delete indexed folder; [usage: 'Lucene -delete 'C:/Lucene/data' '] ")
	private String Del;
    
    @Override
    public void run() {
    	
    	//--indexing--//
    	if(path !=null && !path.isEmpty()) {
    		ESIndexing.main();
        }
        
        //--searching--//
    	if(Word !=null && !Word.isEmpty()) {
    		System.out.println("Searching in esapp");
    	}
    	
    	//--List--//
    	if(!List.isEmpty() && List.equals("show")) {
    		ESPrint.main();
    	}
    	
    	//--Deleting path from list--//
    	if(Del !=null && !Del.isEmpty()) {
    		ESDelete.main();
    	}
    }
}

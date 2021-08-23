package Lucene.lucene;
import java.io.File;
import java.io.IOException;

import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;

@Command(name = "Lucene", description = "App to perform Lucene indexing and searching operations", mixinStandardHelpOptions = true, version = "1.0")
class LuceneApp extends Indexing implements Runnable { 

	@Option(names = "-path", description = "folder to be indexed; [usage: 'Lucene -path 'C:/Lucene/data' '] ")
	private String path;
	
    @Option(names = "-search", description = "Searches given name; [usage: 'Lucene -search 'Lucene' '] ")
    private String Word;
    
    @Option(names = "-list", description = "Lists all indexed folder; [usage: 'Lucene -list 'show' '] ")
	private String List = "list";
	
    @Option(names = "-delete", description = "delete indexed folder; [usage: 'Lucene -delete 'C:/Lucene/data' '] ")
	private String Del;
    
    @Override
    public void run() {
    	String uname = System.getProperty("user.name");
    	File indexDir = new File("C:/Users/" + uname + "/Index");
    	
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
	        try {
				Search.searchIndex(indexDir, query, hits);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	//--List--//
    	if(!List.isEmpty() && List.equals("show")) {
    		try {
				Print.PrintIndex(indexDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	//--Deleting path from list--//
    	if(Del !=null && !Del.isEmpty()) {
    		File DataDir = new File(Del);
    		try {
				Delete.delDocuments(indexDir, "path", DataDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    public static void main(String... args) {
        int exitCode = new CommandLine(new LuceneApp()).execute(args);
        System.exit(exitCode);
    }
}

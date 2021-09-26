package Lucene.lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;

@Command(name = "Lucene", description = "App to perform Lucene indexing and searching operations using core lucene and Elastic Search", mixinStandardHelpOptions = true, version = "1.0")
class Main implements Runnable{
	
	static String uname = System.getProperty("user.name");
	File indexDir = new File("C:/Users/" + uname + "/Index");
	
	@Option(names = "-method", description = "Method to be used; [usage: 'Lucene -method Lucene ']")
	private String Method;
	
	@Option(names = "-which", description = "To know which method is in process; [usage: 'Lucene -which method ']")
	private String Which;
	
	@Option(names = "-index", description = "folder to be indexed; [usage: 'Lucene -index C:/Lucene/data '] ")
	private String path;
	
    	@Option(names = "-search", description = "Searches given name; [usage: 'Lucene -search Lucene '] ")
    	private String Word;
    
    	@Option(names = "-list", description = "Lists all indexed folder; [usage: 'Lucene -list show '] ")
	private String List;
	
    	@Option(names = "-delete", description = "delete indexed folder; [usage: 'Lucene -delete C:/Lucene/data '] ")
	private String Del;
	
	public void run() {
		
		//setting or switching method
		if(Method!=null && !Method.isEmpty()) {
			AppName.setAppName(Method, uname);
		}
		
		try {
			String AppName = GetAppName.getAppName(uname);
			
			//know current method
			if(Which!=null && Which.equals("method")) {
				System.out.println("You are current using " + AppName);
			}
			
			//check whether the method is Lucene or ElasticSearch
			if(AppName!=null && (AppName.equals("Lucene") || AppName.equals("ElasticSearch") )) {
				if(AppName.equals("Lucene")) {
					//LuceneApp
					
			    	//--indexing--//
			    	if(path !=null && !path.isEmpty()) {
			    		try(LuceneIndexing i = new LuceneIndexing()){
			                File dataDir = new File(path);
			                String suffix = "txt";   
			                if(dataDir.exists()) {
			                	i.index(indexDir, dataDir, suffix);
			                } else {
			                	System.out.println("Directory or file does not exist");
			                }
			 
			    		} catch(Exception e) {
			    			System.out.println(e);
			    		}
			        }
			        
			        //--searching--//
			    	if(Word !=null && !Word.isEmpty()) {
				        String query = Word;
				        int hits = 100;        
				        try {
							LuceneSearch.searchIndex(indexDir, query, hits);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	}
			    	
			    	//--List--//
			    	if(List !=null && List.equals("show")) {
			    		try {
							LucenePrint.PrintIndex(indexDir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	}
			    	
			    	//--Deleting path from list--//
			    	if(Del !=null && !Del.isEmpty()) {
			    		File DataDir = new File(Del);
			    		if(DataDir.exists()) {
			        		try {
			    				LuceneDelete.delDocuments(indexDir, "folder", DataDir);
			    			} catch (IOException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}
			    		} else {
			    			System.out.println("Directory or file does not exist");
			    		}
			    	}
				} else if (AppName.equals("ElasticSearch")) {
					//ElasticSearch
					
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
			    	if(List!=null && List.equals("show")) {
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
			} else {
				System.out.println("Please set any one of the methods (Lucene/ElasticSearch)");
				System.out.println("To set a method, type 'lucene -method Lucene (or) ElasticSearch'");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}

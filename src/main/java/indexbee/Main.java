package indexbee;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import indexbee.CommandLine.Command;
import indexbee.CommandLine.Option;

@Command(name = "indexbee", description = "a Command Line Application, for windows, where we can use either Apache Lucene or Elastic Search to perform operations based on indexing machanism such as complex searching.", mixinStandardHelpOptions = true, version = "1.0")
class Main implements Runnable {

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

		// setting or switching method
		if (Method != null && !Method.isEmpty()) {
			AppName.setAppName(Method, uname);
		}

		try {
			String AppName = GetAppName.getAppName(uname);

			// know current method
			if (Which != null && Which.equals("method")) {
				if (AppName != null) {
					System.out.println("You are current using " + AppName);
				} else {
					System.out.println("You didn't set any method yet");
				}
			}

			// check whether the method is Lucene or ElasticSearch
			if (AppName != null && (AppName.equals("Lucene") || AppName.equals("ElasticSearch"))) {
				if (AppName.equals("Lucene")) {
					// LuceneApp

					// --indexing--//
					if (path != null && !path.isEmpty()) {
						try (LuceneIndexing i = new LuceneIndexing()) {
							File dataDir = new File(path);
							String suffix = "txt";
							if (dataDir.exists()) {
								i.index(indexDir, dataDir, suffix);
							} else {
								System.out.println("Directory or file does not exist");
							}

						} catch (Exception e) {
							System.out.println(e);
						}
					}

					// --searching--//
					if (Word != null && !Word.isEmpty()) {
						System.out.println("The word found in");
						String query = Word;
						int hits = 10000;
						try {
							LuceneSearch.searchIndex(indexDir, query, hits);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// --List--//
					if (List != null && List.equals("show")) {
						System.out.println("List of the indexed folder is");
						try {
							LucenePrint.PrintIndex(indexDir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// --Deleting path from list--//
					if (Del != null && !Del.isEmpty()) {
						File DataDir = new File(Del);
						if (DataDir.exists()) {
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
					// ElasticSearch
					org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
					
					try {
						RestHighLevelClient client = new RestHighLevelClient(
						RestClient.builder(new HttpHost("localhost", 9200, "http")));

						// --indexing--//
						if (path != null && !path.isEmpty()) {
							File dataDir = new File(path);
							if (dataDir.exists()) {
								ESIndexing.Index(dataDir, uname, client);
							} else {
								System.out.println("Directory or file does not exist");
							}
						}

						// --searching--//
						if (Word != null && !Word.isEmpty()) {
							System.out.println("The word found in");
							ESSearch.Search(Word, uname, client);
						}

						// --List--//
						if (List != null && List.equals("show")) {
							System.out.println("List of the indexed folder is");
							ESPrint.Print(uname, client);
						}

						// --Deleting path from list--//
						if (Del != null && !Del.isEmpty()) {
							File dataDir = new File(Del);
							if (dataDir.exists()) {
								ESDelete.Delete(dataDir, uname, client);
							} else {
								System.out.println("Directory or file does not exist");
							}
						}
					} catch (ElasticsearchException e) {
						System.out.println("Failed to run elasticsearch.");
						System.out.println("Please ensure the elastic search started properly and the port is set to 9200");
					}
				}
			} else {
				System.out.println("Please set any one of the methods (Lucene/ElasticSearch)");
				System.out.println("To set a method, type 'lucene -method Lucene (or) ElasticSearch'");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		int exitCode = new CommandLine(new Main()).execute(args);
		System.exit(exitCode);
	}
}

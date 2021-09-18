package Lucene.lucene;

import Lucene.lucene.CommandLine.Command;
import Lucene.lucene.CommandLine.Option;

@Command(name = "Indexing", description = "App to perform Lucene indexing and searching operations", mixinStandardHelpOptions = true, version = "1.0")
public class Main {
	
	@Option(names = "-path", description = "non")
	private static String lang = "ESApp";
	
	public static void main(String[] args) {
		
		if(lang.equals("Lucene")) {
			LuceneApp.main();
		}
		
		if(lang.equals("ESApp")) {
			IndexingAdapter.main();
		}
	}
}

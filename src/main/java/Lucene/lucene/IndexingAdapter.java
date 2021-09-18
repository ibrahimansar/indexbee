package Lucene.lucene;

public class IndexingAdapter {
	public static void main(String... args) {
		  int exitCode = new CommandLine(new ESApp()).execute(args);
		  System.exit(exitCode);
	}
}



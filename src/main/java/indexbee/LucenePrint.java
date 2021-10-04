package indexbee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

//Lucene List class
public class LucenePrint {
	static void PrintIndex(File indexDir) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		for (File subfile : indexDir.listFiles()) {
			if (subfile.isDirectory()) {
				PrintIndex(subfile);
				continue;
			}
			Directory directory = FSDirectory.open(indexDir.toPath());
			DirectoryReader reader = DirectoryReader.open(directory);
			for (int i = 0; i < reader.maxDoc(); i++) {
				Document doc = reader.document(i);
				String Files = doc.get("folder");
				if (!list.contains(Files)) {
					list.add(Files);
				}
			}
		}
		for (String print : list) {
			System.out.println(print);
		}

	}
}

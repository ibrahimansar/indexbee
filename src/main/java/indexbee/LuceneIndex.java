package indexbee;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

//Lucene Index class
class LuceneIndex implements AutoCloseable {
	public void index(File indexDir, File dataDir, String suffix) throws IOException {
		indexDirectory(indexDir, dataDir, suffix);
	}

	public void indexDirectory(File indexDir, File dataDir, String suffix) throws IOException {
		String directoryName = dataDir.getName();
		File dir = new File(indexDir.getAbsolutePath() + "\\" + directoryName);
		if (!dir.exists()) {
			dir.mkdir();
		}
		IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
		conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(dir.toPath()), conf);

		File[] files = dataDir.listFiles();
		int count = 0;

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				indexDirectory(indexDir, file, suffix);
			} else {
				indexFileWithIndexWriter(indexWriter, file, suffix);
				count = count + 1;
			}
		}

		indexWriter.close();
		System.out.println(count + " files indexed");
	}

	private void indexFileWithIndexWriter(IndexWriter indexWriter, File file, String suffix) throws IOException {
		if (file.isHidden() || file.isDirectory() || !file.canRead() || !file.exists()) {
			return;
		}
		if (suffix != null && !file.getName().endsWith(suffix)) {
			return;
		}
		Document doc = new Document();
		doc.add(new TextField("contents", new FileReader(file)));
		doc.add(new StringField("path", file.getAbsolutePath(), Field.Store.YES));
		doc.add(new StringField("folder", file.getParent(), Field.Store.YES));
		indexWriter.addDocument(doc);
	}

	public void close() {
	}
}
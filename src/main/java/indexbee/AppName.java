package indexbee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class AppName {
	// set the method
	public static void setAppName(String method, String uname) {
		if (method != null && !method.isEmpty()) {
			if (method.equals("Lucene") || method.equals("ElasticSearch")) {
				try {
					File file = new File("C:/Users/" + uname + "/LuceneConfig.txt");
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(method);
					fileWriter.close();
					System.out.println("Switched to " + method + " App");
				} catch (Exception e) {
					System.out.println("Error in setting method");
					System.exit(1);
				}
			} else {
				System.out.println("Please enter a valid method name (Lucene/ElasticSearch)");
			}
		} else {
			System.out.println("Please enter a method name (Lucene/ElasticSearch)");
		}
	}
}

class GetAppName {
	public static String data;
	// get the method
	public static String getAppName(String uname) throws FileNotFoundException {
		File file = new File("C:/Users/" + uname + "/LuceneConfig.txt");
		if (file.length() == 0) {
			return null;
		} else {
			Scanner myReader = new Scanner(file);
			data = myReader.nextLine();
			myReader.close();
			return data;
		}
	}
}
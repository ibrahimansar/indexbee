package indexbee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class AppName {
	//set the method
	public static void setAppName(String Method, String uname) {
		if(Method!=null && !Method.isEmpty()) {
			if(Method.equals("Lucene") || Method.equals("ElasticSearch")) {
			    try{
			    	File f = new File("C:/Users/" + uname + "/LuceneConfig.txt");
			        FileWriter fw=new FileWriter(f);    
			        fw.write(Method);    
			        fw.close();    
			        System.out.println("Switched to " + Method + " App");
			    }catch(Exception e){
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

class GetAppName{
	public static String data;
	//get the method
	public static String getAppName(String uname) throws FileNotFoundException {
		File f = new File("C:/Users/" + uname + "/LuceneConfig.txt");
        if(f.length()==0) {
        	return null;
        } else {
	        Scanner myReader = new Scanner(f);
			data = myReader.nextLine();
	        myReader.close();
	        return data;
        }
	}
}
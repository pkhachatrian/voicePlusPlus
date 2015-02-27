package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Hashtable;
import java.util.Set;

	
public class Keyword {
	private static Hashtable<String, String> keywords;
	
	public static void instantiateHashTable(String fileName) {
		keywords = new Hashtable<String, String>();
	}
	
	public static void main(String[] args) {
		String fileName;
		
		fileName = "./src/main/resources/keywords.txt";
		instantiateHashTable(fileName);
		
		Set<String> keys = keywords.keySet();
		for (String key : keys)
			System.out.println("Value of " + key + " is " + keywords.get(key));
	}
}

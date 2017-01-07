import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import java.util.ArrayList;

public class StringOperations {
	private FileProcessor fileProcessor = null;
	private TreeMap<String,Integer> countOccurence = null;;
	public StringOperations(FileProcessor fileProcessor_in) {
		fileProcessor = fileProcessor_in;
		countOccurence = new TreeMap<String, Integer>();
	}
	public void countOccurence(String output_file) throws IOException {
		String localRead;
		int counter = 0;
		String mapKey = null;
		String localReadValue[] = null;
		while ((localRead = fileProcessor.readLineFromFile()) != null) {
			localRead = localRead.toLowerCase();
			localRead = localRead.replaceAll("\\bby\\b","");
			localRead = localRead.replaceAll("\\band\\b","");
			localRead = localRead.replaceAll("\\ban\\b","");
			localRead = localRead.replaceAll("\\bfrom\\b","");
			localRead = localRead.replaceAll("\\bfor\\b","");
			localRead = localRead.replaceAll("\\bof\\b","");
			localRead = localRead.replaceAll("\\bhence\\b","");
			localRead = localRead.replaceAll("\\bof\\b","");
			localRead = localRead.replaceAll("\\bthe\\b","");
			localRead = localRead.replaceAll("\\bwith\\b","");
			localRead = localRead.replaceAll("\\bwithin\\b","");
			localRead = localRead.replaceAll("\\bwho\\b","");
			localRead = localRead.replaceAll("\\bwhen\\b","");
			localRead = localRead.replaceAll("\\bwhere\\b","");
			localRead = localRead.replaceAll("\\bwhy\\b","");
			localRead = localRead.replaceAll("\\bhow\\b","");
			localRead = localRead.replaceAll("\\bwhom\\b","");
			localRead = localRead.replaceAll("\\bhave\\b","");
			localRead = localRead.replaceAll("\\bhad\\b","");
			localRead = localRead.replaceAll("\\bhas\\b","");
			localRead = localRead.replaceAll("\\bbut\\b","");
			localRead = localRead.replaceAll("\\bnot\\b","");
			localRead = localRead.replaceAll("\\bdo\\b","");
			localRead = localRead.replaceAll("\\bdoes\\b","");
			localRead = localRead.replaceAll("\\bdone\\b","");
			localRead = localRead.replaceAll("\\ba\\b","");
			localRead = localRead.replaceAll("\\bin\\b","");
			localRead = localRead.replaceAll("-", " ");
			localRead = localRead.replaceAll("\\s{2,}", " ").trim();
			localReadValue = localRead.split(" ");
			for (int index = 0; index < localReadValue.length; index++) {
				if(localReadValue[index].startsWith("\"") || localReadValue[index].endsWith("\"")) {
					localReadValue[index] = localReadValue[index].replaceAll("^\"|\"$", "");
				}
				if(localReadValue[index].endsWith(".")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].endsWith(",")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].endsWith(":")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].endsWith(";")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].endsWith("?")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].endsWith("!")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].startsWith("'") || localReadValue[index].endsWith("'")) {
					localReadValue[index] = localReadValue[index].replaceAll("^'|'$", "");
				}
				if(localReadValue[index].startsWith("(") || (localReadValue[index].endsWith("("))){
					localReadValue[index] = localReadValue[index].substring(1, localReadValue[index].length());
				}
				if(localReadValue[index].endsWith(")") || (localReadValue[index].startsWith(")"))){
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].startsWith("[") || (localReadValue[index].endsWith("["))){
					localReadValue[index] = localReadValue[index].substring(1, localReadValue[index].length());
				}
				if(localReadValue[index].endsWith("]") || (localReadValue[index].startsWith("]"))){
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if(localReadValue[index].contains("'" )){
					localReadValue[index] = localReadValue[index].replaceAll("'", "");
				}
				if (localReadValue[index].endsWith("'s")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-2);
				}
				if (localReadValue[index].endsWith("s'")) {
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-2);
				}
				if(localReadValue[index].endsWith("ies") && !(localReadValue[index].endsWith("eies") || localReadValue[index].endsWith("aies"))){
					localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-3) + "y";
				}
				if (localReadValue[index].endsWith("es") && !(localReadValue[index].endsWith("ees") || localReadValue[index].endsWith("oes") || localReadValue[index].endsWith("aes"))) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				if (localReadValue[index].endsWith("s") && !(localReadValue[index].endsWith("us") || localReadValue[index].endsWith("ss"))) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length()-1);
				}
				localReadValue[index] = localReadValue[index].replaceAll("\\bby\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\band\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\ban\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bfrom\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bfor\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bof\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bhence\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bof\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bthe\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwith\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwithin\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwho\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwhen\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwhere\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwhy\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bhow\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bwhom\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bhave\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bhad\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bhas\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bbut\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bnot\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bdo\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bdoes\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bdone\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\ba\\b","");
				localReadValue[index] = localReadValue[index].replaceAll("\\bbin\\b","");
			}
			for (int index = 0; index < localReadValue.length; index++) {
				if(localReadValue[index].startsWith("<") && (localReadValue[index].endsWith(">") && !(localReadValue[index].startsWith("</")))){
					counter = counter+1;
					continue;
				}
				if(localReadValue[index].startsWith("</") && (localReadValue[index].endsWith(">"))){
					counter = counter-1;
					continue;
				}
				if(counter>0){
					if((localReadValue[index].length()==1) || (localReadValue[index].isEmpty()) || (localReadValue[index].equals(" "))){
						continue;
					}
					else{
						mapKey=localReadValue[index];
						if(countOccurence.containsKey(mapKey)){
							countOccurence.put(mapKey, countOccurence.get(mapKey) + 1);
						}
						else{
							countOccurence.put(mapKey, 1);
						}
					}
				}
			}
		}
		FileWriter outputFile = new FileWriter(output_file);
		PrintWriter file = new PrintWriter(outputFile);
		for(Map.Entry<String,Integer> entry : countOccurence.entrySet()) {
			  String key = entry.getKey();
			  Integer value = entry.getValue();
			  
			  file.println(key + " " + value);
			}
		file.close();
		outputFile.close();
	}
}
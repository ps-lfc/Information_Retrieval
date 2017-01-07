import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.html.HTMLDocument.Iterator;

import java.util.ArrayList;
import java.util.HashMap;

public class StringOperations {
	private FileProcessor fileProcessor = null;
	private Map<String, ArrayList<Integer>> countOccurence = null;;
	public StringOperations(FileProcessor fileProcessor_in) {
		fileProcessor = fileProcessor_in;
		countOccurence = new TreeMap<String, ArrayList<Integer>>();
	}
		
	public void countOccurence() throws IOException {
		String localRead;
		String mapKey = null;
		String firstWord;
		String localReadValue[] = null;
		while ((localRead = fileProcessor.readLineFromFile()) != null) {
			localRead = localRead.toLowerCase();
			localRead = localRead.replaceAll("\\bby\\b","");
			localRead = localRead.replaceAll("\\band\\b","");
			localRead = localRead.replaceAll("\\ban\\b","");
			localRead = localRead.replaceAll("\\bfrom\\b","");
			localRead = localRead.replaceAll("\\bof\\b","");
			localRead = localRead.replaceAll("\\bthe\\b","");
			localRead = localRead.replaceAll("\\bwith\\b","");
			localRead = localRead.replaceAll("\\ba\\b","");
			localRead = localRead.replaceAll("\\bin\\b","");
			localRead = localRead.replaceAll("[.,:;?!]","");
			localRead = localRead.replaceAll("\\s{2,}", " ").trim();
			localReadValue = localRead.split(" ");
			for (int index = 0; index < localReadValue.length; index++) {
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
			}
			firstWord=localReadValue[0];
				if(firstWord.equals("excellent")){
				for (int index = 1; index < localReadValue.length; index++){
					mapKey=localReadValue[index];
					if(countOccurence.containsKey(mapKey)){
						ArrayList<Integer> temp_list=countOccurence.get(mapKey);
						temp_list.set(0, temp_list.get(0)+1);
						countOccurence.put(mapKey, temp_list);
					}
					else{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(0,1);
						list.add(1,0);
						list.add(2,0);
						countOccurence.put(mapKey, list);
					}
				}
			}
			if(firstWord.equals("good")){
				for (int index = 1; index < localReadValue.length; index++){
					mapKey=localReadValue[index];
					if(countOccurence.containsKey(mapKey)){
						ArrayList<Integer> temp_list=countOccurence.get(mapKey);
						temp_list.set(1, temp_list.get(1)+1);
						countOccurence.put(mapKey, temp_list);
					}
					else{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(0,0);
						list.add(1,1);
						list.add(2,0);
						countOccurence.put(mapKey, list);
					}
				}
			}
			if(firstWord.equals("bad")){
				for (int index = 1; index < localReadValue.length; index++){
					mapKey=localReadValue[index];
					if(countOccurence.containsKey(mapKey)){
						ArrayList<Integer> temp_list=countOccurence.get(mapKey);
						temp_list.set(2, temp_list.get(2)+1);
						countOccurence.put(mapKey, temp_list);
					}
					else{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(0,0);
						list.add(1,0);
						list.add(2,1);
						countOccurence.put(mapKey, list);
					}
				}
			}
		}
		PrintWriter file = new PrintWriter("output.txt");
		java.util.Iterator<String> iterator= countOccurence.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next().toString();
			
			file.println(key + " Ecount = "+countOccurence.get(key).get(0)+" Gcount = "+countOccurence.get(key).get(1)+ " Bcount = "+countOccurence.get(key).get(2));

		}
		file.close();
	}
}
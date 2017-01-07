import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StringOperations {
	private FileProcessor fileProcessor = null;
	private TreeMap<String, Integer> countOccurence;
	private HashMap<String, Integer> countTotalOccurence;
	private TreeMap<String, ArrayList<Integer>> countDiffOccurence;
	private TreeMap<String, ArrayList<Integer>> DiffOccurence;

	public StringOperations(FileProcessor fileProcessor_in) {
		fileProcessor = fileProcessor_in;

	}

	public StringOperations() {
		countOccurence = new TreeMap<String, Integer>();
		countTotalOccurence = new HashMap<String, Integer>();
		countDiffOccurence = new TreeMap<String, ArrayList<Integer>>();
		DiffOccurence = new TreeMap<String, ArrayList<Integer>>();
	}

	int totalWords = 0;

	public void countOccurence(String rootDirectory) throws IOException {
		String localRead;
		int counter = 0;
		int docID = 0;
		int OlddocID = 0;
		int Words;
		int tots = 0;
		String mapKey = null;
		ArrayList<String> localReadVal = new ArrayList<String>();
		ArrayList<String> localReadSnip = new ArrayList<String>();
		String localReadValue[] = null;
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<String> filePath = new ArrayList<String>();
		File directory = new File(rootDirectory);
		File[] folders = directory.listFiles();

		for (File file : folders) {
			if (file.isFile()) {
				fileList.add(file.getAbsoluteFile());
			}

			if (file.isDirectory()) {
				FileProcessor.listfilesInDirectoyr(file.getAbsolutePath(), fileList);
			}
		}
		ArrayList<Integer> CountWords = new ArrayList<Integer>();
		int iterator = 0;
		FileWriter outputFile2 = new FileWriter("docsTable.csv");
		PrintWriter file = new PrintWriter(outputFile2);
		file.println("doc number" + "," + "headline" + "," + "doc length |D|" + "," + "snippet" + "," + "Doc path");
		while (iterator < fileList.size()) {
			for (int i = 0; i <= fileList.size(); i++) {
				CountWords.add(i, 0);
			}
			FileProcessor fp = new FileProcessor(fileList.get(iterator).getAbsolutePath());
			filePath.add(fileList.get(iterator).getAbsolutePath());
			while ((localRead = fp.readLineFromFile()) != null) {
				localRead = localRead.replaceAll(",", "");
				localRead = localRead.replaceAll("\\s{2,}", " ").trim();
				localRead = localRead.replaceAll("-", " ");
				localReadValue = localRead.split(" ");
				for (int index = 0; index < localReadValue.length; index++) {
					localReadVal.add(localReadValue[index]);
					localReadSnip.add(localReadValue[index]);
					localReadValue[index] = localReadValue[index].replaceAll("`", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\s+", "");
					localReadValue[index].toLowerCase();
					if (localReadValue[index].startsWith("\"") || localReadValue[index].endsWith("\"")) {
						localReadValue[index] = localReadValue[index].replaceAll("^\"|\"$", "");
					}
					if (localReadValue[index].endsWith(".")) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].endsWith(",")) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].endsWith(":")) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].endsWith(";")) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].endsWith("?")) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].endsWith("!")) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].startsWith("'") || localReadValue[index].endsWith("'")) {
						localReadValue[index] = localReadValue[index].replaceAll("^'|'$", "");
					}
					if (localReadValue[index].startsWith("(") || (localReadValue[index].endsWith("("))) {
						localReadValue[index] = localReadValue[index].substring(1, localReadValue[index].length());
					}
					if (localReadValue[index].endsWith(")") || (localReadValue[index].startsWith(")"))) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].startsWith("[") || (localReadValue[index].endsWith("["))) {
						localReadValue[index] = localReadValue[index].substring(1, localReadValue[index].length());
					}
					if (localReadValue[index].endsWith("]") || (localReadValue[index].startsWith("]"))) {
						localReadValue[index] = localReadValue[index].substring(0, localReadValue[index].length() - 1);
					}
					if (localReadValue[index].contains("'")) {
						localReadValue[index] = localReadValue[index].replaceAll("'", "");
					}
					localReadValue[index] = localReadValue[index].replaceAll("\\bby\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\band\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\ban\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bfrom\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bfor\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bof\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhence\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bof\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bthe\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwith\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwithin\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwho\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhen\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhere\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhy\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhow\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhom\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhave\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhad\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhas\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bbut\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bnot\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bdo\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bdoes\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bdone\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\ba\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bbin\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\s{2,}", " ").trim();
				}
				for (int index = 0; index < localReadValue.length; index++) {
					localReadValue[index] = localReadValue[index].replaceAll(" ", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\.+", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\_+", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bby\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\band\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\ban\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bfrom\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bfor\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bof\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhence\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bof\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bthe\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwith\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwithin\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwho\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhen\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhere\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhy\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhow\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bwhom\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhave\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhad\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bhas\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bbut\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bnot\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bdo\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bdoes\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bdone\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\ba\\b", "");
					localReadValue[index] = localReadValue[index].replaceAll("\\bbin\\b", "");
					if (localReadValue[index].startsWith("</") && (localReadValue[index].endsWith(">"))) {
						counter = counter - 1;
						continue;
					}
					if (localReadValue[index].startsWith("<") && (localReadValue[index].endsWith(">") && !(localReadValue[index].startsWith("</")))) {
						counter = counter + 1;
						continue;
					}
					localReadValue[index] = localReadValue[index].trim();
					if ((localReadValue[index].length() == 1) || (localReadValue[index].isEmpty()) || (localReadValue[index].equals(" ")) || (localReadValue[index].equals("")) || (localReadValue[index].trim().length() == 0)) {
							continue;
						} else {
								mapKey = localReadValue[index];
								mapKey = mapKey.replaceAll("[^a-zA-Z0-9]+", "");
								mapKey = mapKey.replaceAll("[\\W+]+", "");
								mapKey = mapKey.replaceAll(" ", "");
								if (countTotalOccurence.containsKey(mapKey)) {
									countTotalOccurence.put(mapKey, countTotalOccurence.get(mapKey) + 1);
								} else {
									countTotalOccurence.put(mapKey, 1);
								}
								mapKey = mapKey.toLowerCase();
								mapKey = mapKey.replaceAll("\\bbut\\b", "");
								mapKey = mapKey.replaceAll("\\bby\\b", "");
								mapKey = mapKey.replaceAll("\\band\\b", "");
								mapKey = mapKey.replaceAll("\\ban\\b", "");
								mapKey = mapKey.replaceAll("\\bfrom\\b", "");
								mapKey = mapKey.replaceAll("\\bfor\\b", "");
								mapKey = mapKey.replaceAll("\\bof\\b", "");
								mapKey = mapKey.replaceAll("\\bhence\\b", "");
								mapKey = mapKey.replaceAll("\\bof\\b", "");
								mapKey = mapKey.replaceAll("\\bthe\\b", "");
								mapKey = mapKey.replaceAll("\\bwith\\b", "");
								mapKey = mapKey.replaceAll("\\bwithin\\b", "");
								mapKey = mapKey.replaceAll("\\bwho\\b", "");
								mapKey = mapKey.replaceAll("\\bwhen\\b", "");
								mapKey = mapKey.replaceAll("\\bwhere\\b", "");
								mapKey = mapKey.replaceAll("\\bwhy\\b", "");
								mapKey = mapKey.replaceAll("\\bhow\\b", "");
								mapKey = mapKey.replaceAll("\\bwhom\\b", "");
								mapKey = mapKey.replaceAll("\\bhave\\b", "");
								mapKey = mapKey.replaceAll("\\bhad\\b", "");
								mapKey = mapKey.replaceAll("\\bhas\\b", "");
								mapKey = mapKey.replaceAll("\\bbut\\b", "");
								mapKey = mapKey.replaceAll("\\bnot\\b", "");
								mapKey = mapKey.replaceAll("\\bdo\\b", "");
								mapKey = mapKey.replaceAll("\\bdoes\\b", "");
								mapKey = mapKey.replaceAll("\\bdone\\b", "");
								mapKey = mapKey.replaceAll("\\ba\\b", "");
								mapKey = mapKey.replaceAll("\\bbin\\b", "");
								if (mapKey.endsWith("'s")) {
									mapKey = mapKey.substring(0, mapKey.length() - 2);
								}
								if (mapKey.endsWith("s'")) {
									mapKey = mapKey.substring(0, mapKey.length() - 2);
								}
								if (mapKey.endsWith("ies") && !(mapKey.endsWith("eies") || mapKey.endsWith("aies"))) {
									mapKey = mapKey.substring(0, mapKey.length() - 3) + "y";
								}
								if (mapKey.endsWith("es") && !(mapKey.endsWith("ees") || mapKey.endsWith("oes") || mapKey.endsWith("aes"))) {
									mapKey = mapKey.substring(0, mapKey.length() - 1);
								}
								if (mapKey.endsWith("s") && !(mapKey.endsWith("us") || mapKey.endsWith("ss"))) {
									mapKey = mapKey.substring(0, mapKey.length() - 1);
								}
								mapKey = mapKey.replaceAll("\\bbut\\b", "");
								mapKey = mapKey.replaceAll("\\bby\\b", "");
								mapKey = mapKey.replaceAll("\\band\\b", "");
								mapKey = mapKey.replaceAll("\\ban\\b", "");
								mapKey = mapKey.replaceAll("\\bfrom\\b", "");
								mapKey = mapKey.replaceAll("\\bfor\\b", "");
								mapKey = mapKey.replaceAll("\\bof\\b", "");
								mapKey = mapKey.replaceAll("\\bhence\\b", "");
								mapKey = mapKey.replaceAll("\\bof\\b", "");
								mapKey = mapKey.replaceAll("\\bthe\\b", "");
								mapKey = mapKey.replaceAll("\\bwith\\b", "");
								mapKey = mapKey.replaceAll("\\bwithin\\b", "");
								mapKey = mapKey.replaceAll("\\bwho\\b", "");
								mapKey = mapKey.replaceAll("\\bwhen\\b", "");
								mapKey = mapKey.replaceAll("\\bwhere\\b", "");
								mapKey = mapKey.replaceAll("\\bwhy\\b", "");
								mapKey = mapKey.replaceAll("\\bhow\\b", "");
								mapKey = mapKey.replaceAll("\\bwhom\\b", "");
								mapKey = mapKey.replaceAll("\\bhave\\b", "");
								mapKey = mapKey.replaceAll("\\bhad\\b", "");
								mapKey = mapKey.replaceAll("\\bhas\\b", "");
								mapKey = mapKey.replaceAll("\\bbut\\b", "");
								mapKey = mapKey.replaceAll("\\bnot\\b", "");
								mapKey = mapKey.replaceAll("\\bdo\\b", "");
								mapKey = mapKey.replaceAll("\\bdoes\\b", "");
								mapKey = mapKey.replaceAll("\\bdone\\b", "");
								mapKey = mapKey.replaceAll("\\ba\\b", "");
								mapKey = mapKey.replaceAll("\\bbin\\b", "");
								mapKey = mapKey.replaceAll("\\s{2,}", "").trim();
								mapKey = mapKey.replaceAll("\\s+", "");
								mapKey = mapKey.replaceAll("\\s","");
								if(mapKey.equals("in")){
									continue;
								}
								if(mapKey.length()==1){
									continue;
								}
								if(!(mapKey.length() == 1) || !(mapKey.isEmpty()) || !(mapKey.equals(" ")) || !(mapKey.equals("")) || !(mapKey.length() == 0)){
									if (countDiffOccurence.containsKey(mapKey)) {
										ArrayList<Integer> list = countDiffOccurence.get(mapKey);
										if (!list.contains(docID)) {
											list.add(docID);
											countDiffOccurence.put(mapKey, list);
										}
									} else {
										if(mapKey.length()==0){
											continue;
										} else{
											ArrayList<Integer> list1 = new ArrayList<Integer>();
											list1.add(docID);
											countDiffOccurence.put(mapKey, list1);
										}
									}
									if (DiffOccurence.containsKey(mapKey)) {
										ArrayList<Integer> list1 = DiffOccurence.get(mapKey);
										int num = 0;
										num = list1.get(iterator).intValue();
										list1.set(iterator, num+1);
										DiffOccurence.put(mapKey, list1);
									} else {
										if(mapKey.length()==0){
											continue;
										} else{
											ArrayList<Integer> list1 = new ArrayList<>(fileList.size());
											for (int i = 0; i < fileList.size(); i++) {
												list1.add(i , 0);
											}
											list1.add(iterator , 1 );
											DiffOccurence.put(mapKey, list1);
										}
									}
									if (countOccurence.containsKey(mapKey) && !(mapKey.length()==0)) {
										countOccurence.put(mapKey, countOccurence.get(mapKey) + 1);
										if(mapKey.length()==0){
											continue;
										} else {
											Words = CountWords.get(docID);
											Words++;
											CountWords.add(docID, Words);
										}	
									} else {
										mapKey = mapKey.toLowerCase();
										countOccurence.put(mapKey, 1);
										if(mapKey.length()==0){
											continue;
										} else{
											Words = CountWords.get(docID);
											Words++;
											CountWords.add(docID, Words);
										}
									}
								}
							}
						}
					}

			int iterate = 0;
			ArrayList<String> headLine = new ArrayList<String>();
			while (iterate < localReadVal.size()) {
				if (localReadVal.get(iterate).equals("<HEADLINE>")) {
					iterate++;
					while (!localReadVal.get(iterate).equals("</HEADLINE>")) {
						headLine.add(localReadVal.get(iterate));
						iterate++;
					}
					break;
				}
				iterate++;
			}

			int iterateSnip = 0;
			ArrayList<String> Snippet = new ArrayList<String>();
			while (iterate < localReadSnip.size()) {
				if (localReadSnip.get(iterateSnip).equals("<TEXT>")) {
					iterateSnip++;
					while (!localReadSnip.get(iterateSnip).equals("</TEXT>")) {
						if (localReadSnip.get(iterateSnip).startsWith("<")
								&& (localReadSnip.get(iterateSnip).endsWith(">"))) {
							iterateSnip++;
							continue;
						}
						if (localReadSnip.get(iterateSnip).startsWith("</")
								&& (localReadSnip.get(iterateSnip).endsWith(">"))) {
							iterateSnip++;
							continue;
						} else {
							Snippet.add(localReadSnip.get(iterateSnip));
							iterateSnip++;
						}

					}
					break;
				}
				iterateSnip++;
			}
			localReadVal.clear();
			localReadSnip.clear();
			String head = "";
			for (String s : headLine) {
				head += s + " ";
			}
			headLine.clear();
			String Snip = "";
			for (int i = 0; i < 40; i++) {
				if (i == Snippet.size()) {
					break;
				}
				Snip += Snippet.get(i) + " ";

			}
			Snippet.clear();
			iterator++;
			docID++;
			file.println(docID + "," + head + "," + CountWords.get(docID - 1) + "," + Snip + "," + filePath.get(docID - 1));
			countTotalOccurence.clear();

		}
		ArrayList<Integer> postingSize = new ArrayList<Integer>();
		for (Entry<String, ArrayList<Integer>> entry : countDiffOccurence.entrySet()) {
			String key = entry.getKey();
			ArrayList<Integer> value = entry.getValue();
			postingSize.add(value.size());
			totalWords++;
		}
		HashMap <Integer,Integer> offset = new HashMap<Integer,Integer>();
		for(int i=0;i<fileList.size();i++){
			offset.put(i, 0);
		}
		int off=0;
		int offCount=0;
		FileWriter outputFile3 = new FileWriter("postings.csv");
		PrintWriter file3 = new PrintWriter(outputFile3);
		file3.println("docId" +","+"tf");
		for (Entry<String, ArrayList<Integer>> entry : DiffOccurence.entrySet()) {
			ArrayList<Integer> value = entry.getValue();
			String key = entry.getKey();
			for (int i = 0; i < fileList.size(); i++) {
				if ((value.get(i) == 0)) {
					continue;
				}else{
					Integer num =i+1;
					file3.println(num + "," + value.get(i));
					off++;
				}
			}
			offset.put(offCount,off);
			offCount++;
		}
		file3.close();
		outputFile3.close();
		file.close();
		outputFile2.close();
		int counter1 = 1;
		int temp=2;
		FileWriter outputFile1 = new FileWriter("dictionary.csv");
		PrintWriter file1 = new PrintWriter(outputFile1);
		file1.println("term" + "," + "cf" +","+"df"+","+"offset");
		for (Map.Entry<String, Integer> entry : countOccurence.entrySet()) {
			
			String key = entry.getKey();
			Integer value = entry.getValue();
			if(!entry.getKey().trim().isEmpty()){
			int CounterCheck = counter1 - 1;
			tots = tots + value;
			file1.println(key + "," + value + "," + postingSize.get(counter1 - 1) + "," + temp);
			temp = offset.get(counter1-1) +2;
			counter1++;
			totalWords++;
			}
		}
		file1.close();
		outputFile1.close();
		FileWriter outputFile = new FileWriter("total.txt");
		PrintWriter file2 = new PrintWriter(outputFile);
		file2.println(tots);
		file2.close();
		outputFile.close();
	
	}
}
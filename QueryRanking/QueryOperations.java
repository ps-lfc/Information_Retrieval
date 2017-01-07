import java.io.FileWriter;

import java.io.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class QueryOperations {
	private FileProcessor fileProcessor = null;
	private TreeMap<String, Integer> DictionaryTreeOffset; 
	private TreeMap<String, Integer> DictionaryTreecf; 
	private TreeMap<String, Integer> DictionaryTreedf; 
	private HashMap<String, HashMap<Integer,Double>> LoggedHash;
	//private HashMap<String, ArrayList<Integer>> UseHash;
	public QueryOperations() {
		DictionaryTreeOffset = new TreeMap<String, Integer>();
		DictionaryTreecf = new TreeMap<String, Integer>();
		DictionaryTreedf = new TreeMap<String, Integer>();
		LoggedHash = new HashMap<String, HashMap<Integer,Double>>();
		//UseHash = new HashMap<String,ArrayList<Integer>>();
	}
	public void runQuery() throws IOException{
	String query="";
	int counter=0;
	String mapKey=null;
	int temp;
	int docFrequency=0;
	int docOccurence = 0;
	double tobeLogged;
	double Logged =0;
	double HalftobeLogged;
	double HalfLogged = 0;
	ArrayList<Integer> UseHash = new ArrayList<Integer>();
	int temp1;
	int totalFrequency=0;
	int temp2;
	int tf;
	int Offset=0;
	int docID=0;
	String temp3;
	String head;
	String Snippet;
	String queryValue[] = null;
	String fileRead = null;
	String fileReadArray[] = null;
	ArrayList<Integer> postingDoc = new ArrayList<Integer>();
	ArrayList<Integer> postingtf = new ArrayList<Integer>();
	ArrayList<Integer> DocsTotal = new ArrayList<Integer>();
	ArrayList<String> Dictionary = new ArrayList<String>();
	ArrayList<String> Head = new ArrayList<String>();
	ArrayList<String> Path = new ArrayList<String>();
	String query1;
	ArrayList<String> snippet = new ArrayList<String>();
	ArrayList<Integer> probability = new ArrayList<Integer>();
	ArrayList<Integer> flag = new ArrayList<Integer>();
	ArrayList<Double> FinalList = new ArrayList<Double>();
	FileWriter outputFile1 = new FileWriter("result.txt");
	PrintWriter file1 = new PrintWriter(outputFile1);
	FileProcessor fp = new FileProcessor("./psachde3_java_part2/total.txt");
	String readTotal = fp.readLineFromFile();
	int totalWords = Integer.parseInt(readTotal);
	FileProcessor fp1 = new FileProcessor("./psachde3_java_part2/dictionary.csv");
	while((fileRead = fp1.readLineFromFile())!=null){
		fileReadArray = fileRead.split(",");
		try{
				temp = Integer.parseInt(fileReadArray[3]);
				temp1 = Integer.parseInt(fileReadArray[1]);
				temp2 = Integer.parseInt(fileReadArray[2]);
		}
		catch(NumberFormatException ex){
			continue;
		}
		DictionaryTreeOffset.put(fileReadArray[0], temp);
		DictionaryTreecf.put(fileReadArray[0], temp1);
		DictionaryTreedf.put(fileReadArray[0], temp2);
	}
	FileProcessor fp2 = new FileProcessor("./psachde3_java_part2/postings.csv");
	while((fileRead = fp2.readLineFromFile())!=null){
		fileReadArray = fileRead.split(",");
		try{
			temp = Integer.parseInt(fileReadArray[0]);	
		}
		catch(NumberFormatException ex){
			continue;
		}
		try{
			temp1 = Integer.parseInt(fileReadArray[1]);	
		}
		catch(NumberFormatException ex){
			continue;
		}
		postingDoc.add(temp);
		postingtf.add(temp1);
	}
	FileProcessor fp3 = new FileProcessor("./psachde3_java_part2/docsTable.csv");
	while((fileRead = fp3.readLineFromFile())!=null){
		fileReadArray = fileRead.split(",");
		try{
			temp = Integer.parseInt(fileReadArray[2]);
			//temp2 = Integer.parseInt(fileReadArray[0]);
			if(true){
				Head.add(fileReadArray[1]);
				snippet.add(fileReadArray[3]);
				Path.add(fileReadArray[4]);
			}
		}
		catch(NumberFormatException ex){
			continue;
		}
			//Head.add(temp2, fileReadArray[1]);
		DocsTotal.add(temp);
	}
	while(!(query.trim().equals("EXIT"))){
		LoggedHash.clear();
		Scanner input=new Scanner(System.in);
		System.out.println("Please enter the query");
		query1 = input.nextLine();
		query = query1;
		if(query.trim().equals("EXIT")){
			break;
		}
		
		for(int i=0;i<=DocsTotal.size();i++){
			flag.add(i, 0);
		}
		query = query.replaceAll(",", "");
		query = query.replaceAll("\\s{2,}", " ").trim();
		query = query.replaceAll("-", " ");
		queryValue = query.split(" ");
		for (int index = 0; index < queryValue.length; index++) {
			queryValue[index] = queryValue[index].replaceAll("`", "");
			queryValue[index] = queryValue[index].replaceAll("\\s+", "");
			queryValue[index].toLowerCase();
			if (queryValue[index].startsWith("\"") || queryValue[index].endsWith("\"")) {
				queryValue[index] = queryValue[index].replaceAll("^\"|\"$", "");
			}
			if (queryValue[index].endsWith(".")) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].endsWith(",")) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].endsWith(":")) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].endsWith(";")) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].endsWith("?")) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].endsWith("!")) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].startsWith("'") || queryValue[index].endsWith("'")) {
				queryValue[index] = queryValue[index].replaceAll("^'|'$", "");
			}
			if (queryValue[index].startsWith("(") || (queryValue[index].endsWith("("))) {
				queryValue[index] = queryValue[index].substring(1, queryValue[index].length());
			}
			if (queryValue[index].endsWith(")") || (queryValue[index].startsWith(")"))) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].startsWith("[") || (queryValue[index].endsWith("["))) {
				queryValue[index] = queryValue[index].substring(1, queryValue[index].length());
			}
			if (queryValue[index].endsWith("]") || (queryValue[index].startsWith("]"))) {
				queryValue[index] = queryValue[index].substring(0, queryValue[index].length() - 1);
			}
			if (queryValue[index].contains("'")) {
				queryValue[index] = queryValue[index].replaceAll("'", "");
			}
			queryValue[index] = queryValue[index].replaceAll("\\bby\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\band\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\ban\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bfrom\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bfor\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bof\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhence\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bof\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bthe\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwith\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwithin\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwho\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhen\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhere\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhy\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhow\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhom\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhave\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhad\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhas\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bbut\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bnot\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bdo\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bdoes\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bdone\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\ba\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bbin\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\s{2,}", " ").trim();
		}
		UseHash.clear();
		ArrayList<Integer> CurrentHash = new ArrayList<Integer>();
		HashMap<Integer, Double> FinalHash= new HashMap<Integer,Double>();
		TreeMap<Double,Integer> TreeHash= new TreeMap<Double,Integer>();
		for (int index = 0; index < queryValue.length; index++) {
			queryValue[index] = queryValue[index].replaceAll(" ", "");
			queryValue[index] = queryValue[index].replaceAll("\\.+", "");
			queryValue[index] = queryValue[index].replaceAll("\\_+", "");
			queryValue[index] = queryValue[index].replaceAll("\\bby\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\band\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\ban\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bfrom\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bfor\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bof\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhence\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bof\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bthe\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwith\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwithin\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwho\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhen\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhere\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhy\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhow\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bwhom\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhave\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhad\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bhas\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bbut\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bnot\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bdo\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bdoes\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bdone\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\ba\\b", "");
			queryValue[index] = queryValue[index].replaceAll("\\bbin\\b", "");
			if (queryValue[index].startsWith("</") && (queryValue[index].endsWith(">"))) {
				counter = counter - 1;
				continue;
			}
			if (queryValue[index].startsWith("<") && (queryValue[index].endsWith(">") && !(queryValue[index].startsWith("</")))) {
				counter = counter + 1;
				continue;
			}
			queryValue[index] = queryValue[index].trim();
			if ((queryValue[index].length() == 1) || (queryValue[index].isEmpty()) || (queryValue[index].equals(" ")) || (queryValue[index].equals("")) || (queryValue[index].trim().length() == 0)) {
					continue;
			} else {
					mapKey = queryValue[index];
					mapKey = mapKey.replaceAll("[^a-zA-Z0-9]+", "");
					mapKey = mapKey.replaceAll("[\\W+]+", "");
					mapKey = mapKey.replaceAll(" ", "");
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
						for (Map.Entry<String, Integer> entry : DictionaryTreeOffset.entrySet()) {
							String key = entry.getKey();
							Integer value = entry.getValue();
							if(key.equals(mapKey)){
								Offset = value;
							}
						}
						for (Map.Entry<String, Integer> entry : DictionaryTreedf.entrySet()) {
							String key = entry.getKey();
							Integer value = entry.getValue();
							if(mapKey.equals(key)){
								docFrequency = value;
								break;
							}
						}
						for (Map.Entry<String, Integer> entry : DictionaryTreecf.entrySet()) {
							String key = entry.getKey();
							Integer value = entry.getValue();
							if(mapKey.equals(key)){
								totalFrequency = value;
								break;
							}
						}
						CurrentHash.clear();
						for(int iter=0;iter<docFrequency;iter++){
							docID = postingDoc.get(Offset-2);
							tf = postingtf.get(Offset-2);
							HalftobeLogged = 0.1 *((double) totalFrequency)/(double)totalWords;
							HalfLogged = Math.log(HalftobeLogged)/Math.log(2);
							for(int iter1 = 1; iter1<=DocsTotal.size();iter1++){
								if(iter1 == docID){
									tobeLogged = (0.9*((double)tf/(double)DocsTotal.get(docID-1)) + 0.1 * ((double) totalFrequency)/(double)totalWords);
									Logged = Math.log(tobeLogged)/Math.log(2);
									HashMap<Integer,Double> innerHashMap = LoggedHash.get(mapKey);
									if(innerHashMap == null){
										innerHashMap = new HashMap<Integer,Double>();
										LoggedHash.put(mapKey, innerHashMap);
										//flag.remove(iter);
										//flag.add(iter1, 1);
									}
									else{
										LoggedHash.put(mapKey, innerHashMap);
										//flag.add(iter1, 1);
									}
									innerHashMap.put(iter1, Logged);
									if(!(CurrentHash.contains(iter1))){
										//continue;
										CurrentHash.add(iter1);
									}
									
									if(UseHash.contains(iter1)){
										continue;
									}
									else{
										UseHash.add(iter1);
									}
									
									Logged=0;	
								}	
								else {
									//if(flag.get(iter1) == 0){ //System.out.println(iter1 + " Inside3");
										if(CurrentHash.contains(iter1)){
											continue;
										}
										HashMap<Integer,Double> innerHashMap = LoggedHash.get(mapKey);
										if(innerHashMap == null){
											innerHashMap = new HashMap<Integer,Double>();
											LoggedHash.put(mapKey, innerHashMap);
										}
										else{
											LoggedHash.put(mapKey, innerHashMap);	
										}
										innerHashMap.put(iter1, HalfLogged);
									//}
								}
							}
							Offset++;
						}
					}
				}
			}	
			for (Map.Entry<String , HashMap<Integer , Double>> entry : LoggedHash.entrySet()) {
				String Hashquery = entry.getKey();
				HashMap<Integer,Double> innerHashMap = LoggedHash.get(Hashquery);
				//innerHashMap = new HashMap<Integer,Double>();
				for (Map.Entry<Integer , Double> entry1 : innerHashMap.entrySet()) {
					int DOCID = entry1.getKey();
					double value = entry1.getValue();
					//System.out.println(Hashquery + " "+DOCID + " "+value);
					if(UseHash.contains(DOCID)){
						if(FinalHash.containsKey(DOCID)){
							double doc = FinalHash.get(DOCID);
							FinalHash.put(DOCID, doc + value);
						}
						else{
							FinalHash.put(DOCID, value);
						}
					}
				}
			}
			for (Map.Entry<Integer, Double> entry : FinalHash.entrySet()) {
				int Hashquery = entry.getKey();
				Double hashValue = entry.getValue();
				//System.out.println(Hashquery + " " + hashValue);
				TreeHash.put(hashValue,Hashquery);
			}
			Double keyValue=(double) 0;
			for(int index5=0;index5<5;index5++){
				file1.println(query1);
				file1.println(Head.get(TreeHash.get(TreeHash.lastKey())-1));
				file1.println(Path.get(TreeHash.get(TreeHash.lastKey())-1));
				file1.println("Computed probablity: -" + TreeHash.lastKey());
				file1.println(snippet.get(TreeHash.get(TreeHash.lastKey())-1) + "\n");
				TreeHash.remove(TreeHash.lastKey());
				
			}	
		}
		file1.close();
		outputFile1.close();
	}
}
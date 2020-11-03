//Tasks that need to be done

//Implement generate_pool() (hard)
//Implement makeTestcaseObjecdt(Sring) (medium)
//Implement getTestCase() (easy)
//Implement generate_S_Candidate(int[], GREPInput); (easy)

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.lang.Integer;

class ART {
	private int categoriesChoices;
	private int candidatesCount;
	private String grepV1;
	private String grepV2;
	private String filePath;
	private GREPInput[] candidates;

	public ART() {
		try {
			Properties artProperties = new Properties();
			FileInputStream propertiesFile = new FileInputStream("runtime.properties");
			artProperties.load(propertiesFile);
			// this value may change if we remove more categories due to the Perl issue
			categoriesChoices = Integer.parseInt(artProperties.getProperty("categoriesChoices", "57"));
			candidatesCount = Integer.parseInt(artProperties.getProperty("candidatesCount", "10"));
			grepV1 = artProperties.getProperty("grepV1", "grepV1"); // this should be user specified
			grepV2 = artProperties.getProperty("grepV2", "grepV2"); // so should this
			// so should this. You will need to edit this for testing depending where you
			// test file or folders are
			filePath = artProperties.getProperty("filePath", "test_file.txt");
			candidates = new GREPInput[candidatesCount];
			propertiesFile.close();
		} catch (Exception e) {
			System.out.println("ERROR: Failed to initialize the program");
			System.out.println("Details: " + e.getMessage());
			System.err.println("Stacktrace:");
			e.printStackTrace();
		}
	}

	private void generateCandiates() {
		for (GREPInput i : candidates)
			i = makeTestCase(getTestCase());
	}

	private GREPInput makeTestCase(String input) {
		/*
		 * turns a testcase from a string to an object e.g. if the test case string was
		 * "A" then the object would be-- GREPInput input; input.normalCar = 0;... ..and
		 * the rest of the memebers for 'input' object would be their default values
		 */
		return new GREPInput();
	}

	private static ArrayList<String> generatePool() {
		 //category -> choices -> expression
		 String[] normalChar = {"normalAlNum","normalPunct"}; // literal chars    i
		 String[] wordSymbol = {"yesWord","noWord"}; // \w \W        i
		 String[] spaceSymbol = {"yesSpace","noSpace"}; // \s \S     i
		 String[] namedSymbol = {"alpha","upper","lower","digit","xdigit","space","punct","alnum","print","graph","cntrl","blank"}; //   i
		 String[] anyChar = {"dot"}; // "."      i
		 String[] range = {"numRange","upcaseRange","lowcaseRange"}; // [0-9] [A-Z] [a-z]    i
		 String[] bracket = {"normalBracket","caretBracket"}; // [] [^]      d
		 String[] iteration = {"qmark","star","plus","repMinMax"}; // "?" "*" "+" {1,2}      d
		 String[] parentheses = {"normParen","backref"}; // () what is backref?      d
		 String[] line = {"begLine","endLine","begEndLine"}; // presence of patterns at beg,end and beg end line     d
		 String[] word = {"begWord","endWord","begEndWord"}; // presence of patterns at beg,end and beg end word     d
		 String[] edge = {"yesEdgeBeg","yesEdgeEnd","yesEdgeBegEnd","noEdgeBeg","noEdgeEnd","noEdgeBegEnd"}; //      d
		 String[] combine = {"concatenation","alternative"}; //      d
 
		 //categories
		 String[][] categories = {normalChar, wordSymbol, spaceSymbol, namedSymbol, anyChar, range, bracket, iteration, parentheses, line, word, edge, combine};
		 String[][] independentCat = {normalChar, wordSymbol, spaceSymbol, namedSymbol, anyChar, range};
		 String[][] dependentCat = {bracket, iteration, parentheses, line, word, edge, combine};
 
 
		 ArrayList<String> grepInputPool = new ArrayList<>();
		 for (String[] category : independentCat) {
			 for (String choice : category) {
				//  System.out.println(choice + ": ");
 
				 switch (choice) {
 
					 case "normalAlNum":
						 char[] alphaNum = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
						 for (char c : alphaNum) {
							 grepInputPool.add(String.valueOf(c));
						 }
						 break;
 
					 case "normalPunct":
						 char[] punct = ",`~;:'&%$#@!".toCharArray();
						 for (char p : punct) {
							 grepInputPool.add(String.valueOf(p));
						 }
						 break;
 
					 case "yesWord":
						 grepInputPool.add("\\w");
						 break;
 
					 case "noWord":
						 grepInputPool.add("\\W");
						 break;
 
					 case "yesSpace":
						 grepInputPool.add("\\s");
						 break;
 
					 case "noSpace":
						 grepInputPool.add("\\S");
						 break;
 
					 case "alpha":
						 grepInputPool.add("[[:alpha:]]");
						 break;
 
					 case "upper":
						 grepInputPool.add("[[:upper:]]");
						 break;
 
					 case "lower":
						 grepInputPool.add("[[:lower:]]");
						 break;
 
					 case "digit":
						 grepInputPool.add("[[:digit:]]");
						 break;
 
					 case "xdigit":
						 grepInputPool.add("[[:xdigit:]]");
						 break;
 
					 case "space":
						 grepInputPool.add("[[:space:]]");
						 break;
 
					 case "punct":
						 grepInputPool.add("[[:punct:]]");
						 break;
 
					 case "alnum":
						 grepInputPool.add("[[:alnum:]]");
						 break;
 
					 case "print":
						 grepInputPool.add("[[:print:]]");
						 break;
 
					 case "graph":
						 grepInputPool.add("[[:graph:]]");
						 break;
 
					 case "cntrl":
						 grepInputPool.add("[[:cntrl:]]");
						 break;
 
					 case "blank":
						 grepInputPool.add("[[:blank:]]");
						 break;
				 }

			 }
		 }

		 ArrayList<String> dependentPool = new ArrayList<>();
		 for (String[] category : categories) {
			 for (String choice : category) {

				switch (choice) {
 
					 case "normalBracket":
						 char[] alphaNum = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
						 for (char c : alphaNum) {
							dependentPool.add("["+ c +"]");
						 }
						 break;
					// Range will expand on each iteration. [A-B]*, [A-C]* ... etc
					 case "star":
						 char[] upcaseRange = "BCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
						 for (char endRange : upcaseRange) {
							dependentPool.add("["+"A"+"-"+endRange+"]*");
						 }
						 break;
 
					//  case "normParen":
					//  	 dependantPool.add("(A)"+"\\d+)"); // Leaving this one out for now as it used YesDigit and that is only supported by Perl.
					// 	 break;
 
					 case "begLine":
					 	dependentPool.add("^\\w"); 
						 break;
 
					//  case "endWord":
					//  	dependantPool.add("\\d\>"); // This relies on DigitSymbol too
					// 	 break;
 
					 case "yesEdgeBegEnd":
					 	dependentPool.add("\\b" + "[1-9]" + "\\b");
						 break;
 
					 case "concatenation":
						 char[] concatRange = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
						 for (char letter: concatRange) {
							dependentPool.add("(" + letter + "[0-9])+");
						 }
					 	 break;
 
				}

			}
		}

		// Grep doesn't recognise the input for their Combination of Categories "Combine; Iteration; Parentheses; NormalChar; Range" with their test case being (A[0-9])+    ?????

		// Combine the lists
		grepInputPool.addAll(dependentPool);

		
		return grepInputPool;
	}
 

	private String getTestCase() {
		String test_case = "";
		// test_case = get a test case from the pool as a string
		return test_case;
	}

	private void generate_S_Array(int[] S_candidate, GREPInput candidate) {
		// populate the S_candidate array with 0's and 1's according to the candidates
		// values
	}

	private int calculate_sum_distance(int n, int[] S, int[] S_candidate) {
		int sum = 0;
		for (int i = 0; i < categoriesChoices; i++) {
			if (S_candidate[i] == 1)
				sum += (n - S[i]);
		}
		return sum;
	}

	private int runART() {
		int[] S = new int[categoriesChoices];
		for (int i : S)
			i = 0;

		ArrayList<GREPInput> E = new ArrayList<GREPInput>();

		GREPInput test_case;

		int[] S_test_case = new int[categoriesChoices];

		boolean break_loop = false;

		int n = 0;

		while (!break_loop) {
			n++;
			if (n == 1)
				test_case = makeTestCase(getTestCase());
			else {
				generateCandiates();

				int max_sum = -1; /* initialize outside loop? */
				int current_sum = -1;
				GREPInput candidate;
				int[] S_candidate = new int[categoriesChoices];
				int max_candidate_index = -1;

				for (int i = 0; i < candidatesCount; i++) {
					candidate = candidates[i];
					generate_S_Array(S_candidate, candidate);
					current_sum = calculate_sum_distance(n - 1, S, S_candidate);

					if (current_sum > max_sum) {
						max_sum = current_sum;
						max_candidate_index = i;
					}
				}
				test_case = candidates[max_candidate_index];

				// will need to return test case as a string here some how
				// Some stuff will need to change here
				// commented to avoid some errors :)
				// also looks ugly because code formatter fucked it XD
				/*
				 * String test_case_string = ""; try { if
				 * (!feedInputGrepv1(test_case_string).equals(feedInputGrepv2(test_case_string))
				 * ) break_loop = true; } catch (IOException e) { System.out.println(e); }
				 */
			}
			E.add(test_case);
			generate_S_Array(S_test_case, test_case);
			for (int i = 0; i < categoriesChoices; i++) {
				if (S_test_case[i] == 1)
					S[i]++;
			}
		}
		return n;
	}

	// IMORTANT Do not try and run ART() right now. It has not been finished
	// Main is runnable now for demo :)

	private static void generateGrepCommand(ArrayList<String> grepPool) throws IOException {
		// this is placeholder code to demonstrate how this sections works
		// these will eventually be provided as input arguments
		String localGrepFolderDir = "/mnt/c/Users/Ben/Desktop/grep/";
		String grepOracle = "grep_oracle";
		String grepBad = "grep_bad";
		String fileToTest = "/mnt/c/Users/Ben/Desktop/grep/SlyFox.txt"; // Put path to your testing file or directory. the * just means all files within directory.

		int oracleErrorCounter = 0;
		int oracleSuccessCounter = 0;
		int oldErrorCounter = 0;
		int oldSuccessCounter = 0;

		for(String item : grepPool)
		{
			// For the Grep Oracle
			String[] oracleCommands = { "wsl", "\""+localGrepFolderDir + grepOracle+"\"", "\""+item+"\"", "\""+fileToTest+"\""};
			Process oracleProc = Runtime.getRuntime().exec(oracleCommands);

			BufferedReader oracleStdIn = new BufferedReader(new InputStreamReader(oracleProc.getInputStream()));

			BufferedReader oracleStdErr = new BufferedReader(new InputStreamReader(oracleProc.getErrorStream()));

			// Read the output from the command
			//System.out.println("*****Standard Output*****\n");
			String oracleS = null;
			String oracleStdOutput="";
			while ((oracleS = oracleStdIn.readLine()) != null) {
				oracleStdOutput=oracleStdOutput+oracleS;
				//System.out.println(s);
				
			}
			if(oracleStdOutput.length()>0) {
				oracleSuccessCounter++;
			}

			// Read any errors from the attempted command
			//System.out.println("*****Standard Error*****\n");
			String oracleErrOutput="";
			while ((oracleS = oracleStdErr.readLine()) != null) {
				oracleErrOutput=oracleErrOutput+oracleS;
				//System.out.println(oracleErrOutput);
				
			}
			if(oracleErrOutput.length()>0) {
				oracleErrorCounter++;
			}

			// For the old Grep 
			String[] oldCommands = { "wsl", "\""+localGrepFolderDir + grepBad+"\"", "\""+item+"\"", "\""+fileToTest+"\""};
			Process proc = Runtime.getRuntime().exec(oldCommands);

			BufferedReader oldStdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader oldStdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			// Read the output from the command
			//System.out.println("*****Standard Output*****\n");
			String oldS = null;
			String oldStdOutput="";
			while ((oldS = oldStdIn.readLine()) != null) {
				oldStdOutput=oldStdOutput+oldS;
				//System.out.println(s);
				
			}
			if(oldStdOutput.length()>0) {
				oldSuccessCounter++;
			}

			// Read any errors from the attempted command
			//System.out.println("*****Standard Error*****\n");
			String oldErrOutput="";
			while ((oldS = oldStdErr.readLine()) != null) {
				oldErrOutput=oldErrOutput+oldS;
				//System.out.println(oldErrOutput);
			}
			if(oldErrOutput.length()>0) {
				oldErrorCounter++;
			}
			
			
			if(oracleStdOutput.compareTo(oldStdOutput)!=0) {
				System.out.println("*****oracleStdOutput*****\n"+oracleStdOutput);
				
				System.out.println("*****oldStdOutput*****\n"+oldStdOutput);
			}
			if(oracleErrOutput.compareTo(oldErrOutput)!=0) {
				System.out.println("*****oracleErrOutput*****\n"+oracleErrOutput);
				
				System.out.println("*****oldErrOutput*****\n"+oldErrOutput);
			}

		}
		
		System.out.println("ORACLE RESULTS:");
		System.out.println("GrepPool Size: " + grepPool.size());
		System.out.println("Total # Errors: " + oracleErrorCounter);
		System.out.println("Total # Success: " + oracleSuccessCounter);

		System.out.println("OLD RESULTS:");
		System.out.println("GrepPool Size: " + grepPool.size());
		System.out.println("Total # Errors: " + oldErrorCounter);
		System.out.println("Total # Success: " + oldSuccessCounter);
	}


	// we can deal with error handling later
	// for now main throws the exceptions and dies if something is wrong
	public static void main(String args[]) throws IOException {

		// Creating the pool of commands
		ArrayList<String> grepPool = generatePool();

		// Using windows WSL
		System.out.println("ORACLE");
		generateGrepCommand(grepPool);


		// keeping this for later use
		/*
		 * ART artInstance = new ART(); String test_case = "crinkled";
		 * 
		 * try {
		 * 
		 * String output_v1 = artInstance.feedInputGrepv1(test_case); String output_v2 =
		 * artInstance.feedInputGrepv2(test_case); System.out.println(output_v1);
		 * System.out.println(output_v2);
		 * 
		 * if (output_v1.equals(output_v2)) { System.out.println("They are equal"); }
		 * 
		 * } catch (IOException e) { System.out.println(e); }
		 */

	}
}

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
		 String[] normalChar = {"","a","?"}; // literal chars    i
		 String[] wordSymbol = {"","\\w","\\W"}; // \w \W        i
		 String[] spaceSymbol = {"","\\s","\\S"}; // \s \S     i
		 String[] namedSymbol = {"","[[:alpha:]]","[[:upper:]]","[[:lower:]]","[[:digit:]]","[[:xdigit:]]","[[:space:]]","[[:punct:]]","[[:alnum:]]","[[:print:]]","[[:graph:]]","[[:cntrl:]]","[[:blank:]]"}; //   i
		 String[] anyChar = {"", "."}; // "."      i
		 String[] range = {"", "[0-9]","[A-P]","[A-P]"}; // [0-9] [A-Z] [a-z]    i

		//Should be 2808 Test Cases

		//categories
		String[][] independentCategories = {normalChar, wordSymbol, spaceSymbol, namedSymbol, anyChar, range}; 
		ArrayList<String> grepInputPool = new ArrayList<>();

		int cnt = 0;

		 for (int i = 0 ; i != independentCategories[0].length ; i++) {
			for (int j = 0 ; j != independentCategories[1].length ; j++) {
				for (int k = 0 ; k != independentCategories[2].length ; k++) {
					for (int l = 0 ; l != independentCategories[3].length ; l++) {
						for (int m = 0 ; m != independentCategories[4].length ; m++) {
							for (int n = 0 ; n != independentCategories[5].length ; n++) {
								grepInputPool.add(independentCategories[0][i]+independentCategories[1][j]+independentCategories[2][k]+independentCategories[3][l]+independentCategories[4][m]+independentCategories[5][n]);
								cnt++;
							}
						}
					}
				}
			}
		}

		System.out.println(cnt);		
		return grepInputPool;
	}
 

	private String getTestCase() {
		String test_case = "";
		// test_case = get a test case from the pool as a string
		return test_case;
	}

	private static int[] returnS(String test_case){
        int S[] = new int[28];
        Arrays.fill(S, 0);

        if(test_case.contains("z")) S[1] = 1;
        else if(test_case.contains("?")) S[2] = 1;
        else S[0] = 1;

        if(test_case.contains("\\w")) S[4] = 1;
        else if(test_case.contains("\\W")) S[5] = 1;
        else S[3] = 1;

        if(test_case.contains("\\s")) S[7] = 1;
        else if(test_case.contains("\\S")) S[8] = 1;
        else S[6] = 1;

        if(test_case.contains("[:alpha:]")) S[10] = 1;
        else if(test_case.contains("[:upper:]")) S[11] = 1;
        else if(test_case.contains("[:lower:]")) S[12] = 1;
        else if(test_case.contains("[:digit:]")) S[13] = 1;
        else if(test_case.contains("[:xdigit:]")) S[14] = 1;
        else if(test_case.contains("[:space:]")) S[15] = 1;
        else if(test_case.contains("[:punct:]")) S[16] = 1;
        else if(test_case.contains("[:alnum:]")) S[17] = 1;
        else if(test_case.contains("[:print:]")) S[18] = 1;
        else if(test_case.contains("[:graph:]")) S[19] = 1;
        else if(test_case.contains("[:cntrl:]")) S[20] = 1;
        else if(test_case.contains("[:blank:]")) S[21] = 1;
        else S[9] = 1;

        if(test_case.contains(".")) S[23] = 1;
        else S[22] = 1;

        if(test_case.contains("[0-9]")) S[25] = 1;
        else if(test_case.contains("[A-P]")) S[26] = 1;
        else if(test_case.contains("[a-p]")) S[27] = 1;
        else S[24] = 1; 

        return S;
    }

	private static int calculate_sum_distance(int numTestCases, int S_array[], int candidate_S[]) {
        int sum = 0;
        for(int i = 0; i < 28; i++) {
            if (candidate_S[i] == 1) sum += (numTestCases - S_array[i]);
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
		String localGrepFolderDir = "/mnt/c/Users/Dan/Desktop/grep/";
		String linuxGrepFolder = "/home/daniel/Desktop/ARTv2/";
		String grepOracle = "grep_oracle";
		String grepBad = "grep_bad";
		String fileToTest = "/mnt/c/Users/Dan/Desktop/grep/SlyFox.txt"; // Put path to your testing file or directory. the * just means all files within directory.
		String linuxFileToTest = "/home/daniel/Desktop/ARTv2/SlyFox.txt"; 
	
		int oracleErrorCounter = 0;
		int oracleSuccessCounter = 0;
		int oldErrorCounter = 0;
		int oldSuccessCounter = 0;

		String os = System.getProperty("os.name"); 
		if (os.contains("nux")) {

			// If it doesn't work run this in terminal. It's a permission denied issue. chmod u=rwx,g=r,o=r /home/daniel/Desktop/ARTv2/grep_oracle 

			System.out.println("LINUX");

			for(String item : grepPool)
			{	
				// For the Grep Oracle
				String[] oracleCommands = {linuxGrepFolder + grepOracle, item, linuxFileToTest}; //Input needs to be different from windows WSL, Otherwise can't locate files.

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
				String[] oldCommands = {linuxGrepFolder + grepBad, item, linuxFileToTest};
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
					//System.out.println("*****oracleStdOutput*****\n"+oracleStdOutput);
					
					//System.out.println("*****oldStdOutput*****\n"+oldStdOutput);
				}
				if(oracleErrOutput.compareTo(oldErrOutput)!=0) {
					//System.out.println("*****oracleErrOutput*****\n"+oracleErrOutput);
					
					//System.out.println("*****oldErrOutput*****\n"+oldErrOutput);
				}

			}

		} else { // If windows

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

		//generateGrepCommand(grepPool);


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

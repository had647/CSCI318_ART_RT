//Tasks that need to be done
//GUI

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.lang.Integer;
import java.util.Random;


class ART {
	//these need to be user defined in the GUI...
	private static int categoriesChoices = 28;   //...except this. DONT CHANGE THIS VALUE
	private static int candidatesCount = 10;
	private static String grepV1 = "grep_bad";
	private static String grepV2 = "grep_oracle";
	private static String filePath = "/home/isaac/Desktop/ARTv2/SlyFox.txt";
	private static ArrayList<String> candidates;
	private static ArrayList<String> grepPool;
	private static int max_runs = 1000;

    /*
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
	}*/


    private static String feedInputGrepv1(String test_case) throws IOException{
        Process process = Runtime.getRuntime().exec("./" + grepV1 + " " + test_case + " " + filePath);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        String output = "";
        while ((line = reader.readLine()) != null) output += line;
        reader.close();
        return output;
    }

    private static String feedInputGrepv2(String test_case) throws IOException{
        Process process = Runtime.getRuntime().exec("./" + grepV2 + " " + test_case + " " + filePath);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        String output = "";
        while ((line = reader.readLine()) != null) output += line;
        reader.close();
        return output;
    }













	private static ArrayList<String> generatePool() {
		 //category -> choices -> expression
		 String[] normalChar = {"","z","?"}; // literal chars    i
		 String[] wordSymbol = {"","\\w","\\W"}; // \w \W        i
		 String[] spaceSymbol = {"","\\s","\\S"}; // \s \S     i
		 String[] namedSymbol = {"","[[:alpha:]]","[[:upper:]]","[[:lower:]]","[[:digit:]]","[[:xdigit:]]","[[:space:]]","[[:punct:]]","[[:alnum:]]","[[:print:]]","[[:graph:]]","[[:cntrl:]]","[[:blank:]]"}; //   i
		 String[] anyChar = {"", "."}; // "."      i
		 String[] range = {"", "[0-9]","[A-P]","[a-p]"}; // [0-9] [A-Z] [a-z]    i

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
	
		return grepInputPool;
	}
 

	private static int[] returnS(String test_case){
        int S[] = new int[categoriesChoices];
        Arrays.fill(S, 0);

        if(test_case.contains("u")) S[1] = 1;
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
        for(int i = 0; i < categoriesChoices; i++) {
            if (candidate_S[i] == 1) sum += (numTestCases - S_array[i]);
        }
        return sum;
    }
	
	private static String getRandomTestCase(){
		return grepPool.get(new Random().nextInt(grepPool.size()));
	}

	private static ArrayList<String> generateCandiates(){
		ArrayList<String> candidates = new ArrayList<>();
		for(int i = 0; i < candidatesCount; i++) candidates.add(getRandomTestCase());
		return candidates;
	}








	
	
	private static void runART() {
		int[] S = new int[categoriesChoices];
		Arrays.fill(S, 0);

		String test_case;
		String candidate;

		int[] S_test_case;
		int[] S_candidate;

		int max_sum;
		int current_sum;
		int max_candidate_index = -1;

		String grepv1output = "";
		String grepv2output = "";

		for(int j = 0; j < max_runs; j++){
			if(j == 0) test_case = getRandomTestCase();
			else{
				candidates = generateCandiates();
				max_sum = -1; 
				
				for (int i = 0; i < candidatesCount; i++) {
					candidate = candidates.get(i);
					S_candidate = returnS(candidate);
	
					current_sum = calculate_sum_distance(j, S, S_candidate);

					if(current_sum > max_sum) {
						max_sum = current_sum;
						max_candidate_index = i;
					}
				}
				test_case = candidates.get(max_candidate_index);

				try{
					grepv1output = feedInputGrepv1(test_case);
					grepv2output = feedInputGrepv2(test_case);
				}
				catch(Exception e){
					System.out.println("error feeding input to grep");
					break;
				}
			
				if(!grepv1output.equals(grepv2output)){
					System.out.println("outputs do not match. Bug found.");
					System.out.println(test_case);
					System.out.println(j);
					break;
				}	
			}

			S_test_case = returnS(test_case);
			for (int i = 0; i < categoriesChoices; i++) {
				if(S_test_case[i] == 1) S[i]++;
			}
		}
		

	}
	
	public static void runRT(){
		String test_case = "";
		String grepv1output = "";
		String grepv2output = "";
		
		for(int i = 0; i < max_runs; i++){
			test_case = getRandomTestCase();
			try{
				grepv1output = feedInputGrepv1(test_case);
				grepv2output = feedInputGrepv2(test_case);
			}
			catch(Exception e){
				System.out.println("error feeding input to grep");
				break;
			}
			if(!grepv1output.equals(grepv2output)){
				System.out.println("outputs do not match. Bug found.");
				System.out.println(test_case);
				System.out.println(i);
				break;
			}	
		}
	}

	
	


    /*
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
*/

	// we can deal with error handling later
	// for now main throws the exceptions and dies if something is wrong
	public static void main(String args[]) throws IOException {

		// Creating the pool of commands
		/*grepPool = generatePool();

		//generatePool currently produces an empty string as input which grep doesnt like.. so manually removing it here.
		grepPool.remove(0);
	
		System.out.println("ART");
		runART();
		System.out.println("RT");
		runRT();*/
		
		/*EVERYTHING IS TEMPORARILY COMMENTED OUT TO MAKE GUI TESTING EASIER*/
		/*GUI DOES NOT WORK YET - JUST MAKING IT LOOK OK*/
		
		new GUIWindow();
		
		
		
		

		/*
		Notes
			Some possible things to add to GUI.
				Let user choose file path to grepv1 and grepv2
				Let user choose how many candiates are generated
				Let user define max runs
				Let user define the names of the grep exe files
				Let user define name and file path of testing file/directory
				Let user do an RT comparison	
		 */









		
		
		

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

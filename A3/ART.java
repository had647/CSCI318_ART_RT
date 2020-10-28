//Tasks that need to be done

//Implement generate_pool() (hard)
//Implement makeTestcaseObjecdt(Sring) (medium)
//Implement getTestCase() (easy)
//Implement generate_S_Candidate(int[], GREPInput); (easy)

import java.io.*;
import java.util.ArrayList;
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

	private void generatePool() {
		// do stuff
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

	private static void generateGrepCommand() throws IOException {
		// this is placeholder code to demonstrate how this sections works
		// these will eventually be provided as input arguments
		String localGrepFolderDir = "/mnt/c/Users/Ben/Desktop/grep/";
		String grepVersionToTest = "grep_oracle";

		// outputs version info to test if its working
		String[] commands = { "wsl", localGrepFolderDir + grepVersionToTest, "-V" };
		Process proc = Runtime.getRuntime().exec(commands);

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		// Read the output from the command
		System.out.println("*****Standard Output*****\n");
		String s = null;
		while ((s = stdIn.readLine()) != null) {
			System.out.println(s);
		}

		// Read any errors from the attempted command
		System.out.println("*****Standard Error*****\n");
		while ((s = stdErr.readLine()) != null) {
			System.out.println(s);
		}
	}

	// we can deal with error handling later
	// for now main throws the exceptions and dies if something is wrong
	public static void main(String args[]) throws IOException {

		// demo run for wsl
		generateGrepCommand();

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

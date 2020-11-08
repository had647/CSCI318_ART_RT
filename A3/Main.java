import java.io.IOException;

class Main {
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

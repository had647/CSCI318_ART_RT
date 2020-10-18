//Tasks that need to be done

//Implement generate_pool() (hard)
//Implement makeTestcaseObjecdt(Sring) (medium)
//Implement getTestCase() (easy)
//Implement generate_S_Candidate(int[], GREPInput); (easy)
//Work out how to call ./grep within program (easy)
//Work out how to read ./grep output within program (easy-medium) -- implement getTestOutput(), getOracleOutput






import java.io.*;
import java.util.ArrayList;

class ART{ 
    static int num_cat_choice = 57; //this value may change if we remove more categories due to the Perl issue
    static int num_of_candidates = 10;

    static GREPInput[] candidates = new GREPInput[num_of_candidates];

    static void generateCandiates(){
        for(GREPInput i: candidates)i = makeTestcaseObject(getTestCase());
    }

    static GREPInput makeTestcaseObject(String input){                          
        GREPInput input_as_object = new GREPInput();
        /*turns a testcase from a string to an object    
          e.g. if the test case string was "A" then the object would be-- GREPInput input; input.normalCar = 0;... 
          ..and the rest of the memebers for 'input' object would be their default values*/
        return input_as_object;
    }

    static void generate_pool(){
        //do stuff
    }

    static String getTestCase(){
        String test_case = "";
        //test_case = get a test case from the pool as a string
        return test_case;
    }

    static void generate_S_Array(int[] S_candidate, GREPInput candidate){
        //populate the S_candidate array with 0's and 1's according to the candidates values
    }
    
    static int calculate_sum_distance(int n, int[] S, int[] S_candidate){
        int sum = 0;
        for(int i = 0; i < num_cat_choice; i++){
            if(S_candidate[i] == 1) sum += (n - S[i]);
        }
        return sum;
    }


    static Process feedInput(String grepVersion, String test_case, String file_path) throws IOException{
        Process process = Runtime.getRuntime().exec("./" + grepVersion + " " + test_case + " " + file_path);
        return process;
    }

    
    static String returnOutputString(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        String output = "";
        while ((line = reader.readLine()) != null) output += line;

        reader.close();
        return output;
    }








    static int ART(){
        int[] S = new int[num_cat_choice]; 
        for(int i : S) i = 0;

        ArrayList<GREPInput> E = new ArrayList<GREPInput>();

        GREPInput test_case;
        
        int[] S_test_case = new int[num_cat_choice];

        boolean break_loop = false;

        int n = 0;

        while(!break_loop){
            n++;
            if(n == 1) test_case = makeTestcaseObject(getTestCase());
            else{
                generateCandiates();

                int max_sum = -1;               /*initialize outside loop? */
                int current_sum = -1;
                GREPInput candidate;
                int[] S_candidate = new int[num_cat_choice];
                int max_candidate_index = -1;
                

                for(int i = 0; i < num_of_candidates; i++){
                    candidate = candidates[i];
                    generate_S_Array(S_candidate, candidate);
                    current_sum = calculate_sum_distance(n - 1, S, S_candidate);

                    if(current_sum > max_sum){
                        max_sum = current_sum;
                        max_candidate_index = i;
                    }
                }
                test_case = candidates[max_candidate_index];
               // if(!check_outputs_match(getOracleOutput(), getTestOutput())) break_loop = true;       need to change this condition (Isaac)
            }
            E.add(test_case);
            generate_S_Array(S_test_case, test_case);
            for(int i = 0; i < num_cat_choice; i++){
                if(S_test_case[i] == 1) S[i]++;
            }
        }
        return n;
    }








    //IMORTANT Do not try and run ART() right now. It has not been finished

    public static void main(String args[]){ 
        String file_path = "your/file/path.txt";
        String grepv1 = "grepv1";
        String test_case = "a test case";

        try{
            Process p1 = feedInput(grepv1, test_case, file_path);    //still testing here (Isaac)
            String output_v1 = returnOutputString(p1);
        }
        catch(IOException e){ System.out.println(e); }
    

    } 
}











//this will need to be rep
class GREPInput{
    
    enum NamedSymbol {
        novalue,
        ALPHA,
        UPPER,
        LOWER,
        DIGIT,
        XDIGIT,
        SPACE,
        PUNCT,
        ALNUM,
        PRINT,
        GRAPH,
        CNTRL,
        BLANK
    }

    enum Range{
        novalue,
        NumRange,
        UpcaseRange,
        LowcaseRange
    }

    enum Iteration{
        novalue,
        Qmark,
        Star,
        Plus,
        Repminmax
    }

    enum Line{
        novalue,
        BegLine,
        EndLine,
        BegEndLine
    }

    enum Word{
        novalue,
        BegWord,
        EndWord,
        BegEndWord
    }

    enum Edge{
        novalue,
        YesEdgeBeg,
        YesEdgeEnd,
        YesEdgeBegEnd,
        NoEdgeBeg,
        NoEdgeEnd,
        NoEdgeBegEnd
    }



    public int normalChar = 0;  //-1 is uninit, 0 is first choice, 1 is second choice
    public int wordSymbol = -1;  
    //public boolean DigitSymbol;   //use this if we can get Perl compatable grep
    public int spaceSymbol = -1;
    public NamedSymbol namedSymbol = NamedSymbol.novalue;
    public boolean AnyChar = false; //false is uninit
    public Range range = Range.novalue;
    public int bracket = -1;
    public Iteration iteration = Iteration.novalue;
    public int parentheses = -1;
    public Line line = Line.novalue;
    public Word word = Word.novalue;
    public Edge edge = Edge.novalue;
    public int combine = -1;
}
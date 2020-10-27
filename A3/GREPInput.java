public class GREPInput{
    
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
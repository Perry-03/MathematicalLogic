package mathematicallogic.parser;

import mathematicallogic.formula.Formula;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class FormulaParser {
    public static Formula parse(String input) {
        // creo il lexer
        LogicLexer lexer = new LogicLexer(CharStreams.fromString(input)) ;
        //  creo il token stream
        CommonTokenStream tokens = new CommonTokenStream(lexer) ;
        // creo il parser
        LogicParser parser = new LogicParser(tokens) ;
        // parse
        LogicParser.FormulaContext tree = parser.formula() ;
        FormulaBuilder builder = new FormulaBuilder() ;
        // visito
        return builder.visit(tree) ;
    }
}

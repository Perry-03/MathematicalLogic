package mathematicallogic;
import mathematicallogic.bdd.BDDNode;
import mathematicallogic.builder.BDDFactory;
import mathematicallogic.formula.Formula;
import mathematicallogic.formula.Var;
import mathematicallogic.parser.FormulaParser;
import mathematicallogic.util.Benchmark;
import mathematicallogic.util.Utils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String... args) throws FileNotFoundException {
        System.out.println("=== WARMUP ===") ;
        for (int i = 0; i < 5; i++) {
            Benchmark.warmup() ;
            BDDFactory.clear_cache() ;
        }

        InputStream is ;
        File formulas = new File("formulas.txt") ;
        if (formulas.exists())
            is = new FileInputStream(formulas) ;
        else
            is = Main.class.getResourceAsStream("formulas.txt") ;

        List<String> lines = new BufferedReader(new InputStreamReader(is))
                .lines()
                .toList() ;

        System.out.println("=== INPUT FORMULAS BENCHMARK ===\n") ;
        for (int i = 0; i < lines.size(); i++) {
            String name =  lines.get(i) ;
            Formula formula = FormulaParser.parse(name) ;
            Benchmark.benchmark(
                    name,
                    formula,
                    "input" + i,
                    true
            );
        }

        // formule generate random + test scalabilità pesante
        Benchmark.auto_generate() ;

        // export to .dot
        String graphs_path = "generated/graphs" ;
        Benchmark.export(graphs_path) ;

    }


}

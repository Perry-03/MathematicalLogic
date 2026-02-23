package mathematicallogic;
import mathematicallogic.builder.BDDFactory;
import mathematicallogic.formula.Formula;
import mathematicallogic.parser.FormulaParser;
import mathematicallogic.util.Benchmark;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String... args) throws FileNotFoundException {
        boolean runBenchmarks = true ;
        boolean runScalability = true ;

        for (String arg : args) {
            if ("--no-benchmark".equals(arg)) runBenchmarks = false ;
            if ("--no-scalability".equals(arg)) runScalability = false ;
        }

        for (int i = 0; i < 5; i++) {
            Benchmark.warmup() ;
            BDDFactory.clear_cache() ;
        }

        InputStream is ;
        File formulas = new File("formulas.txt") ;
        if (formulas.exists())
            is = new FileInputStream(formulas) ;
        else
            is = Main.class.getResourceAsStream("/formulas.txt") ;

        List<String> lines = new BufferedReader(new InputStreamReader(is))
                .lines()
                .toList() ;

        System.out.println("=== INPUT FORMULAS BENCHMARK ===") ;
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

        System.out.println() ;

        // formule generate random + test scalabilità pesante
        if (runBenchmarks) Benchmark.standard_benchmarks() ;
        if  (runScalability) Benchmark.scalability_test() ;

        // pulisco la cartella
        String graphs_path = "generated/graphs" ;
        File graphs_dir =  new File(graphs_path) ;
        if (graphs_dir.exists())
            for (File f : Objects.requireNonNull(graphs_dir.listFiles()))
                f.delete() ;

        // export to .dot
        Benchmark.export(graphs_path) ;

    }


}

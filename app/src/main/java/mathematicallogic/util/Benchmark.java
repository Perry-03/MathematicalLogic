package mathematicallogic.util;

import mathematicallogic.bdd.BDDNode;
import mathematicallogic.builder.BDDFactory;
import mathematicallogic.formula.Formula;
import mathematicallogic.formula.Var;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class Benchmark {
    
    private static final Map<String, BDDNode> bdds = new LinkedHashMap<>() ;
    private static final Map<String, String> labels = new LinkedHashMap<>() ;

    public static void warmup() {
        Formula a = new Var("warmup_a") ;
        Formula b = new Var("warmup_b") ;
        BDDNode aBDD = BDDFactory.ast_to_bdd(a) ;
        BDDNode bBDD = BDDFactory.ast_to_bdd(b) ;
        BDDFactory.apply(aBDD, bBDD, "||") ;
    }

    public static void benchmark(String name, Formula f, String fName, boolean clear) {
        if  (clear) BDDFactory.clear_cache() ;
        long start = System.nanoTime() ;
        BDDNode bdd = BDDFactory.ast_to_bdd(f) ;
        long end = System.nanoTime() ;
        double ms = (end - start) / 1_000_000.0 ;
        System.out.printf("### %-30s LASTED: %.3f ms\n", name + ":", ms) ;
        bdds.put(fName, bdd) ;
        labels.put(fName, name) ;
    }
    
    public static void auto_generate() {
        // default benchmarks
        System.out.println("\n=== BENCHMARK ===\n") ;
        benchmark("p OR q", Benchmark::or1) ;
        benchmark("(p OR q) OR r", Benchmark::or2) ;
        benchmark("p AND q", Benchmark::and1) ;
        benchmark("de morgan", Benchmark::de_morgan) ;
        benchmark("p XOR q", Benchmark::xor1) ;
        benchmark("(p XOR q) XOR r", Benchmark::xor2) ;
        benchmark("deep nesting", Benchmark::deep_nesting) ;

        // SCALABILITY TEST con XOR
        System.out.println("\n=== SCALABILITY TEST ===\n") ;

        // ogni XOR raddoppia circa il numero di nodi
        benchmark("10 XOR vars", () -> xor_chain(10)) ;
        benchmark("20 XOR vars", () -> xor_chain(20)) ;
        benchmark("25 XOR vars", () -> xor_chain(25)) ;

        System.out.println() ;
    }

    private static void benchmark(String fName, Runnable test) {
        BDDFactory.clear_cache() ;
        long start = System.nanoTime() ;
        test.run() ;
        long end = System.nanoTime() ;
        double ms = (end - start) / 1_000_000.0 ;
        System.out.printf("### %-30s LASTED: %.3f ms\n", fName + ":", ms) ;
    }

    private static void or1() {
        Formula p = new Var("test1_p") ;
        Formula q = new Var("test1_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        BDDFactory.apply(pBDD, qBDD, "||") ;
    }

    private static void or2() {
        Formula p = new Var("test2_p") ;
        Formula q = new Var("test2_q") ;
        Formula r = new Var("test2_r") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;
        BDDNode rBDD = BDDFactory.ast_to_bdd(r) ;

        BDDFactory.apply(
                BDDFactory.apply(pBDD, qBDD, "||"),
                rBDD,
                "||"
        ) ;
    }

    private static void and1() {
        Formula p = new Var("test3_p") ;
        Formula q = new Var("test3_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        BDDFactory.apply(pBDD, qBDD, "&&") ;
    }

    private static void de_morgan() {
        Formula p = new Var("test4_p") ;
        Formula q = new Var("test4_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        BDDFactory.apply_neg(
                BDDFactory.apply(pBDD, qBDD, "&&")
        ) ;
    }

    private static void xor1() {
        Formula p = new Var("test5_p") ;
        Formula q = new Var("test5_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        BDDFactory.apply(pBDD, qBDD, "^") ;
    }

    private static void xor2() {
        Formula p = new Var("test6_p") ;
        Formula q = new Var("test6_q") ;
        Formula r = new Var("test6_r") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;
        BDDNode rBDD = BDDFactory.ast_to_bdd(r) ;

        BDDFactory.apply(
                BDDFactory.apply(pBDD, qBDD, "^"),
                rBDD,
                "^"
        ) ;
    }

    private static void deep_nesting() {
        Formula[] vars = new Formula[8];
        BDDNode[] bddVars = new BDDNode[8];

        for (int i = 0; i < 8; i++) {
            vars[i] = new Var("test7_x" + i) ;
            bddVars[i] = BDDFactory.ast_to_bdd(vars[i]) ;
        }

        BDDFactory.apply(
                BDDFactory.apply(
                        BDDFactory.apply(bddVars[0], bddVars[1], "&&"),
                        BDDFactory.apply(bddVars[2], bddVars[3], "&&"),
                        "||"
                ),
                BDDFactory.apply(
                        BDDFactory.apply(bddVars[4], bddVars[5], "||"),
                        BDDFactory.apply(bddVars[6], bddVars[7], "||"),
                        "&&"
                ),
                "^"
        ) ;
    }

    private static void xor_chain(int n) {
        Formula[] vars = new Formula[n] ;
        BDDNode[] bddVars = new BDDNode[n] ;

        for (int i = 0; i < n; i++) {
            vars[i] = new Var("chain" + n + "_x" + i) ;
            bddVars[i] = BDDFactory.ast_to_bdd(vars[i]) ;
        }

        BDDNode result = bddVars[0] ;
        for (int i = 1; i < n; i++)
            result = BDDFactory.apply(result, bddVars[i], "^") ;

    }

    public static void export(String graphs_path) {
        var check = new File(graphs_path).mkdirs() ;
        for (Map.Entry<String, BDDNode> entry : bdds.entrySet())
            Utils.export_to_dot(
                    entry.getValue(),
                    graphs_path + "/" + entry.getKey() + ".dot",
                    labels.get(entry.getKey())
            ) ;
    }

}

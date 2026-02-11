package mathematicallogic;
import mathematicallogic.bdd.BDDNode;
import mathematicallogic.builder.BDDFactory;
import mathematicallogic.formula.Formula;
import mathematicallogic.formula.Var;
import mathematicallogic.util.Utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private static final Map<String, BDDNode> bdds = new LinkedHashMap<>() ;
    
    public static void main(String... args) {
        System.out.println("=== WARMUP ===") ;
        for (int i = 0; i < 5; i++) {
            warmup() ;
            BDDFactory.clear_cache() ;
        }

        System.out.println("\n=== BENCHMARK ===\n") ;
        benchmark("p OR q", Main::or1) ;
        benchmark("(p OR q) OR r", Main::or2) ;
        benchmark("p AND q", Main::and1) ;
        benchmark("de morgan", Main::de_morgan) ;
        benchmark("p XOR q", Main::xor1) ;
        benchmark("(p XOR q) XOR r", Main::xor2) ;
        benchmark("deep nesting", Main::deep_nesting) ;

        System.out.println("\n===SCALABILITY TEST ===\n") ;

        // ogni XOR raddoppia circa il numero di nodi
        benchmark("10 XOR vars", () -> xor_chain(10)) ;
        benchmark("20 XOR vars", () -> xor_chain(20)) ;

        // export to .dot
        String graphs_path = "generated/graphs" ;
        new File(graphs_path).mkdirs() ;
        for (Map.Entry<String, BDDNode> entry : bdds.entrySet()) {
            Utils.export_to_dot(
                    entry.getValue(),
                    graphs_path + "/" + entry.getKey() + ".dot") ;
        }
    }

    private static void warmup() {
        Formula a = new Var("warmup_a") ;
        Formula b = new Var("warmup_b") ;
        BDDNode aBDD = BDDFactory.ast_to_bdd(a) ;
        BDDNode bBDD = BDDFactory.ast_to_bdd(b) ;
        BDDFactory.apply(aBDD, bBDD, "||") ;
    }

    private static void benchmark(String name, Runnable test) {
        BDDFactory.clear_cache() ;
        long start = System.nanoTime() ;
        test.run() ;
        long end = System.nanoTime() ;
        double ms = (end - start) / 1_000_000.0 ;
        System.out.printf("### %-30s LASTED: %.3f ms\n", name + ":", ms) ;
    }

    private static void or1() {
        Formula p = new Var("test1_p") ;
        Formula q = new Var("test1_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        bdds.put("p_or_q", BDDFactory.apply(pBDD, qBDD, "||")) ;
    }

    private static void or2() {
        Formula p = new Var("test2_p") ;
        Formula q = new Var("test2_q") ;
        Formula r = new Var("test2_r") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;
        BDDNode rBDD = BDDFactory.ast_to_bdd(r) ;

        bdds.put("p_or_q_or_r",
                BDDFactory.apply(
                        BDDFactory.apply(pBDD, qBDD, "||"),
                        rBDD,
                        "||")) ;
    }

    private static void and1() {
        Formula p = new Var("test3_p") ;
        Formula q = new Var("test3_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        bdds.put("p_and_q", BDDFactory.apply(pBDD, qBDD, "&&")) ;
    }

    private static void de_morgan() {
        Formula p = new Var("test4_p") ;
        Formula q = new Var("test4_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        bdds.put("not_p_and_q",
                BDDFactory.apply_neg(
                        BDDFactory.apply(pBDD, qBDD, "&&"))) ;
    }

    private static void xor1() {
        Formula p = new Var("test5_p") ;
        Formula q = new Var("test5_q") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;

        bdds.put("p_xor_q", BDDFactory.apply(pBDD, qBDD, "^")) ;
    }

    private static void xor2() {
        Formula p = new Var("test6_p") ;
        Formula q = new Var("test6_q") ;
        Formula r = new Var("test6_r") ;

        BDDNode pBDD = BDDFactory.ast_to_bdd(p) ;
        BDDNode qBDD = BDDFactory.ast_to_bdd(q) ;
        BDDNode rBDD = BDDFactory.ast_to_bdd(r) ;

        bdds.put("p_xor_q_xor_r",
                BDDFactory.apply(
                        BDDFactory.apply(pBDD, qBDD, "^"),
                        rBDD,
                        "^")) ;
    }

    private static void deep_nesting() {
        Formula[] vars = new Formula[8];
        BDDNode[] bddVars = new BDDNode[8];

        for (int i = 0; i < 8; i++) {
            vars[i] = new Var("test7_x" + i) ;
            bddVars[i] = BDDFactory.ast_to_bdd(vars[i]) ;
        }

        bdds.put("deep_nesting",
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
                )) ;
    }

    private static void xor_chain(int n) {
        Formula[] vars = new Formula[n] ;
        BDDNode[] bddVars = new BDDNode[n] ;

        for (int i = 0; i < n; i++) {
            vars[i] = new Var("chain" + n + "_x" + i) ;
            bddVars[i] = BDDFactory.ast_to_bdd(vars[i]) ;
        }

        BDDNode result = bddVars[0] ;
        for (int i = 1; i < n; i++) {
            result = BDDFactory.apply(result, bddVars[i], "^") ;
        }

        bdds.put("xor_chain_" + n, result);
    }
}

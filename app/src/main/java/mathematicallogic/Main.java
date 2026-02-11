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
    public static void main(String... args) {

        // variabili come Formula
        Formula pF = new Var("p") ;
        Formula qF = new Var("q") ;
        Formula rF = new Var("r") ;

        // trasformazione in BDD base
        BDDNode p = BDDFactory.ast_to_bdd(pF) ;
        BDDNode q = BDDFactory.ast_to_bdd(qF) ;
        BDDNode r = BDDFactory.ast_to_bdd(rF) ;

        Map<String, BDDNode> bdds = new LinkedHashMap<>() ;

        // p OR q
        bdds.put("p_or_q",
                BDDFactory.apply(p, q, "||")) ;

        // (p OR q) OR r
        bdds.put("p_or_q_or_r",
                BDDFactory.apply(
                        BDDFactory.apply(p, q, "||"),
                        r,
                        "||")) ;

        // p AND q
        bdds.put("p_and_q",
                BDDFactory.apply(p, q, "&&")) ;

        // (p AND q) AND r
        bdds.put("p_and_q_and_r",
                BDDFactory.apply(
                        BDDFactory.apply(p, q, "&&"),
                        r,
                        "&&")) ;

        // NOT(p AND q)
        bdds.put("not_p_and_q",
                BDDFactory.apply_neg(
                        BDDFactory.apply(p, q, "&&"))) ;

        // p XOR q
        bdds.put("p_xor_q",
                BDDFactory.apply(p, q, "^")) ;

        // (p XOR q) XOR r
        bdds.put("p_xor_q_xor_r",
                BDDFactory.apply(
                        BDDFactory.apply(p, q, "^"),
                        r,
                        "^")) ;

        // export
        String graphs_path = "generated/graphs" ;
        new File(graphs_path).mkdirs() ;
        for (Map.Entry<String, BDDNode> entry : bdds.entrySet()) {
            Utils.export_to_dot(
                    entry.getValue(),
                    graphs_path + "/" + entry.getKey() + ".dot") ;
        }
    }
}

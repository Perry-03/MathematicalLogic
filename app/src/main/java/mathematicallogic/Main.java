package mathematicallogic;

import mathematicallogic.bdd.BDDNode;
import mathematicallogic.builder.BDDFactory;
import mathematicallogic.formula.Formula;
import mathematicallogic.formula.Var;
import mathematicallogic.formula.Xor;
import mathematicallogic.util.Utils;

public class Main {
    public static void main(String... args) {
        // prima formula: p ⊕ q ⊕ r
        Formula p = new Var("p") ;
        Formula q = new Var("q") ;
        Formula r = new Var("r") ;
        Formula bdd_p_xor_q = new Xor(p, q) ;
        Formula bdd_p_xor_q_xor_r = new Xor(bdd_p_xor_q, r) ;

        BDDNode bdd_p = BDDFactory.ast_to_bdd(p) ;
        BDDNode bdd_q = BDDFactory.ast_to_bdd(q) ;
        BDDNode bdd_r = BDDFactory.ast_to_bdd(r) ;
        BDDNode p_xor_q = BDDFactory.ast_to_bdd(bdd_p_xor_q) ;
        BDDNode p_xor_q_xor_r = BDDFactory.ast_to_bdd(bdd_p_xor_q_xor_r) ;

        Utils.export_to_dot(bdd_p, "app/build/generated/graphs/bdd_p.dot") ;
        Utils.export_to_dot(bdd_q, "app/build/generated/graphs/bdd_q.dot") ;
        Utils.export_to_dot(bdd_r, "app/build/generated/graphs/bdd_r.dot") ;
        Utils.export_to_dot(p_xor_q, "app/build/generated/graphs/p_xor_q.dot") ;
        Utils.export_to_dot(p_xor_q_xor_r, "app/build/generated/graphs/p_xor_q_xor_r.dot") ;

    }
}

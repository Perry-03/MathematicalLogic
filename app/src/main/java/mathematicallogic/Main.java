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

        BDDNode bdd_p = BDDFactory.ast_to_bdd(p) ;
        System.out.println("### p:") ;
        Utils.print_BDD(bdd_p) ;

        BDDNode bdd_q = BDDFactory.ast_to_bdd(q) ;
        System.out.println("### q:") ;
        Utils.print_BDD(bdd_q) ;

        BDDNode bdd_r = BDDFactory.ast_to_bdd(r) ;
        System.out.println("### r:") ;
        Utils.print_BDD(bdd_r) ;

        Formula bdd_p_xor_q = new Xor(p, q) ;
        BDDNode p_xor_q = BDDFactory.ast_to_bdd(bdd_p_xor_q) ;
        System.out.println("### p ^ q:") ;
        Utils.print_BDD(p_xor_q) ;
    }
}

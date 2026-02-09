package src.main.java.mathematicallogic.builder;

import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.formula.Formula;
import src.main.java.mathematicallogic.formula.Or;
import src.main.java.mathematicallogic.formula.Var;

public class BDDFactory {

    public static BDDNode ast_to_bdd(Formula f) {
        if (f instanceof Var) {
            return new BDDNode(((Var) f).getName(), new BDDNode(false), new BDDNode(true)) ;
        } else if (f instanceof Or) {
            Or or = (Or) f ;
            Formula left = or.getLeft() ;
            Formula right = or.getRight() ;
            BDDNode low = BDDFactory.ast_to_bdd(left) ;
            BDDNode high = BDDFactory.ast_to_bdd(right) ;
            return applyOr(low, high) ;
        }
        return null ;
    }

    private static BDDNode applyOr(BDDNode low, BDDNode high) {
        return new BDDNode(false) ;
    }
}

package src.main.java.mathematicallogic.builder;

import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.formula.Formula;
import src.main.java.mathematicallogic.formula.Var;

public class BDDFactory {

    public static BDDNode ast_to_bdd(Formula f) {
        if (f instanceof Var) {
            // return new BDDNode(((Var) f).getName(), new BDDNode(false), new BDDNode(true)) ;
            return null ;
        }
        return null ;
    }
}

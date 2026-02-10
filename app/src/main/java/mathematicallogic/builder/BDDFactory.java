package mathematicallogic.builder;


import mathematicallogic.bdd.BDDNode;
import mathematicallogic.formula.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BiFunction;

public class BDDFactory {

    private static final Comparator<String> LEXICOGRAPHIC = Comparator.comparing(String::toString) ;
    private static final HashMap<String, BiFunction<Boolean, Boolean, Boolean>> BOOL_OPERATIONS = new HashMap<>() ;

    static {
        BOOL_OPERATIONS.put("&&", (a,b) -> a && b) ;
        BOOL_OPERATIONS.put("||", (a,b) -> a || b) ;
        BOOL_OPERATIONS.put("^",  (a,b) -> a ^ b)  ;
    }

    public static BDDNode ast_to_bdd(Formula f) {
        return switch (f) {
            case Var var -> new BDDNode(var.getName(), new BDDNode(false), new BDDNode(true)) ;
            case Or or   -> apply(ast_to_bdd(or.getLeft()), ast_to_bdd(or.getRight()), BOOL_OPERATIONS.get("||")) ;
            case And and -> apply(ast_to_bdd(and.getLeft()), ast_to_bdd(and.getRight()), BOOL_OPERATIONS.get("&&")) ;
            case Xor xor -> apply(ast_to_bdd(xor.getLeft()), ast_to_bdd(xor.getRight()), BOOL_OPERATIONS.get("^")) ;
            case Not not -> apply_neg(ast_to_bdd(not.getFormula())) ;
            default -> throw new UnsupportedOperationException("Not supported yet.") ;
        } ;
    }

    private static BDDNode apply(BDDNode u, BDDNode v, BiFunction<Boolean, Boolean, Boolean> func) {
        if (u.isLeaf() && v.isLeaf()) {
            return new BDDNode(
                    func.apply(u.getValue(), v.getValue())
            );
        }
        String x ;
        if (u.isLeaf()) x = v.getVar() ;
        else if (v.isLeaf()) x = u.getVar() ;
        else x = min_var(u.getVar(), v.getVar()) ;

        BDDNode u_low = cofactor_low(u, x) ;
        BDDNode u_high = cofactor_high(u, x) ;

        BDDNode v_low  = cofactor_low(v, x) ;
        BDDNode v_high = cofactor_high(v, x) ;

        BDDNode low = apply(u_low, v_low, func) ;
        BDDNode high = apply(u_high, v_high, func) ;

        if (low.equals(high)) return low ;

        return new BDDNode(x, low, high) ;
    }

    private static BDDNode apply_neg(BDDNode u) {
        if (u.isLeaf()) return new BDDNode(!u.getValue()) ;
        BDDNode low = apply_neg(u.getLow()) ;
        BDDNode high = apply_neg(u.getHigh()) ;

        return new BDDNode(u.getVar(), low, high) ;
    }

    private static BDDNode cofactor_low(BDDNode node, String x) {
        if (node.isLeaf()) return node ;
        if (node.getVar().equals(x)) return node.getLow() ;
        return node ;
    }

    private static BDDNode cofactor_high(BDDNode node, String x) {
        if (node.isLeaf()) return node ;
        if (node.getVar().equals(x)) return node.getHigh() ;
        return node ;
    }

    private static String min_var(String v1, String v2) { return LEXICOGRAPHIC.compare(v1, v2) <= 0 ? v1 : v2 ; }

}

package src.main.java.mathematicallogic.builder;

import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.formula.*;
import src.main.java.mathematicallogic.util.Utils;

import java.util.Comparator;

public class BDDFactory {

    private static Comparator<String> LEXICOGRAPHIC = Comparator.comparing(String::toString) ;

    public static BDDNode ast_to_bdd(Formula f) {
        if (f instanceof Var) {
            return new BDDNode(((Var) f).getName(), new BDDNode(false), new BDDNode(true)) ;
        } else if (f instanceof Or) {
            Or or = (Or) f ;
            Formula left = or.getLeft() ;
            Formula right = or.getRight() ;
            BDDNode low = BDDFactory.ast_to_bdd(left) ;
            BDDNode high = BDDFactory.ast_to_bdd(right) ;
            return apply_or(low, high) ;
        } else if (f instanceof Not) {
            Not not = (Not) f ;
            BDDNode v = ast_to_bdd(not.getFormula()) ;
            BDDNode res = apply_neg(v) ;
            return res ;
        } else if (f instanceof And) {
            And and = (And) f ;
            Formula left = and.getLeft() ;
            Formula right = and.getRight() ;
            BDDNode low = BDDFactory.ast_to_bdd(left) ;
            BDDNode high = BDDFactory.ast_to_bdd(right) ;
            return apply_and(low, high) ;
        } else if (f instanceof Xor) {
            Xor xor = (Xor) f ;
            Formula left = xor.getLeft() ;
            Formula right = xor.getRight() ;
            BDDNode low = BDDFactory.ast_to_bdd(left) ;
            BDDNode high = BDDFactory.ast_to_bdd(right) ;
            return apply_xor(low, high) ;
        }

        return null ;
    }

    private static BDDNode apply_or(BDDNode u, BDDNode v) {
        if (u.isLeaf() && v.isLeaf()) {
            return new BDDNode(
                    u.getValue() || v.getValue()
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

        BDDNode low = apply_or(u_low, v_low) ;
        BDDNode high = apply_or(u_high, v_high) ;

        if (low.equals(high)) return low ;

        return new BDDNode(x, low, high) ;

    }

    private static BDDNode apply_neg(BDDNode u) {
        if (u.isLeaf()) return new BDDNode(!u.getValue()) ;
        BDDNode low = apply_neg(u.getLow()) ;
        BDDNode high = apply_neg(u.getHigh()) ;

        return new BDDNode(u.getVar(), low, high) ;
    }

    private static BDDNode apply_and(BDDNode u, BDDNode v) {
        if (u.isLeaf() && v.isLeaf()) {
            return new BDDNode(
                    u.getValue() && v.getValue()
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

        BDDNode low = apply_and(u_low, v_low) ;
        BDDNode high = apply_and(u_high, v_high) ;

        if (low.equals(high)) return low ;

        return new BDDNode(x, low, high) ;
    }

    private static BDDNode apply_xor(BDDNode u, BDDNode v) {
        if (u.isLeaf() && v.isLeaf()) {
            return new BDDNode(
                    u.getValue() ^ v.getValue()
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

        BDDNode low = apply_xor(u_low, v_low) ;
        BDDNode high = apply_xor(u_high, v_high) ;

        if (low.equals(high)) return low ;

        return new BDDNode(x, low, high) ;

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

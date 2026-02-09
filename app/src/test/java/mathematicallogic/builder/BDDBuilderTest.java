package src.test.java.mathematicallogic.builder;

import org.junit.Assert;
import org.junit.Test;
import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.builder.BDDFactory;
import src.main.java.mathematicallogic.formula.*;
import src.main.java.mathematicallogic.util.Utils;

public class BDDBuilderTest {
    Formula or  = new Or(new Var("p"), new Var("q")) ;
    Formula neg = new Not(new Var("q")) ;
    Formula and  = new And(new Var("p"), neg) ;

    @Test
    public void testBuildFromASTVar() {
        Formula f = new Var("p") ;

        BDDNode bdd = BDDFactory.ast_to_bdd(f) ;

        Assert.assertNotNull(bdd) ;
        Assert.assertEquals("p", bdd.getVar()) ;
        Assert.assertTrue(bdd.getHigh().getValue()) ;
        Assert.assertFalse(bdd.getLow().getValue()) ;
    }

    @Test
    public void testBuildFromASTOr() {

        BDDNode bdd = BDDFactory.ast_to_bdd(or) ;

        Assert.assertNotNull(bdd) ;

        boolean rootIsP = "p".equals(bdd.getVar()) ;

        Assert.assertTrue(rootIsP) ;

        BDDNode low = bdd.getLow() ;
        BDDNode high = bdd.getHigh() ;

        Assert.assertNotNull(low) ;
        Assert.assertNotNull(high) ;
    }

    @Test
    public void testBuildFromASTOrReduced() {
        BDDNode bdd = BDDFactory.ast_to_bdd(or) ;

        Assert.assertNotNull(bdd) ;

        boolean rootIsP = "p".equals(bdd.getVar()) ;

        Assert.assertTrue(rootIsP) ;

        BDDNode low = bdd.getLow() ;
        BDDNode high = bdd.getHigh() ;

        Assert.assertNotNull(low) ;
        Assert.assertNotNull(high) ;

        boolean highIsTrueLeaf = high.isLeaf() && high.getValue() ;
        boolean lowIsTrueLeaf = low.isLeaf() && low.getValue() ;

        Assert.assertTrue(lowIsTrueLeaf || highIsTrueLeaf) ;
    }

    @Test
    public void testBuildFromASTNegate() {
        BDDNode bdd = BDDFactory.ast_to_bdd(neg) ;
        Assert.assertNotNull(bdd) ;

        Assert.assertFalse(bdd.getHigh().getValue()) ;
        Assert.assertTrue(bdd.getLow().getValue()) ;
    }

    @Test
    public void testBuildCombinedAST() {
        Formula combined = new Not(or) ;
        BDDNode bdd = BDDFactory.ast_to_bdd(combined) ;
        Utils.print_BDD(bdd) ;
        Assert.assertNotNull(bdd) ;
    }

    @Test
    public void testBuildFromASTAnd() {
        BDDNode bdd = BDDFactory.ast_to_bdd(and) ;

        Utils.print_BDD(bdd) ;

        Assert.assertNotNull(bdd) ;

        BDDNode low = bdd.getLow() ;
        BDDNode high = bdd.getHigh() ;

        Assert.assertNotNull(low) ;
        // a sx p = 0
        Assert.assertFalse(low.getValue()) ;
        // p = 1 !q = 1 (q = 0, a sx)
        Assert.assertTrue(high.getLow().getValue()) ;
        // p = 1 !q = 0 (q = 1, a dx)
        Assert.assertFalse(high.getHigh().getValue()) ;
    }

}

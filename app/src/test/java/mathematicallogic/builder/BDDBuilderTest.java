package src.test.java.mathematicallogic.builder;

import org.junit.Assert;
import org.junit.Test;
import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.builder.BDDFactory;
import src.main.java.mathematicallogic.formula.Formula;
import src.main.java.mathematicallogic.formula.Or;
import src.main.java.mathematicallogic.formula.Var;

public class BDDBuilderTest {
    Formula or = new Or(new Var("p"), new Var("q")) ;

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

}

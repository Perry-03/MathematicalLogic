package src.test.java.mathematicallogic.builder;

import org.junit.Assert;
import org.junit.Test;
import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.builder.BDDFactory;
import src.main.java.mathematicallogic.formula.Formula;
import src.main.java.mathematicallogic.formula.Var;

public class BDDBuilderTest {

    @Test
    public void testBuildFromASTVar() {
        Formula f = new Var("p") ;

        BDDNode bdd = BDDFactory.ast_to_bdd(f) ;

        Assert.assertNotNull(bdd);
        Assert.assertEquals("p", bdd.getVar());
        Assert.assertTrue(bdd.getHigh().getValue()) ;
        Assert.assertFalse(bdd.getLow().getValue());
    }
}

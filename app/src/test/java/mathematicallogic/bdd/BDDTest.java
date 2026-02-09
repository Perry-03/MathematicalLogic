package src.test.java.mathematicallogic.bdd;

import org.junit.Assert;
import org.junit.Test;
import src.main.java.mathematicallogic.bdd.BDDNode;
import src.main.java.mathematicallogic.formula.Var;

public class BDDTest {

    @Test
    public void testBuildSimpleLeafBDD() {
        BDDNode a = new BDDNode(true) ;
        BDDNode b = new BDDNode(false) ;

        Assert.assertTrue(a.getValue());
        Assert.assertFalse(b.getValue());
    }

}

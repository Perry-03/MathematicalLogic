package mathematicallogic.bdd;

import org.junit.Assert;
import org.junit.Test;

public class BDDTest {

    @Test
    public void testBuildSimpleLeafBDD() {
        BDDNode a = new BDDNode(true) ;
        BDDNode b = new BDDNode(false) ;

        Assert.assertTrue(a.getValue());
        Assert.assertFalse(b.getValue());
    }

    @Test
    public void testBuildSimpleNodeBDD() {
        BDDNode a = new BDDNode("a", new BDDNode(true), new BDDNode(false)) ;

        Assert.assertTrue(a.getLow().getValue()) ;
        Assert.assertFalse(a.getHigh().getValue()) ;
        Assert.assertEquals("a", a.getVar()); ;
    }

}

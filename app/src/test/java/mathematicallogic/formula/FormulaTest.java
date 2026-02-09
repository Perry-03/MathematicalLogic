package src.test.java.mathematicallogic.formula;

import org.junit.Assert;
import org.junit.Test;
import src.main.java.mathematicallogic.formula.*;

public class FormulaTest {
    @Test
    public void testVar() {
        Formula f = new Var("a") ;

        Assert.assertEquals("a", f.toString()) ;
    }

    @Test
    public void testNot() {
        Formula f = new Not(new Var("a")) ;

        Assert.assertEquals("!a", f.toString()) ;
    }

}

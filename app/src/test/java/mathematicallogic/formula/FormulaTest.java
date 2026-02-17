package mathematicallogic.formula;

import mathematicallogic.formula.*;
import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void testAnd() {
        Formula f = new And(new Var("a"), new Var("b")) ;

        Assert.assertEquals("(a && b)", f.toString()) ;
    }

    @Test
    public void testOr() {
        Formula f = new Or(new Var("a"), new Var("b")) ;

        Assert.assertEquals("(a || b)", f.toString()) ;
    }

}

package src.test.java.mathematicallogic.formula;

import org.junit.Assert;
import org.junit.Test;
import src.main.java.mathematicallogic.formula.*;

public class FormulaTest {
    @Test
    public void testVar() {
        Formula v = new Var("a");

        Assert.assertEquals("a", v.toString());
    }

}

package src.main.java.mathematicallogic.formula;

public class And implements Formula {
    private final Formula f1 ;
    private final Formula f2 ;

    public And(final Formula a, final Formula b) { this.f1 = a ; this.f2 = b ; }

    @Override public String toString() { return "(" + f1 + " ∧ " + f2 + ")" ; }
}

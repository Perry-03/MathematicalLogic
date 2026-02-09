package src.main.java.mathematicallogic.formula;

public class Or implements Formula {
    private final Formula f1 ;
    private final Formula f2 ;

    public Or(final Formula f1, final Formula f2) { this.f1 = f1 ; this.f2 = f2 ; }

    @Override public String toString() { return "(" + f1 + " ∨ " + f2 + ")" ; }
}

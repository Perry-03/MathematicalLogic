package src.main.java.mathematicallogic.formula;

public class Not implements Formula {
    private final Formula f ;

    public Not(final Formula f) { this.f = f ; }

    @Override public String toString() { return "!" + this.f.toString() ; }
}

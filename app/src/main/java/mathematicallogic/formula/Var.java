package src.main.java.mathematicallogic.formula;

public class Var implements Formula {
    private final String name ;

    public Var(final String a) { this.name = a ; }

    @Override public String toString() { return this.name ; }

    public String getName() { return this.name ; }
}

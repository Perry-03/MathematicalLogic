package src.main.java.mathematicallogic.formula;

public class Xor implements  Formula {
    private final Formula left ;
    private final Formula right ;

    public Xor(final Formula left, final Formula right) { this.left = left ; this.right = right ; }

    @Override public String toString() { return "(" + left + " ⊕ " + right + ")" ; }

    public Formula getLeft() { return left ; }
    public Formula getRight() { return right ; }
}

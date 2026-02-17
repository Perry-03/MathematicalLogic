package mathematicallogic.formula;

public class And implements Formula {
    private final Formula left ;
    private final Formula right ;

    public And(final Formula a, final Formula b) { this.left = a ; this.right = b ; }

    @Override public String toString() { return "(" + left + " && " + right + ")" ; }
    
    public  Formula getLeft() { return left; }
    public Formula getRight() { return right; }
}

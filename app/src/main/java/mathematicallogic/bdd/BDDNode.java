package mathematicallogic.bdd;

public class BDDNode {
    private String vName ;
    private BDDNode low ;
    private BDDNode high ;
    private boolean value ;

    public BDDNode(boolean value) { this.value = value ; }
    public BDDNode(final String vName, BDDNode low, BDDNode high) {
        this.vName = vName ;
        this.low = low ;
        this.high = high ;
    }

    public boolean getValue() { return value ; }
    public String getVar() { return vName ; }
    public BDDNode getLow() { return low ; }
    public BDDNode getHigh() { return high ; }

    public boolean isLeaf() { return low == null && high == null ; }

    @Override public boolean equals(Object o) {
        if (this == o) return true ;
        if (!(o instanceof BDDNode other)) return false ;

        if (other.isLeaf() && isLeaf()) return other.getValue() == this.getValue() ;
        if (this.isLeaf() != other.isLeaf()) return false ;
        if (!this.getVar().equals(other.getVar())) return false ;

        boolean lowEq  = getLow() == null && other.getLow() == null ||
                         getLow() != null && getLow().equals(other.getLow()) ;
        boolean highEq = getHigh() == null && other.getHigh() == null ||
                         getHigh() != null && getHigh().equals(other.getHigh()) ;

        return lowEq && highEq ;
    }
}

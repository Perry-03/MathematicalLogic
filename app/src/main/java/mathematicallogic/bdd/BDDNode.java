package src.main.java.mathematicallogic.bdd;

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
}

package mathematicallogic.builder;

import mathematicallogic.bdd.BDDNode;

public record Triple(String vName, BDDNode u, BDDNode v) {

    @Override public boolean equals(Object o) {
        if (this == o) return true ;
        if (o instanceof Triple(String name, BDDNode u1, BDDNode v1))
            return name.equals(this.vName) && u1.equals(this.u) && v1.equals(this.v);

        return false ;
    }
}

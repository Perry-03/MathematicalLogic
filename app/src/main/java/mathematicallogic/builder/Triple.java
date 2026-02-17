package mathematicallogic.builder;

import mathematicallogic.bdd.BDDNode;

public record Triple(String vName, BDDNode u, BDDNode v) { }

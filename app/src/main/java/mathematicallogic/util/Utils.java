package src.main.java.mathematicallogic.util;

import src.main.java.mathematicallogic.bdd.BDDNode;

public class Utils {

    public static void print_BDD(BDDNode root) {
        print_BDD(root, "", true);
    }

    private static void print_BDD(BDDNode node, String indent, boolean isRoot) {
        if (node == null) return;

        // Stampa nodo
        if (node.isLeaf()) {
            System.out.println(indent + (node.getValue() ? "1" : "0"));
            return;
        }

        if (isRoot) {
            System.out.println(node.getVar());
        } else {
            System.out.println(indent + node.getVar());
        }

        // Stampa ramo low (0)
        System.out.print(indent + " ├─0→ ");
        print_BDD(node.getLow(), indent + " │   ", false);

        // Stampa ramo high (1)
        System.out.print(indent + " └─1→ ");
        print_BDD(node.getHigh(), indent + "     ", false);
    }

}

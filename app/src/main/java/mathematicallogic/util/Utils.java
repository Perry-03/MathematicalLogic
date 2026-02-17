package mathematicallogic.util;


import mathematicallogic.bdd.BDDNode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utils {

    public static void print_BDD(BDDNode root) {
        print_BDD(root, "", true);
    }

    private static void print_BDD(BDDNode node, String indent, boolean isRoot) {
        if (node == null) return;

        if (node.isLeaf()) {
            System.out.println(indent + (node.getValue() ? "1" : "0"));
            return;
        }

        if (isRoot) {
            System.out.println(node.getVar());
        } else {
            System.out.println(indent + node.getVar());
        }

        System.out.print(indent + " ├─0→ ");
        print_BDD(node.getLow(), indent + " │   ", false);

        System.out.print(indent + " └─1→ ");
        print_BDD(node.getHigh(), indent + "     ", false);
    }

    public static void export_to_dot(BDDNode bdd, String filename, String gLabel) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            String safeLabel = gLabel
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"") ;

            writer.println("digraph BDD {");
            writer.println("  label =\"" + gLabel + "\";") ;
            writer.println("  labelloc =\"t\";") ;
            writer.println("  rankdir=TB;");
            writer.println("  node [shape=circle];");

            Map<BDDNode, Integer> nodeIds = new HashMap<>();
            assignIds(bdd, nodeIds);

            for (Map.Entry<BDDNode, Integer> entry : nodeIds.entrySet()) {
                BDDNode node = entry.getKey();
                int id = entry.getValue();

                if (node.isLeaf()) {
                    String label = node.getValue() ? "T" : "F";
                    String shape = "box";
                    String style = node.getValue() ? "\"filled,bold\"" : "filled";
                    String color = node.getValue() ? "lightgreen" : "lightcoral";
                    writer.println("  n" + id + " [label=\"" + label +
                            "\", shape=" + shape +
                            ", style=" + style +
                            ", fillcolor=" + color + "];");
                } else {
                    writer.println("  n" + id + " [label=\"" + node.getVar() + "\"];");
                }
            }

            Set<BDDNode> visited = new HashSet<>();
            writeEdges(bdd, nodeIds, writer, visited);

            writer.println("}");

            System.out.println("BDD esportato in: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void assignIds(BDDNode node, Map<BDDNode, Integer> nodeIds) {
        if (nodeIds.containsKey(node)) return;
        nodeIds.put(node, nodeIds.size());
        if (!node.isLeaf()) {
            assignIds(node.getLow(), nodeIds);
            assignIds(node.getHigh(), nodeIds);
        }
    }

    private static void writeEdges(BDDNode node, Map<BDDNode, Integer> nodeIds,
                                   PrintWriter writer, Set<BDDNode> visited) {
        if (node.isLeaf() || visited.contains(node)) return;
        visited.add(node);

        int nodeId = nodeIds.get(node);
        int lowId = nodeIds.get(node.getLow());
        int highId = nodeIds.get(node.getHigh());

        writer.println("  n" + nodeId + " -> n" + lowId +
                " [style=dashed, label=\"0\"];");

        writer.println("  n" + nodeId + " -> n" + highId +
                " [style=solid, label=\"1\"];");

        writeEdges(node.getLow(), nodeIds, writer, visited);
        writeEdges(node.getHigh(), nodeIds, writer, visited);
    }

}

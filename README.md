# BINARY DECISION DIAGRAMS (BDD)

## What's a BDD?
A BDD is a set of an <b>acyclic, rooted graph </b>with directed edges.
Non-terminal nodes are called <b>decision nodes</b>, labelled with a boolean variable. Each decision node has two child nodes:
1) <b>Low Child</b>: the left one
2) <b>High Child</b>: the right one

Therefore, each decision node has 2 edges. The left edge (linked to its low-child) assigns
<b> False </b> to the node, the right one (linked to high-child) assigns <b> True</b>.

A BDD is ordered (<b>OBDD</b>) if different variables appears in the same order in every path in the BDD.

## How to build a BDD from a formula?
### BDD for A AND B
Here is an example of a BDD for the formula <code>f = A && B</code>


### BDD for A OR B
Here is an example of a BDD for the formula <code>f = A || B</code>


### BDD for A XOR B
Here is an example of a BDD for the formula <code>f = A | B</code>, therefore is a XOR between A and B.


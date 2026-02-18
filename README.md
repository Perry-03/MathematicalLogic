# BINARY DECISION DIAGRAMS (BDD)

## What is a BDD?
A BDD is a data structure used to represent a Boolean function as a *rooted, directed acyclic* graph.
Non-terminal nodes are called **decision nodes**, labelled with a Boolean variable. Each decision node has two outgoing edges 
corresponding to the possible assignments of the variable:
1) **Low Child (0-edge)**: represents the assignment *False*
2) **High Child (1-edge)**: represents the assignment *True*

The diagram also contains **terminal nodes** labelled *True* and *False*, which represent the final value of the Boolean function.
Evaluating a path in the diagram corresponds to assigning values to the variables (decision nodes) along the same path until a terminal
node is reached. The label of a terminal node represents the resulting value of the Boolean function under those assignments.

### Some examples
#### A OR B
<a id="a-or-b"></a>
![A or B](img/aORb.png)\
This is the BDD for the Boolean function <code>a || b</code>. The diagram can be interpreted as follows:
- Assigning 1 to <code>a</code> results in the function evaluating to *True*, regardless of the value assigned to <code>b</code>.
- Assigning 0 to <code>a</code>, the function evaluates to *True* if <code>b</code> is *True*, and to *False* if <code>b</code> is *False*.

#### A AND B
<a id="a-and-b"></a>
![A and B](img/aANDb.png)\
This is the BDD for the Boolean function <code>a && b</code>. As in the previous example, we can evaluate the function by following the paths in the diagram:
- Assigning 1 to <code>a</code> the function will be evaluated *True* only if we follow the 1-edge for <code>b</code>.
- On the other hand, if we assign 0 to <code>a</code> the function always evaluates to *False*, regardless of the value of <code>b</code>.

## Reduced Binary Decision Diagrams
In the BDD representing the Boolean function [A or B](#a-or-b), we can observe that the diagram can be reduced. As discussed earlier, regardless of the value of <code>b</code>,
if we assign 1 to <code>a</code> the function evaluates to *True* due to the OR property.
Therefore, the BDD can be reduced by removing the decision node <code>b</code> from the branch where <code>a = 1</code>.
The resulting *Reduced Binary Decision Diagram* for <code>a || b</code> is shown in the figure below.

<a id="a-and-b-reduced"></a>
![A or B reduced](img/aORbREDUCED.png)\
The same reduction principle can be applied to the BDD representing [A and B](#a-and-b). Since the AND function evaluates to *True* only
when both variables are assigned *True*, the resulting **reduced** BDD is shown below.

![A and B reduced](img/aANDbREDUCED.png)

## How to reduce a BDD
**Input**: A *bdd*.\
**Output**: A reduced binary decision diagram *bdd'*
**Steps of the algorithm**:
1. If *bdd* has more than two distinct leaves (one labeled T and one labeled F), remove duplicate leaves. Direct all edges that pointed to leaves
to the remaining two leaves </li>
2. Perform the following steps as long as possible: </li>
   1. If both outgoing edges of a node labeled p<sub>i</sub> point to the same node labeled p<sub>j</sub>, delete this node for p<sub>i</sub> and direct p<sub>i</sub>'s incoming edges to p<sub>j</sub>.
   2. If two nodes labeled p<sub>i</sub> are the roots of identical sub-BDDs, delete one sub-BDD and direct its incoming edges to the other node.

## Ordered Binary Decision Diagram (OBDD)
An **OBDD** is a BDD such that the set of orderings of atoms defined by the branches is compatible.
The size of the diagram vary based on both the function being represented and by the chosen ordering of the variables.
Different variable orderings may lead to dramatically different BDD sizes. In some cases, a function that has a linear-size
BDD under one ordering may require an exponential-size BDD under another ordering.
Find the best variable ordering is quite difficult (*NP-hard*), usually heuristic algorithms are used.

## How to apply operators to BDDs
The power of BDDs is the ability to perform operations directly on two reduced BDDs. Given two **reduced** BDDs bdd<sub>1</sub>, bdd<sub>2</sub> and an operator *op*,
the algorithm **Apply** recursively constructs the BDD for <code>bdd<sub>1</sub> *op* bdd<sub>2</sub></code>.
The algorithm is described as follows:\
**Input**: OBDDs bdd<sub>1</sub> for formula A<sub>1</sub> and bdd<sub>2</sub> for formula A<sub>2</sub> and an operator *op*.\
**Output**: An OBDD for the formula <code>A<sub>1</sub> *op* A<sub>2</sub></code>.\
**Steps of the algorithm**:
1) If bdd<sub>1</sub> and bdd<sub>2</sub> are both leaves labeled w<sub>1</sub> and w<sub>2</sub>, respectively, return the leaf
labeled by w<sub>1</sub> *op* w<sub>2</sub>.
2) If the roots of bdd<sub>1</sub> and bdd<sub>2</sub> are labeled by the same atom *p*, return the following
   BDD:(a) the root is labeled by p; (b) the left sub-BDD is obtained by recursively
   performing this algorithm on the left sub-BDDs of bdd<sub>1</sub> of bdd1 and bdd<sub>2</sub>; (c) the right
   sub-BDD is obtained by recursively performing this algorithm on the right sub
   BDDs of bdd<sub>1</sub> and bdd<sub>2</sub>.
3) If the root of bdd<sub>1</sub> is labeled *p<sub>1</sub>* and the root of bdd<sub>2</sub> is labeled *p<sub>2</sub>* such that
   <code>p1 < p2</code> in the ordering, return the following BDD: (a) the root is labeled by p<sub>1</sub>;
   (b) the left sub-BDD is obtained by recursively performing this algorithm on the
   left sub-BDD of bdd<sub>1</sub> and on (the entire BDD) bdd<sub>2</sub>; (c) the right sub-BDD is
   obtained by recursively performing this algorithm on the right sub-BDD of bdd<sub>1</sub>
   and on (the entire BDD) bdd<sub>2</sub>.
4) Otherwise, we have a symmetrical case to the previous one. The BDD returned
   has its root labeled by *p<sub>2</sub>* and its left (respectively, right) sub-BDD obtained by
   recursively performing this algorithm on bdd<sub>1</sub> and on the left (respectively, right)
   sub-BDD of bdd<sub>2</sub>.


## How to run the app
Firstly unzip the file <code>app.zip</code> in any locations of your computer. After this, you can open the *app* directory
with any editor (i.e. Visual Studio Code).

The app can be launched with the <code>app.bat</code> file: type in the command line <code>app/bin/app.bat</code>.
This command will launch the <code>Main</code> of the application.

If you want to test your own formulas, you should create a file named <code>formulas.txt</code> (the name **must** be the same),
write all the Boolean function you want and then just run <code>app.bat</code> again.

Input formulas are not checked, so here are some guidlines you should know before writing your own input:
- Operators accepted: <code>||, &&, !, ^, =></code>
- Precedence can be controlled using parenthesis <code>()</code>
- Spaces between formulas, operators are accepted but not necessery
- The application is <code>case-sensitive</code> therefore <code>a</code> and <code>A</code> are treated as different variables
- Do not leave empty lines between formulas

For example, whether you **do not** create your own <code>formulas.txt</code> the program will run with this standard file:
```text
a || !a
(a => b) => ((b => c) => (a => c))
(a && b) => a
a && !a
(a && !a) && b
!(a && b)
!a || !b
a => b
!a || b
(a && b) || (c && d)
(a || c) && (a || d) && (b || c) && (b || d)
a ^ b
(a || b) && !(a && b)
```
When you create your custom <code>formulas.txt</code>, the program will ignore the standard file.

Once the app finishes run, each **OBDD** will be generated inside the directory <code>generated/graphs</code>.
At the beginning of each launch, the app will clean and rebuild the <code>generated</code> directory.

The easiest way to represent a **BDD** is a <code>*.dot</code> file, if you want to get a clear image of the result, you can try
[Graphviz Online](https://dreampuf.github.io/GraphvizOnline/?engine=dot#digraph%20G%20%7B%0A%0A%20%20subgraph%20cluster_0%20%7B%0A%20%20%20%20style%3Dfilled%3B%0A%20%20%20%20color%3Dlightgrey%3B%0A%20%20%20%20node%20%5Bstyle%3Dfilled%2Ccolor%3Dwhite%5D%3B%0A%20%20%20%20a0%20-%3E%20a1%20-%3E%20a2%20-%3E%20a3%3B%0A%20%20%20%20label%20%3D%20%22process%20%231%22%3B%0A%20%20%7D%0A%0A%20%20subgraph%20cluster_1%20%7B%0A%20%20%20%20node%20%5Bstyle%3Dfilled%5D%3B%0A%20%20%20%20b0%20-%3E%20b1%20-%3E%20b2%20-%3E%20b3%3B%0A%20%20%20%20label%20%3D%20%22process%20%232%22%3B%0A%20%20%20%20color%3Dblue%0A%20%20%7D%0A%20%20start%20-%3E%20a0%3B%0A%20%20start%20-%3E%20b0%3B%0A%20%20a1%20-%3E%20b3%3B%0A%20%20b2%20-%3E%20a3%3B%0A%20%20a3%20-%3E%20a0%3B%0A%20%20a3%20-%3E%20end%3B%0A%20%20b3%20-%3E%20end%3B%0A%0A%20%20start%20%5Bshape%3DMdiamond%5D%3B%0A%20%20end%20%5Bshape%3DMsquare%5D%3B%0A%7D).
You just need to copy the code inside your <code>inputN.dot</code> and paste it into the Graphviz editor.

Note that your <code>.dot</code> files, will be named as <code>input1.dot, input2.dot, ..., inputN.dot</code>, this happens because the application
creates one BDD for each line of the input file.

It is also possible to add command line argument before launching the program, the arguments accepted are:
- ``--no-benchmark`` which ignores the standard benchmarks included in the project.
- ``--no-scalability`` which ignores the scalability tests.

Please note that the source code is not available in the `app.zip`, but it is in the file `source-code.zip` or visiting my [GitHub](https://github.com/Perry-03/MathematicalLogic).
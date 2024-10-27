
/**
 * Nodes are used in the pathfinding class in order to
 * add properties to tiles.
 */
public class Node {
    Node parent;
    public int col;
    public int row;
    double gCost;
    double hCost;
    double fCost;
    boolean solid;
    boolean open;
    boolean checked;

    /** Base constructor assigning every node created a collumn
     * and a row.
     */

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }

}

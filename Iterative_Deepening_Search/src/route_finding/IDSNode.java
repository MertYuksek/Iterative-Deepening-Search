package route_finding;

public class IDSNode {

    private int depth = 0;
    private Node node;

    public IDSNode(Node node) {
        this.node = node;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "IDSNode{" +
                "depth=" + depth +
                ", node=" + node +
                '}';
    }
}

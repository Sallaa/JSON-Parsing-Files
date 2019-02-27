import java.util.*;

public class Node {
   String name = null;
   Node parent;
   List<Node> children = new ArrayList<Node>();

    public Node(Node parent) {
        this.parent = parent;
    }
}
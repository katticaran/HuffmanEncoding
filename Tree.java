
public class Tree {
public Node root;
public Node curNode;

public Tree() {
    this.root = null;
}

public void addToTree(Node node) {
    if (this.root == null) {
        this.root = node;
    }
    
    else {
        Node curNode = this.root;
        
        if (!curNode.isLeaf) {
            
        }
    }
    
}

}

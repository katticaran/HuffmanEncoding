
public class Node {
    public Short value;
    public Integer frequency;
    public boolean isLeaf;
    public Node lChild;
    public Node rChild;

  
    /**Constructor
     * @param value a Short
     */
    public Node(Short value) {
        this.value = value;
        this.frequency = 0;
        this.isLeaf = true;
        lChild = null;
        rChild = null;
    }

    /**
     * Constructor
     * @param value a Short
     * @param frequency an Integer
     */
    public Node(Short value, Integer frequency) {
        this.value = value;
        this.frequency = frequency;
        this.isLeaf = true;
        lChild = null;
        rChild = null;
    }

    /**
     * Constructor
     * @param frequency and Integer
     */
    public Node(Integer frequency) {
        this.value = 0;
        this.frequency = frequency;
        this.isLeaf = false;
        lChild = null;
        rChild = null;
    }

    /** 
     * Sets left child of Node
     * @param child a Node
     */
    public void setLChild(Node child) {
        this.lChild = child;
    }

    /**
     * Sets right child of Node
     * @param child a Node
     */
    public void setRChild(Node child) {
        this.rChild = child;
    }

    /**
     * @return the frequency of Node
     */
    public int getFreq() {
        return this.frequency;
    }
    
    /**
     * @return the value of Node
     */
    public short getValue() {
        return this.value;
    }
}
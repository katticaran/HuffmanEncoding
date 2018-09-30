import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author katticar, chavezgi
 *
 */

public class HuffmanTree {
    Node root;
    BitInputStream in;
    BitOutputStream out;
    Map<Short, String> encodeTable;  




    /**Constructs a Huffman Tree
     * @param m a map of Shorts with corresponding Integer values
     */
    public HuffmanTree(Map<Short, Integer> m){

        Comparator<Node> comparator = new PriComparator ();
        PriorityQueue<Node> queue =  new PriorityQueue<Node>(10, comparator);


        Node newNode = new Node((short) 256,1);
        queue.add(newNode);

        for(Map.Entry<Short, Integer> entry : m.entrySet()) {
            Short key = entry.getKey();
            Integer value = entry.getValue();

            newNode = new Node(key,value);
            queue.add(newNode);
        }

        System.out.println("Inside priQueue");
        while(!queue.isEmpty()) {
            Node node1 = queue.poll();

            if (queue.isEmpty()) {
                this.root = node1;
                queue.add(node1);
                break;
            }
            Node node2 = queue.poll();

            Node updatedNode = treeCreate(node1,node2) ;
            queue.add(updatedNode);

        }
        printTree();
        this.encodeTable = new HashMap<Short, String> ();
        encodeHelper(this.root, ""); 
        for(Map.Entry<Short, String> entry : encodeTable.entrySet()) {
            System.out.print(entry.getKey() + " ");
            System.out.println(entry.getValue());
        }
    }


    /**
     * A helper method for constructing a Huffman tree
     * @param in a BitInputStream 
     * @return newNode a node
     */
    public Node helpTree(BitInputStream in) {
        int type = in.readBit();
        // System.out.println(type);

        switch (type) {
        case 0: 
            short value = (short) in.readBits(9);
            Node newNode = new Node(value, 1);
            // System.out.println("Leaf: " + (char)value);
            return (newNode);
        case 1: 
            //   System.out.println("Node ");
            return treeCreate(helpTree(in), helpTree(in));
        default :
            throw new IllegalArgumentException();
        }
    }


    /** Constructor for Huffman tree
     * @param in a BitInputStream
     */
    public HuffmanTree(BitInputStream in) {
        this.in = in;
        int magicNumber = in.readBits(32);
        //        System.out.println(magicNumber);

        if (magicNumber != 1846) {
            System.out.println("Invalid File!");
            throw new IllegalArgumentException();
        }

        this.root = helpTree(in) ;
    }


    /**
     * Prints the tree
     */
    public void printTree() {
        printTreeHelper(this.root);
    }

    /**Helper method for printing tree
     * @param node a Node, used to print tree
     */
    public void printTreeHelper(Node node) {
        if (node.isLeaf) {
            System.out.println((char)node.getValue());
        }
        else {
            System.out.println("Node");
            printTreeHelper(node.lChild);
            printTreeHelper(node.rChild);
        }
    }

    /**
     * Creates a new node with children node1 and node2
     * @param node1 a Node
     * @param node2 a Node
     * @return newRoot, a node an updated root
     */
    public Node treeCreate2(Node node1, Node node2) {
        Node newRoot = new Node((node1.frequency+ node2.frequency));
        if (node1.frequency >= node2.frequency) {
            newRoot.setRChild(node1);
            newRoot.setLChild(node2);
        } else {
            newRoot.setRChild(node2);
            newRoot.setLChild(node1);
        }

        return newRoot;
    }

    /**
     * Creates a node with children node1 and node2 
     * @param node1, a node
     * @param node2, a node
     * @return newRoot, a node
     */
    public Node treeCreate(Node node1, Node node2) {
        Node newRoot = new Node((node1.frequency+ node2.frequency));
        newRoot.setRChild(node2);
        newRoot.setLChild(node1);
        return newRoot;
    }



    /**
     * Writes out the values of nodes
     * @param in a BitInputStream
     * @param out a BitOutputStream
     * @param node a Node
     * @return
     */
    boolean decodeHelper(BitInputStream in, BitOutputStream out, Node node) {
        boolean endFlag = false;
        if (node.isLeaf) {
            if (node.value == 256) {
                endFlag = true;
            } else {
                System.out.println((char)node.getValue());
                out.writeBits(node.getValue(),8);
            }
        } else {
            int c = in.readBit();
           // System.out.println(c);
            switch (c) {
            case 0:
                node = node.lChild;
                return decodeHelper( in,  out,  node);

            case 1:
                node = node.rChild;
                return decodeHelper( in,  out,  node);
                
            case -1: return true;

            default:
                throw new IllegalArgumentException();
            }

        }
        return endFlag;
    }


    /**
     * Decodes the given BitInputStream 
     * @param in a BitInputStream
     * @param out a BitOutStream
     */
    void decode(BitInputStream in, BitOutputStream out) {
        boolean endFlag = false;

        while(in.hasBits() && !endFlag) 
        {
            Node curNode = this.root;
            endFlag = decodeHelper( in,  out,  curNode);
        }
    }



    /**
     * Adds the values and their encoded counterparts to a map
     * @param node a Node
     * @param str a String
     */
    private void encodeHelper(Node node, String str) {
        if (node.isLeaf) {
            encodeTable.put(node.value, str);
            str = "";
        } else {
            encodeHelper(node.lChild, str+ "0");
            encodeHelper(node.rChild, str+"1");
        }
    }


    /**
     * Serializes the huffmantree
     * @param out a BitOuputStream
     * @return numbits, an Integer
     */
    public int serialize(BitOutputStream out) {
        int numbits = 0;
        numbits = serializeTreeHelper(this.root, out, numbits);
        return numbits;
    }

    /**
     * Helper method for tree serialization
     * @param node a Node
     * @param out a BitOutputStream
     * @param numbits an Integer
     * @return
     */
    public int serializeTreeHelper(Node node, BitOutputStream out, int numbits) {
        if (node.isLeaf) {
            // System.out.println((char)node.getValue());
            int val = node.getValue();
            out.writeBit(0);
            out.writeBits(val,9);
            numbits+=2;
            numbits = numbits%8;
            return numbits;
        }
        else {
            out.writeBit(1);
            numbits++;
            numbits = numbits%8;
            serializeTreeHelper(node.lChild, out, numbits);
            serializeTreeHelper(node.rChild, out, numbits);
            return numbits;
        }
    }


    /**
     * Creates header and encodes Huffmantree
     * @param in a BitInputStream
     * @param out a BitOutput Stream 
     */
    public void encodeWrite(BitInputStream in, BitOutputStream out)  {

        out.writeBits(1846,32);
        int numbits = serialize(out);

        short val = (short) in.readBits(8);

        while (val != -1) {
            System.out.println(val);
            String treePath = this.encodeTable.get((short) val);
            for (int i = 0; i < treePath.length(); i++) {
                if (treePath.charAt(i) == '1') {
                    out.writeBit(1);
                    numbits+=1;
                    numbits = numbits%8;
                } else if (treePath.charAt(i) == '0') {
                    out.writeBit(0);
                    numbits+=1;
                    numbits = numbits%8;
                }
            }
            val = (short) in.readBits(8);
        }
        String eof = encodeTable.get((short) 256);
        for (int i = 0; i < eof.length(); i++) {
            if (eof.charAt(i) == '1') {
                out.writeBit(1);
                numbits+=1;
                numbits = numbits%8;
            } else if (eof.charAt(i) == '0') {
                out.writeBit(0);
                numbits+=1;
                numbits = numbits%8;
            }
        }
        if (numbits > 0) {
            out.writeBits(0, 8- numbits );
        }
    }
}






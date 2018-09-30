import java.util.Comparator;

public class PriComparator implements Comparator<Node>
{
    
    @Override
    public int compare(Node x, Node y)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        if (x.getFreq() < y.getFreq())
        {
            return -1;
        }
        if (x.getFreq() > y.getFreq())
        {
            return 1;
        }
        return 0;
    }


}
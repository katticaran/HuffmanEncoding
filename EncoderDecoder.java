import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EncoderDecoder {

    
    /**
     * Creates a frequency table for given file
     * @param file a String
     * @return a Map<Short, Integer> with frequency values for all characters
     * @throws IOException
     */
    public static Map<Short, Integer> createFrequencyMap(String file) throws IOException {
        BitInputStream in = new BitInputStream(file);
        Map<Short, Integer> frequencyTable = new HashMap<Short, Integer>();
        short val = (short) in.readBits(8);

        while (val != -1) {
            if (frequencyTable.containsKey(val)){
                frequencyTable.put(val, frequencyTable.get(val)+1);
            } else {
                frequencyTable.put(val, 1);  
            }
            val = (short) in.readBits(8);
        }
        for(Map.Entry<Short, Integer> entry : frequencyTable.entrySet()) {
            System.out.print(entry.getKey() + " ");
            System.out.println( entry.getValue());
        }
        return frequencyTable;
    }
    
    
    
    /**
     * Encodes inFile to outFile
     * @param inFile a String
     * @param outFile a String
     * @throws IOException
     */
    public static void encode(String inFile, String outFile) throws IOException {
        Map<Short, Integer> frequencyMap;
        frequencyMap = createFrequencyMap(inFile);
        HuffmanTree tree = new HuffmanTree(frequencyMap);
        BitInputStream in = new BitInputStream(inFile);
        BitOutputStream out = new BitOutputStream(outFile);
        tree.encodeWrite(in, out);
        
    }
    
    /**
     * Decodes inFiles to outFile
     * @param inFile a String
     * @param outFile a String
     * @throws IOException
     */
    public static void decode(String inFile, String outFile) throws IOException {
      BitInputStream in = new BitInputStream(inFile);
     BitOutputStream out = new BitOutputStream(outFile);
     HuffmanTree val = new HuffmanTree( in);
     val.decode(in, out);
    }
}

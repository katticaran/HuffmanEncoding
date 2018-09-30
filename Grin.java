import java.io.IOException;

public class Grin {

    public static void main(String[] args) throws IOException {
            //reads command line arguments
        if (args.length == 3) {
            String code = args[0].toLowerCase();
            String inFile = args[1];
            String outFile = args[2];

            switch (code) {
            case "encode":
                encode(inFile,outFile);
                break;
            case "decode" :
                decode(inFile,outFile);
                break;
            default:
                System.out.println("Invalid Input. Quitting....");

            }
        } else {
            System.out.println("Invalid Input. Quitting....");
        }
    }



    /**
     * Performs decode on infile, and outputs it on outfile
     * @param infile a String
     * @param outfile a String
     * @throws IOException
     */
    public static void decode(String infile, String outfile) throws IOException {
        EncoderDecoder.decode(infile, outfile);

    }

    /**
     * Performs encode on infile, encoding it and outputting it to outfile
     * @param infile a String
     * @param outfile a String
     * @throws IOException
     */
    public static void encode(String infile, String outfile) throws IOException {
        EncoderDecoder.encode(infile, outfile);

    }

}




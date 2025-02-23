package IQ;

/** Exception thrown whenever the input file does not have the correct format.
*/
public class InvalidInputFormatException extends Exception {
    /** Constructs exception.
     * @param s     exception explanation.
    */
    public InvalidInputFormatException(String s){
        super(s);
    }
}
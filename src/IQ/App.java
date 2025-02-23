package IQ;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
/** Main program class. */
public class App {
    /** Main program. 
     * @param args  command line arguments. if none provided prompts user for
     *              file names to be read from and written to
    */
    public static void main(String[] args) {
        if (args.length > 2){
            System.out.println("wrong arguments");
            return;
        } 
        Scanner inputFile;
        PrintStream outputFile;
        String filename;
        Scanner sc = new Scanner(System.in);
        if (args.length == 0){
            while (true){
                System.out.println("Input File:");
                filename = sc.nextLine();
                try {
                    inputFile = new Scanner(new File(filename));
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!");
                }
            }
        } else {
            filename = args[0];
            try {
                inputFile = new Scanner(new File(filename));
            } catch (FileNotFoundException e){
                System.err.println("File not found");
                sc.close();
                return;
            }
        }
        while (true) {
            System.out.println("Output File:");
            filename = sc.nextLine();
            try {
                if (filename.length() > 0) {
                    outputFile = new PrintStream(new File(filename));
                } else {
                    outputFile = null;
                }
                break;
            } catch (FileNotFoundException e){
                System.out.println("Something went wrong");
            }
        }
        sc.close();
        try {
            System.out.println("Solving...");
            Puzzle puzzle = PuzzleIO.readFile(inputFile);
            puzzle.solve();
            PuzzleIO.printResult(puzzle, System.out);
            if (outputFile != null){
                PuzzleIO.printResult(puzzle, outputFile);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
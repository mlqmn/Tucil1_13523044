package IQ;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;

/** Class for handling input/output of puzzle */
public class PuzzleIO {
    /** Reads input from a Scanner class 
     * @param scanner   scanner from which puzzle is to be read.
     * @return          the Puzzle read from scanner.
     * @throws InvalidInputFormatException
     *       if input file is not in the correct format
    */
    public static Puzzle readFile(Scanner scanner) throws InvalidInputFormatException{
        try {
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            int p = scanner.nextInt();
            if (row < 1 || col < 1 || p < 1){
                throw new InvalidInputFormatException("number of row/column/p must be at least 1");
            }
            scanner.nextLine();
            GameMode gm = GameMode.valueOf(scanner.nextLine());
            Character[][] board = new Character[row][col];
            if (gm == GameMode.CUSTOM){
                readBoard(scanner, board, row, col);
            };
            ArrayList<Shape> shapes = readShape(scanner, p);
            if (gm == GameMode.DEFAULT){
                return new DefaultPuzzle(row, col, p, gm, shapes);
            } else if (gm == GameMode.CUSTOM){
                return new CustomPuzzle(row, col, p, gm, shapes, board);
            } else {
                System.err.println("Not yet implemented");
                return null;
            }
        } catch (InvalidInputFormatException e) {
            throw e;
        } catch (InputMismatchException e){
            throw new InvalidInputFormatException("invalid number of row/column/p");
        }
    }

    private static ArrayList<Shape> readShape(Scanner scanner, int p) throws InvalidInputFormatException {
        ArrayList<Shape> res = new ArrayList<Shape>();
        int i = 0;
        char tempName = '.';
        var tempMtx = new ArrayList<ArrayList<Boolean>>();
        while (scanner.hasNext()){
            String str = scanner.nextLine();    
            char c = '.';
            for (int j = 0; j < str.length(); j++){
                if (str.charAt(j) != ' ' && c == '.'){
                    c = str.charAt(j);
                } else if (str.charAt(j) != ' ' && str.charAt(j) != c){
                    throw new InvalidInputFormatException(
                        "Two or more different letters in the same line\n" +
                        "Line: \"" + str + "\"");
                }
            }
            if (c != tempName) {
                if (tempName != '.'){
                    res.add(new Shape(tempName, tempMtx));
                    i++;
                    if (i >= p) {
                        throw new InvalidInputFormatException(
                            "Too many shapes given");
                    }
                }
                tempMtx.clear();
                tempName = c;
            }
            ArrayList<Boolean> line = new ArrayList<Boolean>();
            for (int j = 0; j < str.length(); j++){
                if (str.charAt(j) == ' '){
                    line.add(false);
                } else {
                    line.add(true);
                }
            }
            tempMtx.add(line);
        }
        res.add(new Shape(tempName, tempMtx));
        i++;
        // System.out.println(p);
        // System.out.println(i);
        // System.out.println(res);
        if (i < p) {
            throw new InvalidInputFormatException(
                "Not enough shapes data\n" +
                "given p = " + Integer.toString(p) + "\n" + 
                "but " + Integer.toString(i) + " shapes specified");
        }
        return res;
    }

    private static void readBoard(Scanner scanner, Character[][] board, int row, int col)
        throws InvalidInputFormatException{
        for (int i = 0; i < row; i++){
            String line = scanner.next();
            if (line.length() < col){
                throw new InvalidInputFormatException("Not enough cells in a row");
            } else if (line.length() > col){
                throw new InvalidInputFormatException("Too many characters in a row");
            }
            for (int j = 0; j < col; j++){
                char c = line.charAt(j);
                // System.out.println(c);
                if (c == '.'){
                    board[i][j] = '.';
                } else if (c == 'X'){
                    board[i][j] = null;
                } else throw new InvalidInputFormatException("Illegal character");
            }
        }
    }
    
    /** Prints state of the puzzle's board to an output stream 
     * @param stream    print destination.
     * @param puzzle    the Puzzle to be printed
    */
    public static void printResult(Puzzle puzzle, PrintStream stream){
        if (puzzle.checkWin())
            stream.print(puzzle.boardToString());
        else
            stream.println("No solution");
        stream.print("Waktu pencarian: ");
        stream.print(puzzle.getSolveTime());
        stream.println("ms");
        stream.print("Banyak kasus yang ditinjau: ");
        stream.println(puzzle.getNVisitedBranch());
    }
}
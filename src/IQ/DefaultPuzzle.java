package IQ;
import java.util.ArrayList;

/** Class representing puzzle in DEFAULT puzzle mode. */
public class DefaultPuzzle extends CustomPuzzle {
    /** Number of rows in the puzzle board. */
    public int row;
    /** Number of columns in the puzzle board. */
    public int col;
    /** Number of pieces in the puzzle. */
    public int p;
    /** Mode of puzzle. */
    public GameMode gm;
    /** List of shapes of the pieces. */
    public ArrayList<Shape> shapes;
    /** Puzzle board. */
    public Character[][] board;
    /** Puzzle solution. */
    public String solution;
    int solveTime;
    int caseVisited;

    /** Constructs the puzzle input trivially. 
     * @param row   number of rows in the puzzle's board
     * @param col   number of columns in the puzzle's board
     * @param p     number of pieces
     * @param gm    puzzle's gamemode
     * @param shapes    list of puzzle pieces
    */
    public DefaultPuzzle(int row, int col, int p, GameMode gm, ArrayList<Shape> shapes){
        super(row, col, p, gm, shapes, new Character[row][col]);
    }
}
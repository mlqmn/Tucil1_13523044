package IQ;

/** Interface for puzzle representation */
public interface Puzzle {
    /** */
    /** Generates string representation of the puzzle board. (Uses ANSI escape code for color)
     * @return     string representation of the puzzle board.  
    */
    String boardToString();
    /** Check if the puzzle is solved.
     * @return     true if puzzle is solved, false otherwise.
    */
    boolean checkWin();
    /** Solves the puzzle. 
     * @return     solution in string. (Uses ANSI escape code for color)
    */
    String solve();
    /** Gets solve time in ms. 
     * @return    solve time in ms.
    */
    long getSolveTime();
    /** Gets number of branch visited in solving process. 
     * @return    number of branch visited.
    */
    long getNVisitedBranch();
}
enum GameMode {
    DEFAULT, CUSTOM, PYRAMID
}

package IQ;
import java.util.ArrayList;

/** Class representing puzzle in CUSTOM puzzle mode. */
public class CustomPuzzle implements Puzzle{
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
    private long solveTime;
    private long caseVisited;

    /** Constructs the puzzle input trivially. 
     * @param row   number of rows in the puzzle's board
     * @param col   number of columns in the puzzle's board
     * @param p     number of pieces
     * @param gm    puzzle's gamemode
     * @param shapes    list of puzzle pieces
     * @param board puzzle board
    */
    public CustomPuzzle(int row, int col, int p, GameMode gm, ArrayList<Shape> shapes, Character[][] board){
        this.row = row;
        this.col = col;
        this.p = p;
        this.gm = gm;
        this.shapes = shapes;
        this.board = board;
        this.solveTime = 0;
        this.caseVisited = 0;
    }

    public String boardToString(){
        String res = "";
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++){
                res += "\033[38;5;";
                if (this.board[i][j] !=null){
                    res += (int)this.board[i][j] * 151 % 255;
                    res += "m";
                    res += this.board[i][j];
                } else {
                    res += 0;
                    res += "m";
                    res += 'X';
                }
                // System.out.print("\033[0;32m");
            }
            res += '\n';
        }
        res += "\033[0m";
        return res;
    }

    public String solve(){
        long start = System.nanoTime();
        String res = this.solveHelper();
        long end =  System.nanoTime();
        this.solveTime = (end - start) / 1000000;
        return res;
    }
    private String solveHelper(){
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        Shape head = this.shapes.remove(0);
        for (int i = 0; i < 8; i++){
            Shape currShape = head.rotation(i);
            if (i >= 4){
                currShape = currShape.reflection();
            }
            this.generatePossibilites(currShape, rows, cols);
            for (int j = 0; j < rows.size(); j++){
                this.caseVisited += 1;
                CustomPuzzle nextStep = new CustomPuzzle(
                    this.row,
                    this.col,
                    this.p,
                    this.gm,
                    new ArrayList<Shape>(this.shapes),
                    fillBoard(this.board, currShape, rows.get(j), cols.get(j))
                );
                if (nextStep.checkWin() && shapes.size() == 0){
                    this.board = nextStep.board;
                    return nextStep.boardToString();
                }
                String res = nextStep.solveHelper();
                this.caseVisited += nextStep.caseVisited;
                if (res != null){
                    this.solution = res;
                    this.board = nextStep.board;
                    return res;
                }
            }
            rows.clear();
            cols.clear();
        }
        return null;
    }

    public boolean checkWin(){
        for (Character[] row : board){
            for (Character cell : row){
                if (cell == null){
                    return false;
                }
            }
        }
        return true;
    }
    private void generatePossibilites(Shape shape, ArrayList<Integer> rows, ArrayList<Integer> cols){
        for (int j = 0; j < this.board.length; j++){
            for (int k = 0; k < this.board[0].length; k++){
                if (!this.checkCollision(shape, j, k)){
                    rows.add(j);
                    cols.add(k);
                }
            }
        }
    }
    private boolean checkCollision(Shape shape, int r, int c){
        try{
            int nrows = shape.mtx.length;
            int ncols = shape.mtx[0].length;
            for(int i = 0; i < nrows; i++){
                for(int j = 0; j < ncols; j++){
                    if (shape.mtx[i][j] && this.board[r+i][c+j] != null){
                        return true;
                    }
                }
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            return true;
        }
    }

    private static Character[][] fillBoard(
        Character[][] boardState,
        Shape shape,
        int row,
        int col){
        Character[][] res = new Character[boardState.length][];
        for (int i = 0; i < res.length; i++){
            res[i] = boardState[i].clone();
        }
        for (int i = 0; i < shape.mtx.length; i++){
            for (int j = 0; j < shape.mtx[0].length; j++){
                if (shape.mtx[i][j]){
                    res[i+row][j+col] = shape.name;
                }
            }
        }
        return res;
    }

    public long getSolveTime(){
        return solveTime;
    }

    public long getNVisitedBranch(){
        return caseVisited;
    }
}
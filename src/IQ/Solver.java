// package IQ;
// import IQ.Input.GameMode;
// import java.util.ArrayList;

// /** IQ Puzzler Pro solver class. This class (hopefully) produces the correct solution,
//  * or a message "No solution" if there is none, of the puzzle it is given.
// */
// public class Solver {
//     /** The puzzle to be solved. */
//     public Input input;
//     /** The solution in string. If no solution is found, the value will be "No solution" */
//     public String solution;
//     /** Constructs the solver given a puzzle input. */
//     public Solver(Input input){
//         this.input = input;
//     }

//     private static void generatePossibilites(
//         Character[][] boardState,
//         Shape shape,
//         ArrayList<Integer> rows,
//         ArrayList<Integer> cols){
//         for (int j = 0; j < boardState.length; j++){
//             for (int k = 0; k < boardState[0].length; k++){
//                 if (!checkCollision(boardState, shape, j, k)){
//                     rows.add(j);
//                     cols.add(k);
//                 }
//             }
//         }
//     }

//     private static Character[][] fillBoard(
//         Character[][] boardState,
//         Shape shape,
//         int row,
//         int col){
//         Character[][] res = new Character[boardState.length][];
//         for (int i = 0; i < res.length; i++){
//             res[i] = boardState[i].clone();
//         }
//         for (int i = 0; i < shape.mtx.length; i++){
//             for (int j = 0; j < shape.mtx[0].length; j++){
//                 if (shape.mtx[i][j]){
//                     res[i+row][j+col] = shape.name;
//                 }
//             }
//         }
//         return res;
//     }

//     /** Solves the puzzle <em>brute-forcefully</em>. */
//     public void solve(){
//         if (this.input.gm == GameMode.DEFAULT){
//             Character[][] board = new Character[this.input.row][this.input.col];
//             this.solution = solveDefault(board, this.input.shapes);
//         }
//         if (this.solution == null) {
//             this.solution = "No solution";
//         }
//     }
    
//     private static String solveDefault(Character[][] boardState, ArrayList<Shape> shapes){
//         ArrayList<Integer> rows = new ArrayList<>();
//         ArrayList<Integer> cols = new ArrayList<>();
//         Shape head = shapes.remove(0);
//         for (int i = 0; i < 8; i++){
//             Shape currShape = head.rotation(i);
//             if (i >= 4){
//                 currShape = currShape.reflection();
//             }
//             generatePossibilites(boardState, currShape, rows, cols);
//             // System.out.println(rows);
//             // System.out.println(cols);
//             // System.out.println(currShape);
//             // System.out.println(boardToString(boardState));
//             for (int j = 0; j < rows.size(); j++){
//                 Character[][] currBoard =  fillBoard(boardState, currShape, rows.get(j), cols.get(j));
//                 if (checkWin(currBoard) && shapes.size() == 0){
//                     return boardToString(currBoard);
//                 }
//                 String res = solveDefault(currBoard, new ArrayList<Shape>(shapes));
//                 if (res != null){
//                     return res;
//                 }
//             }
//             rows.clear();
//             cols.clear();
//         }
//         return null;
//     }

//     private static String boardToString(Character[][] boardState){
//         String res = "";
//         for (int i = 0; i < boardState.length; i++){
//             for (int j = 0; j < boardState[0].length; j++){
//                 res += "\033[38;5;";
//                 res += (int)boardState[i][j] * 127 % 255;
//                 res += "m";
//                 res += boardState[i][j];
//                 // System.out.print("\033[0;32m");
//             }
//             res += '\n';
//         }
//         return res;
//     }
    
//     private static boolean checkCollision(Character[][] board, Shape shape, int r, int c){
//         try{
//             int nrows = shape.mtx.length;
//             int ncols = shape.mtx[0].length;
//             for(int i = 0; i < nrows; i++){
//                 for(int j = 0; j < ncols; j++){
//                     if (shape.mtx[i][j] && board[r+i][c+j] != null){
//                         return true;
//                     }
//                 }
//             }
//             return false;
//         } catch (ArrayIndexOutOfBoundsException e){
//             return true;
//         }
//     }
    
//     private static boolean checkWin(Character[][] board){
//         for (Character[] row : board){
//             for (Character cell : row){
//                 if (cell == null){
//                     return false;
//                 }
//             }
//         }
//         return true;
//     }
// }
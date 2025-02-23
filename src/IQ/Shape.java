package IQ;

import java.util.ArrayList;

/** Class representing a puzzle piece. */
public class Shape {
    /** Name of the puzzle piece, one character only. */
    public char name;
    /** The shape of the puzzle piece. */
    public boolean[][] mtx;
    
    /** Constructs a piece from given input.
     * 
     * @param name      name of the puzzle piece.
     * @param mtx       list of list of boolean representing.
     *                  the shape of the puzzle piece. Does not
     *                  need to form a <em>neat</em> matrix. e.g.
     *                  [[true], [true, false, true], [true, true, true]]
     *                  is a valid input.
    */
    public Shape(char name, ArrayList<ArrayList<Boolean>> mtx){
        this.name = name;
        int maxcols = 0;
        for (int i = 0; i < mtx.size(); i++){
            maxcols = mtx.get(i).size() > maxcols ? mtx.get(i).size() : maxcols;
        }
        this.mtx = new boolean[mtx.size()][maxcols];
        for (int i = 0; i < mtx.size(); i++){
            int rowsize = mtx.get(i).size();
            for (int j = 0; j < maxcols; j++){
                if (j < rowsize){
                    this.mtx[i][j] = mtx.get(i).get(j);
                } else {
                    this.mtx[i][j] = false;
                }
            }
        }
    }

    /** Constructs a piece trivially.
     * @param name  Name of the piece.
     * @param mtx   shape of the piece.
    */
    public Shape(char name, boolean[][] mtx){
        this.name = name;
        this.mtx = mtx;
    }

    /** Generates a NEW Shape, rotated n * 90 degrees clockwise.
     * 
     * @param n     number of 90 degrees clockwise rotation.
     * @return      new rotated shape.
    */
    public Shape rotation(int n){
        n %= 4;
        if (n == 0){
            boolean [][] m = new boolean[this.mtx.length][];
            for (int i = 0; i < m.length; i++){
                m[i] = this.mtx[i].clone();
            }
            return new Shape(this.name, m);
        }
        int r = this.mtx.length;
        int c =  this.mtx[0].length;
        boolean[][] res = n == 2 ? new boolean[r][c] : new boolean[c][r];
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (n == 1){
                    res[j][r-i-1] = this.mtx[i][j];
                }
                else if (n == 2){
                    res[r-i-1][c-j-1] = this.mtx[i][j];
                }
                else {
                    res[c-j-1][i] = this.mtx[i][j];
                }
            }
        }
        return new Shape(this.name, res);
    }

    /** Generates a NEW Shape, reflected around the vertical axis.
     * 
     * @return      new reflected shape.
    */
    public Shape reflection(){
        int r = this.mtx.length;
        int c =  this.mtx[0].length;
        boolean[][] res = new boolean[r][c];
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                res[i][c-j-1] = this.mtx[i][j];
            }
        }
        return new Shape(this.name, res);
    }

    /** Generate string representation of the puzzle piece.
     * @return  string representation of the puzzle piece.
    */
    public String toString(){
        String res = "\n";
        for (boolean[] row : this.mtx){
            for(boolean cell : row){
                res += cell ? this.name : ' ';
            }
            res += '\n';
        }
        return res;
    }
}
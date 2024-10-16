import java.util.Random;

public class Matrix {
    double[]data;
    int rows;
    int cols;
    double[][]res_mat;
    //...


    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows*cols];
        res_mat = new double[rows][cols];
    }
    //...

    Matrix(double[][] d){
        //liczba kolumn ma być ustalona na podstawie najdłuższego wiersza w d, brakujace elementy zerowe.
        this.rows = d.length;
        this.cols = 0;
        for (double[] row : d){
            if (row.length > this.cols){
                this.cols = row.length;
            }
        }
        this.res_mat = new double[rows][cols];
        this.data = new double[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < d[i].length; j++) {
                this.res_mat[i][j] = d[i][j];
            }
            for (int j = d[i].length; j < cols; j++) {
                this.res_mat[i][j] = 0.0;
            }
        }
        int index = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                data[index++] = res_mat[i][j];
            }
        }
    }

    double[][] asArray(){
        return this.res_mat;
    }

    double get(int c, int r){
        return this.res_mat[c][r];
    }

    void set(int row, int col, double value) {
        if (row >= 0 && row < this.res_mat.length && col >= 0 && col < this.res_mat[row].length) {
            this.res_mat[row][col] = value;
            this.data[row * this.cols + col] = value;
        } else {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[\n");
        for(int i=0;i<rows;i++){
            buf.append("[");
            for (int j = 0; j < cols; j++){
                buf.append(res_mat[i][j]);
                buf.append(" ,");
            }
            buf.append("]\n");
        }
        buf.append("]");
        return buf.toString();
    }

    void reshape(int newRows, int newCols) {
        if (rows * cols != newRows * newCols) {
            throw new RuntimeException("Rows and columns do not match");
        }
        double[][] new_mat = new double[newRows][newCols];
        int index = 0;
        for (int i = 0; i < newRows; i++) {
            for (int j = 0; j < newCols; j++) {
                new_mat[i][j] = data[index++];
            }
        }

        this.rows = newRows;
        this.cols = newCols;
        this.res_mat = new_mat;
        data = new double[newRows * newCols];
        index = 0;
        for (double[] row : res_mat) {
            for (double val : row) {
                data[index++] = val;
            }
        }
    }

    int[] shape(){
        int[] res = new int[2];
        res[0] = rows;
        res[1] = cols;
        return res;
    }

    Matrix add(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Cannot add matrices: dimensions do not match.");
        }
        double[][] resultData = new double[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) + m.get(i, j);
                assert(resultData[i][j] == this.get(i, j) + m.get(i, j)) : "Assertion failed at (" + i + ", " + j + ")";
            }
        }
        return new Matrix(resultData);
    }

    Matrix sub(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Cannot subtract matrices: dimensions do not match.");
        }

        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) - m.get(i, j);
                assert(resultData[i][j] == this.get(i, j) - m.get(i, j)) :
                        "Assertion failed at (" + i + ", " + j + ")";
            }
        }
        for (int i = 0; i < rows; i++){

        }

        return new Matrix(resultData);
    }

    Matrix mul(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Cannot subtract matrices: dimensions do not match.");
        }

        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) * m.get(i, j);
                assert(resultData[i][j] == this.get(i, j) * m.get(i, j)) :
                        "Assertion failed at (" + i + ", " + j + ")";
            }
        }

        return new Matrix(resultData);
    }


    Matrix div(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Cannot divide matrices: dimensions do not match.");
        }

        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) / m.get(i, j);
                assert(resultData[i][j] == this.get(i, j) / m.get(i, j)) :
                        "Assertion failed at (" + i + ", " + j + ")";
            }
        }

        return new Matrix(resultData);
    }

    Matrix add(double w) {
        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) + w;
            }
        }

        return new Matrix(resultData);
    }

    Matrix sub(double w) {
        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) - w;
            }
        }

        return new Matrix(resultData);
    }

    Matrix mul(double w) {
        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) * w;
            }
        }

        return new Matrix(resultData);
    }
    

    Matrix div(double w) {

        double[][] resultData = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                resultData[i][j] = this.get(i, j) / w;
            }
        }

        return new Matrix(resultData);
    }

    Matrix dot(Matrix m) {
        if (this.cols != m.rows) {
            throw new RuntimeException("Cannot multiply matrices: incompatible dimensions.");
        }

        double[][] resultData = new double[this.rows][m.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    resultData[i][j] += this.get(i, k) * m.get(k, j);
                }
            }
        }
        return new Matrix(resultData);
    }

    double frobenius(){
        double sum = 0.0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                sum += Math.pow(res_mat[i][j], 2);
            }
        }
        return Math.sqrt(sum);
    }

    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.set(i, j, r.nextDouble());
            }
        }
        return m;
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (i == j){
                    m.set(i, j, 1.0);
                }
            }
        }
        return m;
    }

    public double wyznacznik(){
        if (rows != cols){
            throw new RuntimeException("Matrix is not square");
        }
        if (rows == 1){
            return res_mat[0][0];
        }
        if (rows == 2){
            return res_mat[0][0] * res_mat[1][1] - res_mat[0][1] * res_mat[1][0];
        }
        for (int i = 0, j=0; i < cols; i++, j++){
            for (int r = i+1; r < rows; r++){
                double ratio = res_mat[r][j] / res_mat[i][j];
                for (int c = 0; c < cols; c++){
                    res_mat[r][c] -= ratio * res_mat[i][c];
                }
            }
        }
        double det = 1.0;
        for (int i = 0; i < rows; i++){
            det *= res_mat[i][i];
        }
        return det;
    }
}


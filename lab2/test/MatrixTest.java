import static org.junit.Assert.*;

public class MatrixTest {

    @org.junit.Test
    public void Matrix_params(){
        Matrix expected = new Matrix(2,3);
        assert expected.rows == 2;
        assert expected.cols == 3;
    }

    @org.junit.Test
    public void Matrix_from_array(){
        Matrix expected = new Matrix(new double[][]{{},{4,5,6}});
        for (int i = 0; i < expected.rows; i++){
            for (int j = 0; j < expected.cols; j++){
                if (i == 0){
                    assertEquals(expected.get(i,j), 0.0, 0.0);
                }else {
                    assertEquals(expected.get(i, j), (double) (i + j + 3), 0.0);
                }
            }
        }
        assert expected.rows == 2;
        assert expected.cols == 3;
    }

    @org.junit.Test
    public void asArray() {
        Matrix expected = new Matrix(2, 4);
        double[][] res = expected.asArray();
        assert res.length == 2;
        assert res[0].length == 4;
        assert res[1].length == 4;
    }

    @org.junit.Test
    public void get() {
        double[] row0 = {1,2,3};
        double[] row1 = {4,5,6};
        double[][] param = new double[][]{row0, row1};
        Matrix exp = new Matrix(param);
        for (int i = 0; i < row0.length; i++){
            assertEquals(exp.get(0,i), row0[i], 0.0);
        }
        for (int i = 0; i < row1.length; i++){
            assertEquals(exp.get(1,i), row1[i], 0.0);
        }
    }

    @org.junit.Test
    public void set() {
        double[] row0 = {1,2,3};
        double[] row1 = {4,5,6};
        double[][] param = new double[][]{row0, row1};
        Matrix exp = new Matrix(param);
        exp.set(0,0, 0.0);
        exp.set(1,2, 10.0);
        row0[0] = 0.0;
        row1[2] = 10.0;
        for (int i = 0; i < row0.length; i++){
            assertEquals(exp.get(0,i), row0[i], 0.0);
        }
        for (int i = 0; i < row1.length; i++){
            assertEquals(exp.get(1,i), row1[i], 0.0);
        }
    }

    @org.junit.Test
    public void ToString() {
        Matrix exp = new Matrix(new double[][]{{1,2},{3,4}});
        int przecinki = 0;
        int nawiasy = 0;
        for (int i = 0; i < exp.toString().length(); i++){
            if (exp.toString().charAt(i) == ','){
                przecinki++;
            } else if (exp.toString().charAt(i) == '[' || exp.toString().charAt(i) == ']'){
                nawiasy++;
            }
        }
        assert przecinki == exp.cols * exp.rows;
        assert nawiasy == 2 + 2*exp.rows;
    }

    @org.junit.Test
    public void reshape() {
        Matrix exp = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        double[] row0 = {1,2};
        double[] row1 = {3,4};
        double[] row2 = {5,6};
        exp.reshape(3,2);
        assert exp.rows == 3;
        assert exp.cols == 2;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < row0.length; j++) {
                if (i == 0) {
                    assertEquals(exp.get(i, j), row0[j], 0.0);
                } else if (i == 1) {
                    assertEquals(exp.get(i, j), row1[j], 0.0);
                } else {
                    assertEquals(exp.get(i, j), row2[j], 0.0);
                }
            }
        }
    }

    @org.junit.Test
    public void shape() {
        Matrix exp = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        int[] res = exp.shape();
        assert res[0] == 2;
        assert res[1] == 3;
    }

    @org.junit.Test
    public void add_matrices() {
        Matrix exp1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix exp2 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = exp1.add(exp2);
        for (int i = 0; i < exp1.rows; i++){
            for (int j = 0; j < exp1.cols; j++){
                assertEquals(res.get(i,j), exp1.get(i,j) + exp2.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void sub_matrices() {
        Matrix mat1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix mat2 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = mat1.sub(mat2);
        for (int i = 0; i < res.rows; i++){
            for (int j = 0; j < res.cols; j++){
                assertEquals(0.0, res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void mul_matrices_elem_wise() {
        Matrix mat1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix mat2 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        double[] exp = {1,4,9,16,25,36};
        Matrix res = mat1.mul(mat2);
        int idx = 0;
        for (int i = 0; i < mat1.rows; i++){
            for (int j = 0; j < mat1.cols; j++){
                assertEquals(exp[idx++], res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void div_matrices() {
        Matrix mat1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix mat2 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = mat1.div(mat2);
        int idx = 0;
        for (int i = 0; i < mat1.rows; i++){
            for (int j = 0; j < mat1.cols; j++){
                assertEquals(1.0, res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void testAdd_const() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = matrix.add(1.0);
        for (int i = 0; i < matrix.rows; i++){
            for (int j = 0; j < matrix.cols; j++){
                assertEquals(matrix.get(i,j) + 1.0, res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void testSub_const() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = matrix.sub(1.0);
        for (int i = 0; i < matrix.rows; i++){
            for (int j = 0; j < matrix.cols; j++){
                assertEquals(matrix.get(i,j) - 1.0, res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void testMul_const() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = matrix.mul(2.0);
        for (int i = 0; i < matrix.rows; i++){
            for (int j = 0; j < matrix.cols; j++){
                assertEquals(matrix.get(i,j) * 2.0, res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void testDiv_const() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix res = matrix.div(2.0);
        for (int i = 0; i < matrix.rows; i++){
            for (int j = 0; j < matrix.cols; j++){
                assertEquals(matrix.get(i,j) / 2.0, res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void dot_matrices() {
        Matrix mat1 = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        Matrix mat2 = new Matrix(new double[][]{{1,2},{3,4},{5,6}});
        Matrix res = mat1.dot(mat2);
        double[][] exp = new double[][]{{22,28},{49,64}};
        for (int i = 0; i < res.rows; i++){
            for (int j = 0; j < res.cols; j++){
                assertEquals(exp[i][j], res.get(i,j), 0.0);
            }
        }
    }

    @org.junit.Test
    public void frobenius() {
        Matrix mat = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(Math.sqrt(91), mat.frobenius(), 0.0);
    }

    @org.junit.Test
    public void random() {
        Matrix mat = Matrix.random(2,3);
        assert mat.rows == 2;
        assert mat.cols == 3;
        for (int i = 0; i < mat.rows; i++){
            for (int j = 0; j < mat.cols; j++){
                assert mat.get(i,j) >= 0.0;
                assert mat.get(i,j) <= 1.0;
            }
        }
    }

    @org.junit.Test
    public void eye() {
        Matrix mat = Matrix.eye(3);
        assert mat.rows == 3;
        assert mat.cols == 3;
        for (int i = 0; i < mat.rows; i++){
            for (int j = 0; j < mat.cols; j++) {
                if (i == j) {
                    assertEquals(mat.get(i, j), 1.0, 0.0);
                } else {
                    assertEquals(mat.get(i, j), 0.0, 0.0);
                }
            }
        }
    }
}
import java.lang.reflect.Array;

public class matrixDemo {
    public static void main(String args[]) {
        int[][] matrix = {
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
        };

        int[][] matrix2 = {
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
        };

        int[][] matrix3 = classicalMultiplication(matrix, matrix2);
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                System.out.println(matrix3[row][col]);
            }
            System.out.println();
        }

    }

    public static int[][] classicalMultiplication(int m1[][], int m2[][]){

        int size = m1.length;
        int[][] result = new int[size][size];


        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                result[row][col] = m1[row][col]*m2[row][col];
                // System.out.println(result[row][col]);
            }
        }


        return result;
    }

    public static int[][] naiveMultiplication(int m1[][], int m2[][]){
        return null;
    }

    public static int[][] strassenMultiplication(int m1[][], int m2[][]){
        return null;
    }
}
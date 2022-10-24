import java.util.Arrays;

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
            {1, 2, 3},
        };

        int[][] matrix3 = classicalMultiplication(matrix, matrix2);
        for(int row = 0; row < matrix3.length; row++)
        {
            for(int col = 0; col < matrix3.length; col++)
            {
                System.out.println(matrix3[row][col]);
            }
            System.out.println();
        }

        matrix3 = naiveMultiplication(matrix, matrix2);
        for(int row = 0; row < matrix3.length; row++)
        {
            for(int col = 0; col < matrix3.length; col++)
            {
                System.out.println(matrix3[row][col]);
            }
            System.out.println();
        }

    }

    // all methods assume n is a power of 2
    public static int[][] classicalMultiplication(int m1[][], int m2[][]){

        int size = m1.length;
        int[][] result = new int[size][size];


        for(int row = 0; row < size; row++)
        {
            for(int col = 0; col < size; col++)
            {
                int multSum = 0;
                for(int index = 0; index < size; index++)
                {
                    multSum += m1[row][index]*m2[index][col];
                }
                // System.out.println(result[row][col]);
                result[row][col] = multSum;
            }
        }


        return result;
    }

    public static int[][] naiveMultiplication(int m1[][], int m2[][]){

        int size = m1.length;
        int[][] result = new int[size][size];
        int middle = size/2;

        int[][] tl;
        int[][] tr;
        int[][] bl;
        int[][] br;
        // new int[middle][middle];

        for(int row = 0; row < middle; row++)
        {
            // tl = Arrays.copyOfRange(, row, row)
        }
        // int[] newArray = Arrays.copyOfRange(oldArray, startIndex, endIndex);
        

        return result;
    }

    public static int[][] strassenMultiplication(int m1[][], int m2[][]){
        return null;
    }
}
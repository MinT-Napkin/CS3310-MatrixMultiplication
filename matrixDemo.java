import java.lang.Math;
import java.util.Arrays;

public class matrixDemo {
    // private static int counter = 0;
    public static void main(String args[]) {
        int[][] matrix = {
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
        };

        int[][] matrix2 = {
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4},
        };

        int[][] matrix3 = {
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
        };

        int[][] matrix4 = {
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
        };

        int[][] resultMatrix = classicalMultiplication(matrix, matrix2);
        System.out.println("Classical Result: ");
        for(int row = 0; row < resultMatrix.length; row++)
        {
            for(int col = 0; col < resultMatrix.length; col++)
            {
                System.out.print(resultMatrix[row][col] + " ");
            }
            System.out.println();
        }
        
        resultMatrix = naiveMultiplication(matrix, matrix2);
        System.out.println();

        System.out.println("Naive Result: ");
        for(int row = 0; row < resultMatrix.length; row++)
        {
            for(int col = 0; col < resultMatrix.length; col++)
            {
                System.out.print(resultMatrix[row][col] + " ");
            }
            System.out.println();
        }
        
        // System.out.println("\nCounter: " + counter);

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

        if(m1.length == 1 || m2.length == 1)
        {
            int[][] r = new int[1][1];
            r[0][0] = m1[0][0]*m2[0][0]; 
            return r;
        }

        int size = m1.length;
        int[][] result = new int[size][size];
        int middle = (int) Math.floor(size/2);

        // splitting into 4 quadrants
        int[][] a1 = new int[middle][middle];
        int[][] a2 = new int[middle][middle];
        int[][] a3 = new int[middle][middle];
        int[][] a4 = new int[middle][middle];

        int[][] b1 = new int[middle][middle];
        int[][] b2 = new int[middle][middle];
        int[][] b3 = new int[middle][middle];
        int[][] b4 = new int[middle][middle];

        int[][] c1 = new int[middle][middle];
        int[][] c2 = new int[middle][middle];
        int[][] c3 = new int[middle][middle];
        int[][] c4 = new int[middle][middle];

        for(int row = 0; row < middle; row++)
        {
            a1[row] = Arrays.copyOfRange(m1[row], 0, middle);
            a2[row] = Arrays.copyOfRange(m1[row], middle, size);
            a3[row] = Arrays.copyOfRange(m1[row+middle], 0, middle);
            a4[row] = Arrays.copyOfRange(m1[row+middle], middle, size);

            b1[row] = Arrays.copyOfRange(m2[row], 0, middle);
            b2[row] = Arrays.copyOfRange(m2[row], middle, size);
            b3[row] = Arrays.copyOfRange(m2[row+middle], 0, middle);
            b4[row] = Arrays.copyOfRange(m2[row+middle], middle, size);
        }

        // counter++;
        for(int row = 0; row < middle; row++)
        {
            for(int col = 0; col < middle; col++)
            {
                c1[row][col] = naiveMultiplication(a1, b1)[row][col] + naiveMultiplication(a2, b3)[row][col];  
                c2[row][col] = naiveMultiplication(a1, b2)[row][col] + naiveMultiplication(a2, b4)[row][col]; 
                c3[row][col] = naiveMultiplication(a3, b1)[row][col] + naiveMultiplication(a4, b3)[row][col]; 
                c4[row][col] = naiveMultiplication(a3, b2)[row][col] + naiveMultiplication(a4, b4)[row][col];              
            }
        }

        for(int row = 0; row < middle; row++)
        {
            for(int col = 0; col < middle; col++)
            {
                result[row][col] = c1[row][col];
                result[row][col + middle] = c2[row][col];
                result[row + middle][col] = c3[row][col];
                result[row + middle][col + middle] = c4[row][col];        
            }
        }

        // System.out.println("Result: " + counter);
        // for(int row = 0; row < result.length; row++)
        // {
        //     for(int col = 0; col < result[0].length; col++)
        //     {
        //         System.out.print(result[row][col] + " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();

        return result;
    }

    public static int[][] strassenMultiplication(int m1[][], int m2[][]){
        return null;
    }
}
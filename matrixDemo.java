import java.lang.Math;
import java.util.Arrays;

import javax.naming.spi.DirStateFactory.Result;

public class matrixDemo {
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
        // for(int row = 0; row < matrix3.length; row++)
        // {
        //     for(int col = 0; col < matrix3.length; col++)
        //     {
        //         System.out.println(matrix3[row][col]);
        //     }
        //     System.out.println();
        // }

        resultMatrix = naiveMultiplication(matrix, matrix2);
        // for(int row = 0; row < matrix3.length; row++)
        // {
        //     for(int col = 0; col < matrix3.length; col++)
        //     {
        //         System.out.println(matrix3[row][col]);
        //     }
        //     System.out.println();
        // }

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
        System.out.println("Quadrant 1:");
        for(int row = 0; row < a1.length; row++)
        {
            for(int col = 0; col < a1[0].length; col++)
            {
                System.out.print(a1[row][col]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Quadrant 2:");
        for(int row = 0; row < a2.length; row++)
        {
            for(int col = 0; col < a2[0].length; col++)
            {
                System.out.print(a2[row][col]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Quadrant 3:");
        for(int row = 0; row < a3.length; row++)
        {
            for(int col = 0; col < a3[0].length; col++)
            {
                System.out.print(a3[row][col]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Quadrant 4:");
        for(int row = 0; row < a4.length; row++)
        {
            for(int col = 0; col < a4[0].length; col++)
            {
                System.out.print(a4[row][col]);
            }
            System.out.println();
        }

        return result;
    }

    public static int[][] strassenMultiplication(int m1[][], int m2[][]){
        return null;
    }
}
import java.lang.Math;
import java.util.Arrays;
import java.util.Random;

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
        
        // System.out.println("That took " + (endTime - startTime) + " milliseconds");
        
        long startTime = System.nanoTime();
        int[][] resultMatrix = classicalMultiplication(matrix3, matrix4);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Classical Result (Execution Time: " + (elapsedTime) + " nanoseconds)");

        for(int row = 0; row < resultMatrix.length; row++)
        {
            for(int col = 0; col < resultMatrix.length; col++)
            {
                System.out.print(resultMatrix[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        startTime = System.nanoTime();
        resultMatrix = naiveMultiplication(matrix3, matrix4, 3);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Naive Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        for(int row = 0; row < resultMatrix.length; row++)
        {
            for(int col = 0; col < resultMatrix.length; col++)
            {
                System.out.print(resultMatrix[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
        // System.out.println("\nCounter: " + counter);
        
        // Testing random matrixes generation
        for(int i = 0; i < 1; i++)
        {
            System.out.println("Random 2D Matrix: ");
            int[][] rArr = populateRandom2DMatrix(10, 10);
    
            for(int row = 0; row < rArr.length; row++)
            {
                for(int col = 0; col < rArr.length; col++)
                {
                    System.out.print(rArr[row][col] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        
        // Testing extendMatrix and trimMatrix methods
        System.out.println("Before Extension: " + matrix3.length);
        System.out.println("After Extension: " + extendMatrix(matrix3).length);

        int[][] test = extendMatrix(matrix3);

        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[0].length; j++) {
                System.out.print(test[i][j]);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Before Trim: " + test.length);
        System.out.println("After Trim: " + trimMatrix(test).length);

        int[][] test2 = trimMatrix(test);
        for (int i = 0; i < test2.length; i++) {
            for (int j = 0; j < test2[0].length; j++) {
                System.out.print(test2[i][j]);
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

    public static int[][] naiveMultiplication(int a_m1[][], int a_m2[][], int a_originalSize){

        if(a_m1.length == 1 || a_m2.length == 1)
        {
            int[][] r = new int[1][1];
            r[0][0] = a_m1[0][0]*a_m2[0][0]; 
            return r;
        }

        int[][] m1 = a_m1;
        int[][] m2 = a_m2;
        int originalSize = a_originalSize;

        if(!isPowerOfTwo(a_m1.length) || !isPowerOfTwo(a_m2.length))
        {
            m1 = extendMatrix(a_m1);
            m2 = extendMatrix(a_m2);
            originalSize = m1.length;
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
                c1[row][col] = naiveMultiplication(a1, b1, originalSize)[row][col] + naiveMultiplication(a2, b3, originalSize)[row][col];  
                c2[row][col] = naiveMultiplication(a1, b2, originalSize)[row][col] + naiveMultiplication(a2, b4, originalSize)[row][col]; 
                c3[row][col] = naiveMultiplication(a3, b1, originalSize)[row][col] + naiveMultiplication(a4, b3, originalSize)[row][col]; 
                c4[row][col] = naiveMultiplication(a3, b2, originalSize)[row][col] + naiveMultiplication(a4, b4, originalSize)[row][col];              
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

        int[][] finalResult = result;

        if(result.length == originalSize)
        {
            finalResult = trimMatrix(result);
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

        return finalResult;
    }

    public static int[][] strassenMultiplication(int m1[][], int m2[][]){
        return null;
    }

    // For testing purposes
    public static int[][] populateRandom2DMatrix(int n1, int n2)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(19) - 9;

        int[][] result = new int[n1][n2];

        for (int row = 0; row < result.length; row++) {
            for(int col = 0; col < result.length; col++)
            {
                result [row][col] = randNum;
                randNum = rand.nextInt(19) - 9;
            }
        }

        return result;
    }

    public static int[][] extendMatrix(int[][] matrix)
    {
        int size = matrix.length;

        if(isPowerOfTwo(size))
        {
            return matrix;
        }

        while(!isPowerOfTwo(size))
        {
            size++;
        }

        int[][] result = new int[size][size];

        // Only fills in the available data and the rest are 0's
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j];
            }
        }

        return result;
    }

    public static int[][] trimMatrix(int[][] matrix)
    {
        int size = matrix.length;
        boolean isExtended = false;

        // Detects a rows of 0's, which is the point of extension
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] != 0)
                {
                    j = matrix.length;
                }
                else if (j == matrix.length-1)
                {
                    size = i;
                    isExtended = true;
                }
            }
        }

        if(!isExtended)
        {
            return matrix;
        }

        int[][] result = new int[size][size];
        
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = matrix[i][j];
            }
        }

        return result;
    }

    /* Checks if the number is a power of 2 */
    public static boolean isPowerOfTwo(int n)
    {
        return (n != 0) && ((n & (n - 1)) == 0);
    }
     
}
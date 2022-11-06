import java.lang.Math;
import java.util.Arrays;
import java.util.Random;


public class matrixDemo {
    public static void main(String args[]) {

        /*
         * SANITY CHECK DONE HERE
         */
        int[][] matrix1 = {
            {2, 0, -1, 6},
            {3, 7, 8, 0},
            {-5, 1, 6, -2},
            {8, 0, 1, 7},
        };

        int[][] matrix2 = {
            {0, 1, 6, 3},
            {-2, 8, 7, 1},
            {2, 0, -1, 0},
            {9, 1, 6, -2},
        };

        System.out.println("First Matrix:");
        printMatrix(matrix1);

        System.out.println("Second Matrix:");
        printMatrix(matrix2);

        long startTime = System.nanoTime();
        int[][] resultMatrix = classicalMultiplication(matrix1, matrix2);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Classical Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);
        
        startTime = System.nanoTime();
        resultMatrix = trimMatrix(naiveMultiplication(matrix1, matrix2));
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        matrix1 = trimMatrix(matrix1);
        matrix2 = trimMatrix(matrix2);

        System.out.println("Naive DC Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

        matrix1 = extendMatrix(matrix1);
        matrix2 = extendMatrix(matrix2);
        startTime = System.nanoTime();
        resultMatrix = trimMatrix(r_strassenMultiply(matrix1, matrix2));
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        matrix1 = trimMatrix(matrix1);
        matrix2 = trimMatrix(matrix2);

        System.out.println("Strassen Algorithm Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);
        
        // System.out.println("That took " + (endTime - startTime) + " milliseconds");
        startTime = System.nanoTime();
        resultMatrix = classicalMultiplication(matrix1, matrix2);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        /*
         * TESTING MATRICES THAT ARE OF SIZE THAT AREN'T POWER OF 2
         */
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
        
        System.out.println("First Matrix:");
        printMatrix(matrix3);
        System.out.println("Second Matrix:");
        printMatrix(matrix4);

        startTime = System.nanoTime();
        resultMatrix = classicalMultiplication(matrix3, matrix4);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Classical Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);
        
        startTime = System.nanoTime();
        resultMatrix = trimMatrix(naiveMultiplication(matrix3, matrix4));
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Naive DC Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

        startTime = System.nanoTime();
        resultMatrix = trimMatrix(strassenMultiply(matrix3, matrix4));
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Strassen Algorithm Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

        /*
         * TESTING RUNTIME OF ALGORITHMS HERE
         */

        int sizeN = 10;
        int[][] randomMatrix = populateRandom2DMatrix(sizeN, sizeN);

        // printMatrix(randomMatrix);
    }

    /*
     * REQUIRED FUNCTIONS
     */

    /* Classical Matrix Multiplication (Minh) */
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

    /* Naive Divide and Conquer Matrix Multiplication (Minh) */
    public static int[][] naiveMultiplication(int A[][], int B[][])
    {
        int[][] a = A;
        int[][] b = B;

        if(!isPowerOfTwo(A.length) || !isPowerOfTwo(B.length))
        {
            a = extendMatrix(a);
            b = extendMatrix(b);
        }
        
        int[][] result = trimMatrix(r_naiveMultiplication(a, b));

        return result;
    }

    public static int[][] r_naiveMultiplication(int a_m1[][], int a_m2[][]){

        if(a_m1.length == 1 || a_m2.length == 1)
        {
            int[][] r = new int[1][1];
            r[0][0] = a_m1[0][0]*a_m2[0][0]; 
            return r;
        }

        int[][] m1 = a_m1;
        int[][] m2 = a_m2;

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

        for(int row = 0; row < middle; row++)
        {
            for(int col = 0; col < middle; col++)
            {
                c1[row][col] = r_naiveMultiplication(a1, b1)[row][col] + r_naiveMultiplication(a2, b3)[row][col];  
                c2[row][col] = r_naiveMultiplication(a1, b2)[row][col] + r_naiveMultiplication(a2, b4)[row][col]; 
                c3[row][col] = r_naiveMultiplication(a3, b1)[row][col] + r_naiveMultiplication(a4, b3)[row][col]; 
                c4[row][col] = r_naiveMultiplication(a3, b2)[row][col] + r_naiveMultiplication(a4, b4)[row][col];              
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

        return result;
    }

    private static int[][] strassenMultiply(int[][] A, int[][] B)
    {
        int[][] a = A;
        int[][] b = B;

        if(!isPowerOfTwo(A.length) || !isPowerOfTwo(B.length))
        {
            a = extendMatrix(a);
            b = extendMatrix(b);
        }
        
        int[][] result = trimMatrix(r_strassenMultiply(a, b));

        return result;
    }

    /* Strassen's Algorithm to Matrix Multiplication (Priyanshu) */
    private static int[][] r_strassenMultiply(int[][] A, int[][] B) {

        int n = A.length;

        int[][] result = new int[n][n];

        // if the input matrix is 1x1
        if (n == 1) {
            result[0][0] = A[0][0] * B[0][0];
        } else {

            // first matrix
            int[][] a = new int[n / 2][n / 2];
            int[][] b = new int[n / 2][n / 2];
            int[][] c = new int[n / 2][n / 2];
            int[][] d = new int[n / 2][n / 2];

            // second matrix
            int[][] e = new int[n / 2][n / 2];
            int[][] f = new int[n / 2][n / 2];
            int[][] g = new int[n / 2][n / 2];
            int[][] h = new int[n / 2][n / 2];

            // dividing matrix A into 4 parts
            divideArray(A, a, 0, 0);
            divideArray(A, b, 0, n / 2);
            divideArray(A, c, n / 2, 0);
            divideArray(A, d, n / 2, n / 2);

            // dividing matrix B into 4 parts
            divideArray(B, e, 0, 0);
            divideArray(B, f, 0, n / 2);
            divideArray(B, g, n / 2, 0);
            divideArray(B, h, n / 2, n / 2);

            /**
             p1 = (a + d)(e + h)
             p2 = (c + d)e
             p3 = a(f - h)
             p4 = d(g - e)
             p5 = (a + b)h
             p6 = (c - a) (e + f)
             p7 = (b - d) (g + h)
             **/

            int[][] p1 = r_strassenMultiply(addMatrices(a, d), addMatrices(e, h));
            int[][] p2 = r_strassenMultiply(addMatrices(c,d),e);
            int[][] p3 = r_strassenMultiply(a, subMatrices(f, h));
            int[][] p4 = r_strassenMultiply(d, subMatrices(g, e));
            int[][] p5 = r_strassenMultiply(addMatrices(a,b), h);
            int[][] p6 = r_strassenMultiply(subMatrices(c, a), addMatrices(e, f));
            int[][] p7 = r_strassenMultiply(subMatrices(b, d), addMatrices(g, h));


            /**
             C11 = p1 + p4 - p5 + p7
             C12 = p3 + p5
             C21 = p2 + p4
             C22 = p1 - p2 + p3 + p6
             **/

            int[][] C11 = addMatrices(subMatrices(addMatrices(p1, p4), p5), p7);
            int[][] C12 = addMatrices(p3, p5);
            int[][] C21 = addMatrices(p2, p4);
            int[][] C22 = addMatrices(subMatrices(addMatrices(p1, p3), p2), p6);

            // adding all subarray back into one
            copySubArray(C11, result, 0, 0);
            copySubArray(C12, result, 0, n / 2);
            copySubArray(C21, result, n / 2, 0);
            copySubArray(C22, result, n / 2, n / 2);
        }
        return result;
    }

    /* Helper functions for Strassen Algorithm */
    // Adding 2 matrices
    public static int[][] addMatrices(int[][] a, int[][] b) {
        int n = a.length;

        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    // Subtracting 2 matrices
    public static int[][] subMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    // divides array
    public static void divideArray(int[][] P, int[][] C, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    // copies
    public static void copySubArray(int[][] C, int[][] P, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }

    /*
     * TESTING FUNCTIONS
     */
    public static void printMatrix(int [][] matrix)
    {
        for(int row = 0; row < matrix.length; row++)
        {
            for(int col = 0; col < matrix.length; col++)
            {
                System.out.printf("%3d ", matrix[row][col]);
            }
            System.out.println();
        }

        System.out.println();
    }

    public static int[][] populateRandom2DMatrix(int n1, int n2)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(19) - 9;

        int[][] result = new int[n1][n2];

        for (int row = 0; row < result.length; row++) {
            for(int col = 0; col < result.length; col++)
            {
                if(randNum == 0)
                {
                    col--;
                }
                else
                {
                    result [row][col] = randNum;
                }
                randNum = rand.nextInt(19) - 9;
            }
        }

        return result;
    }

    /*
     * EXTRA CREDIT HELPER FUNCTIONS
     */

    /* Extends any matrix of size [n] into a power of 2,
     * filling in the extended portions of the matrix
     * with 0s
     */
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

    /* Trims down any matrix of size [n] into its original size,
     * only a returning the portion of the matrix that isn't
     * filled with 0s
     */
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
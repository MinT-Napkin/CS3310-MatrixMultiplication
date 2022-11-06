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
        resultMatrix = naiveMultiplication(matrix1, matrix2);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Naive DC Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

        startTime = System.nanoTime();
        resultMatrix = r_strassenMultiply(matrix1, matrix2);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Strassen Algorithm Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

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
        resultMatrix = naiveMultiplication(matrix3, matrix4);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Naive DC Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

        startTime = System.nanoTime();
        resultMatrix = strassenMultiply(matrix3, matrix4);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        System.out.println("Strassen Algorithm Matrix Result: (Execution Time: " + (elapsedTime) + " nanoseconds)");
        printMatrix(resultMatrix);

        /*
         * TESTING EMPIRICAL RUNTIME OF ALGORITHMS HERE
         */

        final int sizeN = 4;
        int[][] randomMatrix1 = populateRandom2DMatrix(sizeN, sizeN);
        int[][] randomMatrix2 = populateRandom2DMatrix(sizeN, sizeN);

        System.out.println("TESTING RUNTIME : Random Matrices of Size " + sizeN + "\n");

        final long initialAttempts = 10;
        long attempts = initialAttempts;

        long classicalAvg = 0;
        long naiveAvg = 0;
        long strassenAvg = 0;
        
        System.out.println("-----BEGINNING TEST-----\n");
        while(attempts > 0)
        {
            System.out.print(attempts + " ");
            attempts--;
            startTime = System.nanoTime();
            classicalMultiplication(randomMatrix1, randomMatrix2);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            classicalAvg += elapsedTime;

            System.out.print("Classical complete . . . ");

            startTime = System.nanoTime();
            naiveMultiplication(randomMatrix1, randomMatrix2);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            naiveAvg += elapsedTime;

            System.out.print("Naive complete . . . ");

            startTime = System.nanoTime();
            strassenMultiply(randomMatrix1, randomMatrix2);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            strassenAvg += elapsedTime;

            System.out.print("Strassen complete . . . \n");
        }
        System.out.println("\n-----END TEST-----\n");
        
        classicalAvg /= initialAttempts;
        naiveAvg /= initialAttempts;
        strassenAvg /= initialAttempts;

        System.out.printf("Classical Matrix Multipliation Average Time (Size %d): %d nanosecs\n", sizeN, classicalAvg);
        System.out.printf("Naive DC Matrix Multipliation Average Time (Size %d): %d nanosecs\n", sizeN, naiveAvg);
        System.out.printf("Strassen Matrix Multipliation Average Time (Size %d): %d nanosecs\n", sizeN, strassenAvg);
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

    public static int[][] r_naiveMultiplication(int m1[][], int m2[][]){

        if(m1.length == 1 || m2.length == 1)
        {
            int[][] r = new int[1][1];
            r[0][0] = m1[0][0]*m2[0][0]; 
            return r;
        }

        int size = m1.length;
        int[][] result = new int[size][size];
        int middle = size/2;

        // first matrix
        int[][] a1 = new int[middle][middle];
        int[][] a2 = new int[middle][middle];
        int[][] a3 = new int[middle][middle];
        int[][] a4 = new int[middle][middle];

        // second matrix
        int[][] b1 = new int[middle][middle];
        int[][] b2 = new int[middle][middle];
        int[][] b3 = new int[middle][middle];
        int[][] b4 = new int[middle][middle];

        // result matrix
        int[][] c1 = new int[middle][middle];
        int[][] c2 = new int[middle][middle];
        int[][] c3 = new int[middle][middle];
        int[][] c4 = new int[middle][middle];

        // dividing matrix A into 4 parts
        divideArray(m1, a1, 0, 0);
        divideArray(m1, a2, 0, size / 2);
        divideArray(m1, a3, size / 2, 0);
        divideArray(m1, a4, size / 2, size / 2);

        // dividing matrix B into 4 parts
        divideArray(m2, b1, 0, 0);
        divideArray(m2, b2, 0, size / 2);
        divideArray(m2, b3, size / 2, 0);
        divideArray(m2, b4, size / 2, size / 2);

        /*
         * 
         */
        c1 = addMatrices(r_naiveMultiplication(a1, b1), r_naiveMultiplication(a2, b3));
        c2 = addMatrices(r_naiveMultiplication(a1, b2), r_naiveMultiplication(a2, b4));
        c3 = addMatrices(r_naiveMultiplication(a3, b1), r_naiveMultiplication(a4, b3));
        c4 = addMatrices(r_naiveMultiplication(a3, b2), r_naiveMultiplication(a4, b4));

        // adding all subarray back into one
        copySubArray(c1, result, 0, 0);
        copySubArray(c2, result, 0, size / 2);
        copySubArray(c3, result, size / 2, 0);
        copySubArray(c4, result, size / 2, size / 2);
        
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

    /* Helper functions for Naive DC algorithm and Strassen algorithm */
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

    // prints out a matrix
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

    // returns a 2d matrix with random numbers of specified size
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
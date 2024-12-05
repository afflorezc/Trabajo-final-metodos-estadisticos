using System;

namespace MatrixProductNamespace
{
    public class MatrixProductMemory
    {
        public static void InMemoryProduct(int matrixSize)
        {
            // Permitir que OpenBLAS utilice un solo hilo
            MatrixProduct.openblas_set_num_threads(1);
            
            double[] matrixA = GenerateRandomMatrix(matrixSize, matrixSize);
            double[] matrixB = GenerateRandomMatrix(matrixSize, matrixSize);

            long startTime = DateTime.Now.Ticks;

            double[] matrixC = new double[matrixSize * matrixSize];

            // Uso de OpenBLAS para multiplicar las matrices
            MatrixProduct.cblas_dgemm(101, 111, 111,
                matrixSize, matrixSize, matrixSize,
                1.0, matrixA, matrixSize,
                matrixB, matrixSize,
                0.0, matrixC, matrixSize);

            long endTime = DateTime.Now.Ticks;

            Console.WriteLine("Product completed. First 10 columns in first row:");
            for (int i = 0; i < 10; i++)
            {
                Console.Write(i < 9
                    ? $" {matrixC[i]:F2} , "
                    : $" {matrixC[i]:F2} \n");
            }

            Console.WriteLine($"Product completed in {(endTime - startTime) / 10000000.0:F2} seconds.");
        }

        public static double[] GenerateRandomMatrix(int rows, int cols)
        {
            Random rand = new Random();
            double[] matrix = new double[rows * cols];
            for (int i = 0; i < rows * cols; i++)
            {
                matrix[i] = rand.NextDouble();
            }
            return matrix;
        }
    }
}
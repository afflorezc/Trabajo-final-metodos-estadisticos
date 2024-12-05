using System;
using System.Threading.Tasks;

namespace MatrixProductNamespace
{
    public class MatrixProductMemParallel
    {
        public static void ParallelProduct(int matrixSize)
        {
            // Establecer el número de hilos de OpenBLAS
            MatrixProduct.openblas_set_num_threads(Environment.ProcessorCount);

            double[] MatrixA = MatrixProductMemory.GenerateRandomMatrix(matrixSize, matrixSize);
            double[] MatrixB = MatrixProductMemory.GenerateRandomMatrix(matrixSize, matrixSize);

            long startTime = DateTime.Now.Ticks;

            double[] MatrixC = new double[matrixSize * matrixSize];

            int numThreads = Environment.ProcessorCount;
            int blockSize = (matrixSize + numThreads - 1) / numThreads;

            Parallel.For(0, numThreads, i =>
            {
                int startRow = i * blockSize;
                int endRow = Math.Min(startRow + blockSize, matrixSize);
                int rowCount = endRow - startRow;

                double[] subMatrixA = new double[rowCount * matrixSize];
                double[] subMatrixC = new double[rowCount * matrixSize];

                Array.Copy(MatrixA, startRow * matrixSize, subMatrixA, 0, rowCount * matrixSize);

                MatrixProduct.cblas_dgemm(101, 111, 111,
                    rowCount, matrixSize, matrixSize,
                    1.0, subMatrixA, matrixSize,
                    MatrixB, matrixSize,
                    0.0, subMatrixC, matrixSize);

                Array.Copy(subMatrixC, 0, MatrixC, startRow * matrixSize, rowCount * matrixSize);
            });

            long endTime = DateTime.Now.Ticks;

            Console.WriteLine("Product completed. First 10 columns in first row:");
            for (int i = 0; i < 10; i++)
            {
                Console.Write(i < 9
                    ? $" {MatrixC[i]:F2} , "
                    : $" {MatrixC[i]:F2} \n");
            }
            Console.WriteLine($"Product completed in {(endTime - startTime) / 10000000.0:F2} seconds.");
        }
    }
}
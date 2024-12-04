using System;
using System.Runtime.InteropServices;
using System.IO;

namespace MatrixProductNamespace
{
    public class MatrixProduct
    {
        [DllImport("libopenblas.dll", CallingConvention = CallingConvention.Cdecl)]
        public static extern void cblas_dgemm(
            int Order, int TransA, int TransB,
            int M, int N, int K,
            double alpha, double[] A, int lda,
            double[] B, int ldb,
            double beta, double[] C, int ldc);

        // Importar la función para establecer el número de hilos en OpenBLAS
        [DllImport("libopenblas.dll", CallingConvention = CallingConvention.Cdecl)]
        public static extern void openblas_set_num_threads(int numThreads);

        public static int ReadInt(string message, string errorMessage, string inErrorMessage)
        {
            while (true)
            {
                Console.WriteLine(message + ": ");
                Console.Write("Enter a value: ");
                if (int.TryParse(Console.ReadLine(), out int value))
                {
                    return value;
                }
                Console.WriteLine(errorMessage);
                Console.WriteLine(inErrorMessage);
            }
        }

        public static void Main(string[] args)
        {
            int matrixSize = ReadInt(
                "Please enter the size of the matrix",
                "Invalid value",
                "The size of a matrix must be a positive integer"
            );

            Console.WriteLine($"Execution of a matrix product for two matrices of size {matrixSize} x {matrixSize}");

            Console.WriteLine("Sequential in-memory product");
            MatrixProductMemory.InMemoryProduct(matrixSize);

            Console.WriteLine("Parallel in-memory product");
            MatrixProductMemParallel.ParallelProduct(matrixSize);

            Console.WriteLine("Multiprocessing in-memory product");
            MatrixProductMultiprocessing.MultiprocessingProduct(matrixSize);
        }
    }
}
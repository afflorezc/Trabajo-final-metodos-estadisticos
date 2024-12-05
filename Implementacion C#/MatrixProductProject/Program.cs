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

        public static int selectOption(String message, String errorMessage, String inErrorMessage, int[] options)
        {
            Boolean validOption = true;
            int numberOption;
            do
            {
                numberOption = ReadInt(message, errorMessage, inErrorMessage);
                validOption = isInIntVector(numberOption, options);
                if (validOption != true)
                {
                    Console.WriteLine(errorMessage);
                }
            } while (validOption != true);

            return numberOption;
        }

        public static Boolean isInIntVector(int value, int[] vector)
        {
            Boolean isInVector = false;
            foreach (int currentValue in vector)
            {
                if (currentValue == value)
                {
                    isInVector = true;
                    break;
                }
            }
            return isInVector;
        }

        public static void Main(string[] args)
        {
            int[] selectOptions = { 1, 2, 3 };
            String optionsText = "\n[1]Secuential in memory product. \n[2]Parallel in memory product. \n[3]Multiprocessing in memory product.y";

            int multiplicationMethod = selectOption("\nSelect the method of matrix multiplication: " + optionsText,
                                                "invalid option", "Please Select a valid option: " + optionsText, selectOptions);

            int matrixSize = ReadInt(
                "Please enter the size of the matrix",
                "Invalid value",
                "The size of a matrix must be a positive integer"
            );

            Console.WriteLine($"\nExecution of a matrix product for two matrices of size {matrixSize} x {matrixSize}");

            switch (multiplicationMethod) {
                case 1:
                    Console.WriteLine("\nSequential in-memory product");
                    MatrixProductMemory.InMemoryProduct(matrixSize);
                    break;

                case 2:
                    Console.WriteLine("\nParallel in-memory product");
                    MatrixProductMemParallel.ParallelProduct(matrixSize);
                    break;

                case 3:
                    Console.WriteLine("\nMultiprocessing in-memory product");
                    MatrixProductMultiprocessing.MultiprocessingProduct(matrixSize);
                    break;

                default:
                    Console.WriteLine("Error: invalid option");
                    break;
            }
        }
    }
}
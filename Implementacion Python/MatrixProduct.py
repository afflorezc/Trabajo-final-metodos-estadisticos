from multiprocessing import freeze_support

import MatrixProductMemory
import MatrixProductParallel
import MatrixProductMultiProcessing

def main():

    matrixSize = int(input("set up the size of the matrix: "))
    memoryProduct = MatrixProductMemory.MatrixProductMemory(matrixSize)
    print(f"Product of two matrix of size {matrixSize:.0f} x {matrixSize:.0f}")
    
    print("Sequential in memory product: ")
    memoryProduct.inMemoryProduct()

    print("Parallel in memory product: ")
    inParallelProduct = MatrixProductParallel.MatrixProductParallel(matrixSize)
    inParallelProduct.parallelProduct()

    print("Multiprocessing in memory product: ")
    multiProcessProduct = MatrixProductMultiProcessing.MatrixProductMultiProcessing(matrixSize)
    multiProcessProduct.multiProcessingProduct()

if __name__ == '__main__':
    freeze_support()
    main()
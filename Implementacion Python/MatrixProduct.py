from multiprocessing import freeze_support

import MatrixProductMemory
import MatrixProductParallel
import MatrixProductMultiProcessing

def main():
    invalidOption = True
    while(invalidOption):
        print("select the method of matrix multiplication:")
        print("[1]Secuential in memory product. \n[2]Parallel in memory product. \n[3]Multiprocessing in memory product.")
        option = int(input(":"))

        if option not in [1,2,3]:
            print("\ninvalid option")
        else:
            invalidOption = False
    

    matrixSize = int(input("set up the size of the matrix: "))
    memoryProduct = MatrixProductMemory.MatrixProductMemory(matrixSize)
    print(f"Product of two matrix of size {matrixSize:.0f} x {matrixSize:.0f}")

    if(option == 1):
        print()
        print("Sequential in memory product: ")
        memoryProduct.inMemoryProduct()
    
    elif(option == 2):
        print()
        print("Parallel in memory product: ")
        inParallelProduct = MatrixProductParallel.MatrixProductParallel(matrixSize)
        inParallelProduct.parallelProduct()

    elif(option == 3):
        print()
        print("Multiprocessing in memory product: ")
        multiProcessProduct = MatrixProductMultiProcessing.MatrixProductMultiProcessing(matrixSize)
        multiProcessProduct.multiProcessingProduct()
    
    else:
        print("Error: invalid option")

if __name__ == '__main__':
    freeze_support()
    main()
import MatrixProductMemory

def main():
    matrixSize = int(input("set up the size of the matrix: "))
    memoryProduct = MatrixProductMemory.MatrixProductMemory(matrixSize)
    print(f"Product of two matrix of size {matrixSize:.0f} x {matrixSize:.0f}")
    memoryProduct.inMemoryProduct()

main()
    
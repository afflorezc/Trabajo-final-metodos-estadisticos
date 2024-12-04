import numpy as np
import time

class MatrixProductMemory:
    
    def __init__(self, matrixSize):
        self.matrixSize = matrixSize
        self.matrixA = np.random.rand(matrixSize, matrixSize)
        self.matrixB = np.random.rand(matrixSize, matrixSize)
        self.matrixC = np.zeros((matrixSize, matrixSize))

    def inMemoryProduct(self):
        startTime = time.time()
        np.matmul(self.matrixA, self.matrixB, self.matrixC)
        endTime = time.time()
        self.printFirstColumns()
        print(f"Product completed in {endTime - startTime:.2f} seconds.")

    def matrixProduct(self):
        for i in range(self.matrixSize):
            for j in range(self.matrixSize):

                self.matrixC[i][j] = 0
                for k in range(self.matrixSize):
                    self.matrixC[i][j] += self.matrixA[i][k]*self.matrixB[k][j]

    def printFirstColumns(self):
        print("Product completed. 10 first columns in first row: ")
        for i in range(10):
            if i < 9:
                print(f"{self.matrixC[0][i]:.2f}", end =", ")
            else:
                print(f"{self.matrixC[0][i]:.2f}")
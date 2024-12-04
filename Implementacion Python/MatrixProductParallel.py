import numpy as np
from concurrent.futures import ThreadPoolExecutor
import time
import os

class MatrixProductParallel:

    def __init__(self, matrixSize):
    
        self.matrixSize = matrixSize 
        self.numThreads = os.cpu_count()
        self.matrixA = np.random.rand(matrixSize, matrixSize)
        self.matrixB = np.random.rand(matrixSize, matrixSize)
        self.matrixC = np.zeros((matrixSize, matrixSize))

    def printFirstColumns(self):
        print("Product completed. 10 first columns in first row: ")
        for i in range(10):
            if i < 9:
                print(f"{self.matrixC[0][i]:.2f}", end =", ")
            else:
                print(f"{self.matrixC[0][i]:.2f}")

    def parallelProduct(self):
        
        startTime = time.time()

        with ThreadPoolExecutor(max_workers = self.numThreads) as executor:
       
            block_size = self.matrixSize // self.numThreads
            futures = [
                executor.submit(self.multiply_block, i * block_size, min((i + 1) * block_size,self.matrixSize))
                for i in range(self.numThreads)
            ]
            for future in futures:
                future.result()

        endTime = time.time()
        self.printFirstColumns()
        print(f"Product completed in {endTime - startTime:.2f} seconds.")

    def multiply_block(self, startRow, endRow):

        self.matrixC[startRow:endRow, :] = np.matmul(self.matrixA[startRow:endRow, :], self.matrixB)

    
import numpy as np
from multiprocessing import Pool
import time
import os

class MatrixProductMultiProcessing:

    def __init__(self, matrixSize):

        self.matrixSize = matrixSize  
        self.numProcesses = os.cpu_count()   
        
        self.matrixA = np.random.rand(matrixSize, matrixSize)
        self.matrixB = np.random.rand(matrixSize, matrixSize)
        self.matrixC = np.zeros((matrixSize, matrixSize))

    def multiProcessingProduct(self):

        startTime = time.time()

        with Pool(processes = self.numProcesses) as pool:
            block_size = self.matrixSize // self.numProcesses
            tasks = [
                (i * block_size, (i + 1) * block_size)
                for i in range(self.numProcesses)
            ]
            results = pool.map(self.multiplyBlock, tasks)

        for result in results:
            self.collectResults(result)

        endTime = time.time()
        self.printFirstColumns()
        print(f"Product completed in {endTime - startTime:.2f} seconds.")

    def printFirstColumns(self):

        print("Product completed. 10 first columns in first row: ")
        for i in range(10):
            if i < 9:
                print(f"{self.matrixC[0][i]:.2f}", end =", ")
            else:
                print(f"{self.matrixC[0][i]:.2f}")

    def multiplyBlock(self, args):
        startRow, endRow = args
        
        subMatrix = np.zeros((endRow - startRow, self.matrixSize))
        subMatrix = np.matmul(self.matrixA[startRow:endRow, :], self.matrixB)

        return startRow, subMatrix

    def collectResults(self, result):
        startRow, subMatrix = result
        self.matrixC[startRow:startRow + subMatrix.shape[0], :] = subMatrix

   
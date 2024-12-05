package com.afflorezc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jblas.DoubleMatrix;

public class MatrixProductMemParallel {

    static int numThreads = 4;
    static DoubleMatrix matrixA;
    static DoubleMatrix matrixB;
    static DoubleMatrix matrixC;

    public static void parallelProduct(int matrixSize) {

        matrixA = DoubleMatrix.rand(matrixSize, matrixSize);
        matrixB = DoubleMatrix.rand(matrixSize, matrixSize);

        long startTime = System.nanoTime();

        matrixC = new DoubleMatrix(matrixSize, matrixSize);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int blockSize = matrixSize / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int startRow = i * blockSize;
            int endRow = (i == numThreads - 1) ? matrixSize : (i + 1) * blockSize;
            executor.submit(new MatrixMultiplier(matrixSize, startRow, endRow));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}

        long endTime = System.nanoTime();

        System.out.println("Product completed. First 10 columns in first row: ");
        for(int i = 0; i < 10; i++){
            if(i < 9){
                System.out.printf(" %.2f , ", matrixC.get(0,i));
            }
            else{
                System.out.printf(" %.2f %n", matrixC.get(0,i));
            }
        }
        
        System.out.printf("product completed in %.2f seconds.%n", (endTime - startTime) / 1e9);
    }

    static class MatrixMultiplier implements Runnable {
        int matrixSize, startRow, endRow, blockSize;
        DoubleMatrix blockMatrixA;
        DoubleMatrix blockMatrixB;
        DoubleMatrix blockProduct;

        MatrixMultiplier(int matrixSize, int startRow, int endRow) {
            
            this.matrixSize = matrixSize;
            this.startRow = startRow;
            this.endRow = endRow;
            this.blockSize = endRow - startRow;
            this.blockMatrixA = new DoubleMatrix(blockSize, matrixSize);
            this.blockMatrixB = new DoubleMatrix(matrixSize, blockSize);
            this.blockProduct = new DoubleMatrix(blockSize, blockSize);
        }

        public void getBlockMatrixA(){
            for(int i = startRow; i < endRow; i++){

                this.blockMatrixA.putRow(i - startRow, matrixA.getRow(i));
            }
        }

        public void getBlockMatrixB(int startColumn, int endColumn){

            for(int i = startColumn; i < endColumn; i++){

                this.blockMatrixB.putColumn(i - startColumn, matrixB.getColumn(i));
                }
        }

        public void setMatrixResultBlock(int startColumn, int endColumn){
            
            int rows = this.blockMatrixA.rows;

            for(int i = 0; i < rows; i++){
                for(int j = startColumn; j < endColumn; j++){
                    matrixC.put(startRow + i, j, this.blockProduct.get(i,j - startColumn));
                }
            }
        }

        @Override
        public void run() {

            getBlockMatrixA();

            for(int i = 0; i < matrixSize; i += this.blockSize){
                getBlockMatrixB(i, i +this.blockSize);
                this.blockProduct = this.blockMatrixA.mmul(this.blockMatrixB);
                setMatrixResultBlock(i, i + this.blockSize);
            }
        }
    }
}

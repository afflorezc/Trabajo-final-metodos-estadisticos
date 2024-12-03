package com.afflorezc;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

import org.jblas.DoubleMatrix;

public class MatrixProductMultiprocessing {

    static DoubleMatrix matrixA;
    static DoubleMatrix matrixB;
    static DoubleMatrix matrixC;


    public static void multiprocessingProduct(int matrixSize){

        long startTime = System.nanoTime();

        matrixA = DoubleMatrix.rand(matrixSize, matrixSize);
        matrixB = DoubleMatrix.rand(matrixSize, matrixSize);
        matrixC = new DoubleMatrix(matrixSize, matrixSize);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MatrixMultiplier(matrixSize, 0, matrixSize));

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

    static class MatrixMultiplier extends RecursiveTask<Void> {
        
        int THRESHOLD; 
        
        int matrixSize,startRow, endRow, blockSize;
        DoubleMatrix blockMatrixA;
        DoubleMatrix blockMatrixB;
        DoubleMatrix blockProduct;

        MatrixMultiplier(int matrixSize, int startRow, int endRow) {
            
            THRESHOLD = Math.max(100, matrixSize / Runtime.getRuntime().availableProcessors());
            this.matrixSize = matrixSize;
            this.startRow = startRow;
            this.endRow = endRow;
            this.blockSize = endRow - startRow;
        }

        public void getBlockMatrixA(){

            this.blockMatrixA = new DoubleMatrix(blockSize, matrixSize);

            for(int i = startRow; i < endRow; i++){
                this.blockMatrixA.putRow(i - startRow, matrixA.getRow(i));
            }
        }

        public void getBlockMatrixB(int startColumn, int endColumn){
            
            this.blockMatrixB = new DoubleMatrix(matrixSize, blockSize);

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
        protected Void compute() {
            if (blockSize <= THRESHOLD) {

                getBlockMatrixA();

                for(int i = 0; i < matrixSize; i += this.blockSize){
                    getBlockMatrixB(i, i +this.blockSize);
                    this.blockProduct = this.blockMatrixA.mmul(this.blockMatrixB);
                    setMatrixResultBlock(i, i + this.blockSize);
                }

            } else {

                int mid = (startRow + endRow) / 2;
                MatrixMultiplier upperHalf = new MatrixMultiplier(matrixSize, startRow, mid);
                MatrixMultiplier lowerHalf = new MatrixMultiplier(matrixSize, mid, endRow);
                invokeAll(upperHalf, lowerHalf);
            }
            return null;
        }
    }

}

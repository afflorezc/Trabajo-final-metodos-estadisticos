package com.afflorezc;

import org.jblas.DoubleMatrix;
/**import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;**/

public class MatrixProductMemory {
    
	public static void inMemoryProduct(int matrixSize){

        DoubleMatrix matrixA = DoubleMatrix.rand(matrixSize, matrixSize);
        DoubleMatrix matrixB = DoubleMatrix.rand(matrixSize, matrixSize);
        
        long startTime = System.nanoTime();
        
        DoubleMatrix matrixC = matrixProduct(matrixA, matrixB);

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

    public static DoubleMatrix matrixProduct(DoubleMatrix matrixA, DoubleMatrix matrixB){

		int m = matrixA.columns;
		int p = matrixB.rows;
		
		DoubleMatrix result;
		
		if(m != p) {
			System.out.println("You can't multiply the two matrix, dimensions don't match"
					   + "the number of columns for matrix one must coincide with the "
					   + "number of rows of the second matrix");
			result = new DoubleMatrix(0,0);
			
		} else {
            result = matrixA.mmul(matrixB);
		}

		return result;
    }
    
}

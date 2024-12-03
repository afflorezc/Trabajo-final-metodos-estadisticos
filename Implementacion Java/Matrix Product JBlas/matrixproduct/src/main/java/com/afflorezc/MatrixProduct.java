package com.afflorezc;

import java.io.*;
import java.util.Scanner;

public class MatrixProduct {

    static Scanner sc = new Scanner(System.in);

    public static void executeSecuentialProduct(int matrixSize){

        MatrixProductMemory.inMemoryProduct(matrixSize);
    }

    public static void executeParallelProduct(int matrixSize){
        
        MatrixProductMemParallel.parallelProduct(matrixSize);
    }

    public static void executeMultiproccessProduct(int matrixSize){
        MatrixProductMultiprocessing.multiprocessingProduct(matrixSize);
    }

    public static int readInt(String message, String errorMessage, String inErrorMessage){
        System.out.println(message + ": ");
        validateInt(errorMessage, inErrorMessage);
        int value = sc.nextInt();
        return value;
    }

    public static void validateInt(String errorMessage, String suggestionMessage) {
    	
    	while (!sc.hasNextInt()) {
            System.out.println(errorMessage);
            System.out.println(suggestionMessage);
            sc.next();
        }
    }
    public static void main(String[] args) throws IOException {
        
        int matrixSize = readInt("Please enter the size of the matrix: ", "Invalid value",
                             "The size of a matrix must be a positive integer");

        System.out.println("Execution of a matrix product for two matrix of size " + matrixSize +" x " 
                            + matrixSize);

        System.out.println("Secuential in memory product");
        executeSecuentialProduct(matrixSize);
        
        System.out.println("Parallel in memory product");
        executeParallelProduct(matrixSize);
        
        /* System.out.println("Secuential in disc product");
        executeInDiscProduct();**/

        System.out.println("Multiprocessing in memory product");
        executeMultiproccessProduct(matrixSize);
    }  
}

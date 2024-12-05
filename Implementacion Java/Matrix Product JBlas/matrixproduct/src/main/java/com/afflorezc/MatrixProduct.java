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

    public static int selectOption(String message, String errorMessage, String inErrorMessage, int[] options){
        Boolean validOption = true;
        int numberOption;
        do{
            numberOption = readInt(message, errorMessage, inErrorMessage);
            validOption = isInIntVector(numberOption, options);
            if (validOption != true) {
                System.out.println(errorMessage);
            }
        }while(validOption != true);

        return numberOption;
    }

    public static Boolean isInIntVector(int value, int[] vector){
        Boolean isInVector = false;
        for (int currentValue : vector) {
            if (currentValue == value) {
                isInVector = true;
                break;
            }
        }
        return isInVector;
    }

    public static void validateInt(String errorMessage, String suggestionMessage) {
    	
    	while (!sc.hasNextInt()) {
            System.out.println(errorMessage);
            System.out.println(suggestionMessage);
            sc.next();
        }
    }
    public static void main(String[] args) throws IOException {
        int[] selectOptions = {1,2,3};
        String optionsText = "\n[1]Secuential in memory product. \n[2]Parallel in memory product. \n[3]Multiprocessing in memory product.y";

        int multiplicationMethod = selectOption("Select the method of matrix multiplication: "+optionsText,
                                                "invalid option", "Please Select a valid option: "+optionsText, selectOptions);
        
        int matrixSize = readInt("Please enter the size of the matrix: ", "Invalid value",
                             "The size of a matrix must be a positive integer");

        System.out.println("Execution of a matrix product for two matrix of size " + matrixSize +" x " 
                            + matrixSize);
                        
        switch (multiplicationMethod) {
            case 1:
                System.out.println("\nSecuential in memory product");
                executeSecuentialProduct(matrixSize);   
                break;
            case 2:
                System.out.println("\nParallel in memory product");
                executeParallelProduct(matrixSize);
                break;
            case 3:
                System.out.println("\nMultiprocessing in memory product");
                executeMultiproccessProduct(matrixSize);
                break;
        
            default:
            System.out.println("error: invalid oprion");
                break;
        }
     
        /* System.out.println("Secuential in disc product");
        executeInDiscProduct();**/
    }  
}

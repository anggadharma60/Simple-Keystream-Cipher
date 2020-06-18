/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockettcp;

import java.util.Random;

/**
 *
 * @author Angga
 */
public class KeyGeneratorPattern {
    protected int randomKeyDecimal[];
    protected String randomKeyBinary[];
    protected char randomKeyASCII[];

    public KeyGeneratorPattern(String key, int length) {
        randomKeyBinary = new String[length];
        
        int index0=0;
        int index1=1;
        int index2=2;
        int index3=3;
        for(int i=0;i<length;i++){
            StringBuilder sb = new StringBuilder();
            sb.append(key.charAt(index0)^key.charAt(index3));
            sb.append(key.charAt(index1));
            sb.append(key.charAt(index2));
            sb.append(key.charAt(index3));
            randomKeyBinary[i]=String.format("%08d", Integer.parseInt(sb.toString()));
            
            index0=(index0+1)%15;
            index1=(index1+1)%15;
            index2=(index2+1)%15;
            index3=(index3+1)%15;
        }
        
    }

    public void printRandomKeyDecimal(int[] randomKeyDecimal){
        System.out.println("Random Key Decimal:");
        for(int i=0;i<randomKeyDecimal.length;i++){
            
            System.out.print(randomKeyDecimal[i]+" ");
//           System.out.print(randomKeyBinary[i]+" ");
//           System.out.print(randomKeyASCII[i]+" ");
        }
        System.out.println("\n");
    }
    public void printRandomKeyBinary(String[] randomKeyBinary){
        System.out.println("Random Key Binary:");
        for(int i=0;i<randomKeyBinary.length;i++){
//            System.out.print(randomKeyDecimal[i]+" ");
            System.out.print(randomKeyBinary[i]+" ");
//           System.out.print(randomKeyASCII[i]+" ");
        }
        System.out.println("\n");
    }
    public void printRandomKeyASCII(int[] randomKeyASCII){
        System.out.println("Random Key ASCII:");
        for(int i=0;i<randomKeyASCII.length;i++){
//            System.out.print(randomKeyDecimal[i]+" ");
//           System.out.print(randomKeyBinary[i]+" ");
            System.out.print(randomKeyASCII[i]+" ");
        }
        System.out.println("\n");
    }
    
    public int randomNumber(){
        int min=1;
        int max=255;
        Random number = new Random();
        return number.nextInt((max - min) + 1) + min;
    }
    
    public String convertDecimalToBinary(int number){
        String binary = Integer.toBinaryString(number);
        binary=String.format("%08d", Integer.parseInt(binary));
        return binary;
    }
}

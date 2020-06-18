/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockettcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Angga
 */
public class TCPServer {
    public static void main(String arg[]) throws Exception{
        String sentence;
        String modifiedSentence;
        int[] arrayDecimal,binaryToDecimal = null;
        String[] arrayBinary,hasilXOR = null;
        String[] randomKeyBinary = null;
        char[] randomKeyASCII = null;
        int textlength;
        String ciphertext,newtext,plaintex;
        String[] text;
        
        try{
            ServerSocket welcomeSocket = new ServerSocket(8080);
        
            while(true){
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                ciphertext = inFromClient.readLine();
                System.out.println("Input From Client");

                arrayBinary = stringToArray(ciphertext);
                printStringToArray(arrayBinary);

    //          Generate Random Key
                textlength = ciphertext.length()/8;
                KeyGeneratorPattern key = new KeyGeneratorPattern("101101110111011", textlength);

                //Random Key Binary
                randomKeyBinary = key.randomKeyBinary;
                key.printRandomKeyBinary(randomKeyBinary);

                //XOR Operation
                hasilXOR = XOROperation(arrayBinary, randomKeyBinary);
                printXOROperation(hasilXOR);
                
                //Binary to Decimal
                binaryToDecimal = convertBinaryToDecimal(hasilXOR);
                printBinaryToDecimal(binaryToDecimal);
                
                //Decimal To Text
                newtext = convertDecimaltoText(binaryToDecimal);
                printDecimalToText(newtext);
                
                
                outToClient.writeBytes(newtext + '\n');
    //            modifiedSentence = inFromClient.readLine();
    //            System.out.println("From Server: " + modifiedSentence);
    //            System.out.println("");
            }
        }catch(IOException e){
            System.out.println(e); 
        }
        
        
    }
    
    public static String[] stringToArray(String ciphertext){
        int len=ciphertext.length();
        int x=0;
        int y=7;
        String[] Text = new String[len/8];
        for(int i=0;i<len/8;i++){
            Text[i]=ciphertext.substring(x, y+1);
            x=x+8;
            y=y+8;
        }
        return Text;
    }
    
    public static void printStringToArray(String[] arrayBinary){
        int len=arrayBinary.length;
        for(int i=0;i<len;i++){
            System.out.print(arrayBinary[i]+" ");
        }
        System.out.println("\n");
    }
    
    public static int[]convertTextToDecimal(String text){
       int len = text.length();
       int[] arrayDecimal= new int[len];
       for(int i=0;i<len;i++){
           char character = text.charAt(i); // start on the first character
           arrayDecimal[i] = (int) character; //convert the first character
//           System.out.println(arrayASCII[i]);
       } 
       return arrayDecimal;    
    }
    
    public static void printTextToDecimal(int[] arrayDecimal){
       int len = arrayDecimal.length;
       System.out.println("Text To Decimal:");
       for(int i=0;i<len;i++){
            System.out.print(arrayDecimal[i]+" ");
       }
        System.out.println("\n");
    }
    
    public static String[] convertDecimalToBinary(int[] arrayDecimal){
        int len = arrayDecimal.length;
        String[] arrayBinary = new String[len];
        for(int i=0;i<len;i++){
            String binary = Integer.toBinaryString(arrayDecimal[i]);
            binary=String.format("%08d", Integer.parseInt(binary));
            arrayBinary[i] = binary;
//            System.out.println(binary);      
        }
        return arrayBinary;
    }
    
    public static void printDecimalToBinary(String[] arrayBinary){
       int len = arrayBinary.length;
       System.out.println("Decimal To Binary:");
       for(int i=0;i<len;i++){
            System.out.print(arrayBinary[i]+" ");
       }
       System.out.println("\n");
    }
    
    public static int[] convertBinaryToDecimal(String[] arrayBinary){
        int len = arrayBinary.length;
        int[] arrayDecimal= new int[len];
        for(int i=0;i<len;i++){
          int decimal = Integer.parseInt(arrayBinary[i],2);
          arrayDecimal[i]= decimal;
//            System.out.print(arrayDecimal[i]+" ");
        }
        return arrayDecimal;
    }
    
    public static void printBinaryToDecimal(int[] arrayBinary){
       int len = arrayBinary.length;
       System.out.println("Decimal To Binary:");
       for(int i=0;i<len;i++){
            System.out.print(arrayBinary[i]+" ");
       }
       System.out.println("\n");
    }

    public static String[] XOROperation(String[] arrayBinary, String[] randomKeyBinary){
        int len = arrayBinary.length;
        String[] hasilXOR = new String[len] ;
        
        for(int i=0;i<len;i++){
            String chr = arrayBinary[i];
            String key= randomKeyBinary[i];
            StringBuffer sb=new StringBuffer();
      
            for(int j=0;j<8;j++){
                sb.append(arrayBinary[i].charAt(j)^randomKeyBinary[i].charAt(j));
                hasilXOR[i]= sb.toString();
            } 
        } 
        return hasilXOR;
    }
    
    public static void printXOROperation(String[] hasilXOR){
        int len= hasilXOR.length;
        System.out.println("XOR Operation");
        for(int i=0;i<len;i++){
          System.out.print(hasilXOR[i]+" ");
        }
        System.out.println("\n");
     }
    
    public static String convertDecimaltoText(int[] arrayDecimal){
        String Text;
        StringBuilder sb = new StringBuilder();
        int len = arrayDecimal.length;
        for(int i=0;i<len;i++){
            char chr = (char) arrayDecimal[i];
            sb.append(chr);
        }
        Text = sb.toString();
        return Text;
    }
    
    public static void printDecimalToText(String Text){
        System.out.println("Decimal To Text:");
        System.out.println(Text);
        System.out.println("");
    }
}
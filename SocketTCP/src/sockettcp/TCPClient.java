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
import java.net.Socket;

/**
 *
 * @author Angga
 */
public class TCPClient {
    public static void main(String arg[])throws Exception{
        String sentence;
        String modifiedSentence;
        int[] arrayDecimal,binaryToDecimal = null;
        String[] arrayBinary,hasilXOR = null;
        String[] randomKeyBinary = null;
        char[] randomKeyASCII = null;
        int textlength;
        String ciphertext,newtext;
        try{
            while (true) {   
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                Socket clientSocket = new Socket("",8080);  
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Input To Server:");
                sentence = inFromUser.readLine();
                //String To Decimal
                arrayDecimal= convertTextToDecimal(sentence);
                printTextToDecimal(arrayDecimal);

                //Decimal To Binary
                arrayBinary = convertDecimalToBinary(arrayDecimal);
                printDecimalToBinary(arrayBinary);

        //      Generate Random Key
                textlength = sentence.length();
                KeyGeneratorPattern key = new KeyGeneratorPattern("101101110111011", textlength);
        //        
        //       Random Key Binary
                randomKeyBinary = key.randomKeyBinary;
                key.printRandomKeyBinary(randomKeyBinary);
        //        
                //XOR Operation
                hasilXOR = XOROperation(arrayBinary, randomKeyBinary);
                printXOROperation(hasilXOR);

                //Binary to Decimal
                binaryToDecimal = convertBinaryToDecimal(hasilXOR);
                printBinaryToDecimal(binaryToDecimal);

        //      Decimal To Text
                newtext = convertDecimaltoText(binaryToDecimal);
                printDecimalToText(newtext);
                
                ciphertext = arrayToString(hasilXOR);
                outToServer.writeBytes(ciphertext+ '\n');
//                modifiedSentence = inFromServer.readLine();
//                System.out.println("From Server: "+modifiedSentence);
//                System.out.println("");
            }
        }catch(IOException e){
            System.out.println(e); 
        }
        
    } 
    
    public static String arrayToString(String[] hasilXOR){
        int len = hasilXOR.length;
        StringBuffer sb = new StringBuffer();
        String Text;
        for(int i= 0;i<len;i++){
            sb.append(hasilXOR[i]);
        }
        Text= sb.toString();
        return Text;
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
       System.out.println("Binary To Decimal:");
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
        System.out.print("\n");
    }
    
}

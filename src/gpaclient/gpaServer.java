/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpaclient;

import java.net.*; 
import java.io.*;
import java.util.*;

/**
 *
 * @author pelon
 */
public class gpaServer {
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null; 
    private DataOutputStream out = null;
  

    public gpaServer(int port) 
    { 
        // starts server and waits for a connection 
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server started"); 
  
            System.out.println("Waiting for a client ..."); 
  
            socket = server.accept(); 
            System.out.println("Client Connected"); 
            PrintStream ps 
            = new PrintStream(socket.getOutputStream()); 
            // takes input from the client socket 
            in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
  
            String line = ""; 
  
            // reads message from client until "Over" is sent 
            while (!line.equals("OK")) 
            { 
                try
                { 
                    line = in.readUTF(); 
                    String[] values = line.split(", ");
                    System.out.println(Arrays.toString(values));
                    String answer = calcGPA(values);
                    System.out.println(answer);
                    
                    ps.println(answer);
                    
                    
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("Closing connection"); 
  
            // close connection 
            socket.close(); 
            in.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        gpaServer server = new gpaServer(5000); 
    } 
    
    public String calcGPA(String[] pel){
        Hashtable<String, Double> hashTable = new Hashtable<String, Double>();
        hashTable.put("A", 4.0);
        hashTable.put("A-", 3.7);
        hashTable.put("B+", 3.3);
        hashTable.put("B", 3.0);
        hashTable.put("B-", 2.7);
        hashTable.put("C+", 2.3);
        hashTable.put("C", 2.0);
        hashTable.put("C-", 1.7);
        hashTable.put("D+", 1.3);
        hashTable.put("D", 1.0);
        hashTable.put("F", 0.0);
        
       
       
       int currCreditValue = pel.length-1;
       int currGPAValue= pel.length-2;
       int currentCredit = Integer.parseInt((pel[currCreditValue]));
       double currentGpa = Double.parseDouble(pel[currGPAValue]);
       
       
       
       double sum = 0;
       double semesterCredit = 0;
       
       
       for(int i = 1; i < pel.length-2; i++){
           sum += hashTable.get(pel[i]) * Integer.parseInt(pel[i+1]);
           i++;
       }
       for(int i = 2; i < pel.length-2; i++){
           semesterCredit += Integer.parseInt(pel[i]);
           i++;
       }
       double gpaSemester = sum/semesterCredit;
       double gpaCummulative = (currentGpa * currentCredit + sum)/(currentCredit + semesterCredit);
       
       int totalCredits = (int) (currentCredit + semesterCredit);
       
       String solution = String.format("%.3f", gpaSemester) + ", " + String.format("%.3f", gpaCummulative) + ", " + totalCredits;
       
        return solution;
    }
}

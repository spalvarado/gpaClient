/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpaclient;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
/**
 *
 * @author pelon
 */
public class gpaClient {

    /**
     * @param args the command line arguments
     */
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;


    public gpaClient(String address, int port) throws IOException {

        try {
            socket = new Socket(address, port);
            System.out.println("Connected");


            input = new DataInputStream(System.in);


            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }


        String line = "";
 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str;

        while (!line.equals("OK")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
                
                    out.writeBytes(line + "\n");
                    str = br.readLine();
                    String[] courseResults = str.split(", ");
                    System.out.println("Semester GPA: " + courseResults[0] + "\n" +
                            "Cumulative GPA: " + courseResults[1] + "\n" +
                            "Total Credit Hours: " + courseResults[2]);
                
                
            } catch (IOException i) {
                System.out.println(i);
            }
        }


        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) throws IOException {
        gpaClient s = new gpaClient("Localhost", 5000);
 
        }
    }



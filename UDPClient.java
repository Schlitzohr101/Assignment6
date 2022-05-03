package com.company;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class UDPClient{
    public static void main(String args[]){
        // args give message contents and server hostname
        DatagramSocket aSocket = null;
        Console cnsl = System.console();

        try {
            aSocket = new DatagramSocket();

            int serverPort = parseInt(cnsl.readLine("Please input the server port: "));

            InetAddress aHost = InetAddress.getByName(cnsl.readLine("Please input ip address of server: "));

            byte m[];

            while(true){
                m = cnsl.readLine("Please input your message: ").getBytes(StandardCharsets.UTF_8);

                if(Objects.equals(new String(m), "/")){
                    break;
                }

                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("Reply: " + new String(reply.getData()));
            }
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}
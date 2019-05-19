/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author alexsid
 */
public class ASocket {

    public ASocket() {
        
    }
    
    public void startSocket() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(Const.PORT);
            System.out.println("Simple Socket Server ver " + Const.PROG_VER + " \nStarted on port " + Const.PORT);
        } catch (Exception e) {
            System.err.println("Simple Socket Server ver " + Const.PROG_VER + " \nCan not startSocket listening on port " + Const.PORT);
            e.printStackTrace();
            System.exit(-1);
        }

        ServerCommander serverCommander = new ServerCommander();
        serverCommander.start();

        while (true) {

            try {

                Socket socket = serverSocket.accept();
                socket.setSoTimeout(Const.SERVERSOCKETTIMEOUT);

                SocketListener listener = new SocketListener(socket);
                SocketSender sender = new SocketSender(socket);
                
                listener.start();
                sender.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }

    }
}

class ServerCommander extends Thread {

    public ServerCommander() {

    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (! isInterrupted()) {
                String message = in.readLine();
                if(message.equalsIgnoreCase("exit")) {
                    System.out.println("Server STOP");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
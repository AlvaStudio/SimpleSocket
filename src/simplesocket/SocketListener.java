/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author alexsid
 */
class SocketListener extends Thread {
    
    private BufferedReader bufferedReader;
    private Socket socket;

    public SocketListener(Socket socket) {
        try {
            this.socket = socket;
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        
        while (!isInterrupted()) {
            try {
                if (this.socket != null) {
                    String incomingString = bufferedReader.readLine();
                    System.out.println("----------");
                    System.out.println();
                    System.out.println(incomingString);
                    System.out.println("----------");
                } else {
                    System.out.println("Socket is NULL");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    
}

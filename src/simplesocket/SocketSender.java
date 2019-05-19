/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesocket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexsid
 */
class SocketSender extends Thread {

    private PrintWriter printWriter;
    private Socket socket;
    private ArrayList<String> messageQueue = new ArrayList<String>();
    
    public SocketSender(Socket socket) {
        try {
            this.socket = socket;
            printWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void sendMessage(String message) {
        messageQueue.add(message);
        notify();
    }
    
    private synchronized String getNextMessageFromQueue() {
        String message = null;
        try {
            while (messageQueue.size() == 0)
                wait();
            
            message = (String) messageQueue.get(0);
            messageQueue.remove(0);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return message;
    }
    
    private void sendMessageToClient(String message) {
        System.out.println("----------");
        System.out.println("Sending message...");
        System.out.println("----------");
        System.out.println("Send message : " + message);

        printWriter.println(message);
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {            
            String message = getNextMessageFromQueue();
            if (message != null) {
                sendMessageToClient(message);
            }
        }
    }
    
    

    
    
}

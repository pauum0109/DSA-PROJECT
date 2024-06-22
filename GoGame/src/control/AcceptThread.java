/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose:
Handles received objects through an AcceptListener interface;
Designed to continuously listen for incoming objects from a server
*/
package control;

import java.io.*;
import java.net.*;

public class AcceptThread extends Thread {
    Client      goNetClient;
    AcceptListener accepter = null;
    Object      acceptObject;

    public AcceptThread(Client goNetClient) {
        super("GoAcceptThread");
        this.goNetClient = goNetClient;
    }
    
    public void run() {
        while (true) {
            try {
                acceptObject = goNetClient.acceptFromServer();
            } catch (SocketException soEx) {
                System.err.println("Socket Exception in line 28 of GoAcceptThread.java");

                acceptObject = new Object(Constants.CONFIRM, Constants.MYSOCKETERRORSTR);
                if (accepter != null) accepter.accept(acceptObject);
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
            
            if (accepter != null) accepter.accept(acceptObject);
        }
    }

    /*public AcceptListener getAccepter() {
        if (accepter != null) return accepter;
        else return null;
    }*/

    public void setAccepter(AcceptListener accepter) {
        this.accepter = accepter;
    }
}
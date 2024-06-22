/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Manage socket connections, send data to the server, and receive responses
*/
package control;

import java.io.*;
import java.net.*;

public class Client {
    private Socket              goSocket = null;
    private ObjectOutputStream  goNetOut = null;
    private ObjectInputStream   goNetIn  = null;

    public  int                 retValue;

    public Client(String serverName, int serverPort) {
        try {
            goSocket = new Socket(InetAddress.getByName(serverName), serverPort);
            goNetOut = new ObjectOutputStream(goSocket.getOutputStream());
            goNetIn  = new ObjectInputStream(goSocket.getInputStream());
            retValue = 0;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverName + ".");
            retValue = -1;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverName + ".");
            retValue = -2;
        }
    }

    public Object acceptFromServer() throws OptionalDataException, ClassNotFoundException, IOException {
        Object fromServer;
        if ((fromServer = (Object)goNetIn.readObject()) != null) {
            return fromServer;
        }
        return null;
    }

    public boolean sendToServer(Object fromUser) {
        try {
            goNetOut.writeObject(fromUser);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            return false;
        }
        return true;
    }
}
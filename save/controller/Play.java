package controller;

import ui.*;

/**
 *
 * @author alice
 */
public class Play {
    public static Login login;
    
    public static void main(String[] args){
        new Play().init();
    }
    
    public void init(){
        login = new Login();
        login.run();
//        socketHandle = new SocketHandle();
//        socketHandle.run();
    }
    
    public enum View{
        LOGIN,
        REGISTER
    }
}

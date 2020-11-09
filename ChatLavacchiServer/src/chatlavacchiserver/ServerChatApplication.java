/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiserver;

/**
 *
 * @author Ginevra
 */
public class ServerChatApplication {
    /**
     * il main
     * @param args 
     */
    public static void main(String[] args) {
        
        MultiServer s=new MultiServer();
        s.start();
    } 
}

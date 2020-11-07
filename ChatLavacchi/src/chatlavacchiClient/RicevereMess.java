/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.net.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 *
 * @author Ginevra
 */
class RicevereMess implements Runnable
{
    /**
     * @param socket
     * @param input_tastiera
     * @param messaggio
     * @param risposta
     * @param outVersoServer
     * @param inDalServer
     */
    Socket socket;
    BufferedReader input_tastiera;
    String messaggio, risposta;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    private JPanel chat;
    
    /**
     * contruttore con parametro
     * @param c 
     */
    public RicevereMess(JPanel p,BufferedReader i ){
        //this.input_tastiera=c.input_tastiera;
        /*this.messaggio=c.messaggio;
        this.risposta=c.risposta;*/
        //this.outVersoServer=c.outVersoServer;
        this.inDalServer=i;
        this.chat=p;
        
    }
    @Override
    /**
     * override del metodo run dei thread, si occupa di gestire l'uscita
     */
    public void run() {
        for(;;){
            try {
                risposta=inDalServer.readLine();
                if(risposta.toUpperCase().equals("ADDIO")){
                    return;
                }
                JLabel nuovo=new JLabel("<html>"+risposta+"</html>");
                //JTextArea nuovo=new JTextArea("<html>"+risposta+"</html>");
                nuovo.setPreferredSize(new Dimension(100,40));
                chat.add(nuovo);
                //chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server.");
                System.exit(1);
            }
        }
    }
}

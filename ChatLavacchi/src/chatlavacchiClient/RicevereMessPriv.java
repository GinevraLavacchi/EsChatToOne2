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
import javax.swing.SwingUtilities;
/**
 *
 * @author Ginevra
 * la classe che si occupa di riceve i messaggi nella chat privata
 *  @param socket il socket
 * @param input_tastiera il BufferedReader per leggere i messaggi di risposta
 * @param risposta la risposta del server
 * @param outVersoServer il DataOutputStream
 * @param inDalServer il BufferedReader
 * @param chat il pannello della chat
 * @param jsp lo scrollPane che contiene la chat
 */
class RicevereMessPriv implements Runnable
{
    Socket socket;
    BufferedReader input_tastiera;
    String risposta;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    private JPanel chat;
    JScrollPane jsp;
    /**
     * contruttore con parametro
     * @param c 
     */
    public RicevereMessPriv(JScrollPane jsp,JPanel p,BufferedReader i ){
        
        this.inDalServer=i;
        this.chat=p;
        this.jsp=jsp;
    }
    @Override
    /**
     * override del metodo run dei thread, si occupa di gestire l'uscita
     */
    public void run() {
        for(;;){
            try {
                
                risposta=inDalServer.readLine();
                String[] appoggio=risposta.split(":::");
                /*if(appoggio[1].toUpperCase().equals("ADDIO")){
                    return;
                }*/
                if(appoggio[0].equals("Privato"))//guardo se è per la chat privata
                {
                    String appo=appoggio[1];
                    System.out.println(appo);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>");
                    nuovo.setPreferredSize(new Dimension(300,40));
                    chat.add(nuovo);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                    
                }
                
                SwingUtilities.updateComponentTreeUI(chat);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server.");
                System.exit(1);
            }
        }
    }
}

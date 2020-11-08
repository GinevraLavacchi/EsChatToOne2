/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ginevra
 * la classe che gestisce l'invio del messaggio al server
 * @param outVersoServer il DataOutputStream 
 * @param m la stringa del messaggio ricavata dal JTextField
 * @param messaggio il JTextField che contiene il messaggio
 */
public class EventoInviaMess implements ActionListener 
{
    private DataOutputStream outVersoServer;
    private String m;
    private JTextField messaggio;
    /**
     * contruttore con parametri
     * @param mess
     * @param o 
     */
    public EventoInviaMess(JTextField mess,DataOutputStream o )
    {
        messaggio=mess;
        outVersoServer=o;
    }
    /**
     * l'evento
     * @param e 
     */
    public void actionPerformed(ActionEvent e ) {
        m=messaggio.getText();
        try {
            outVersoServer.writeBytes("tutti:::"+m+'\n');
        } catch (IOException ex) {
            Logger.getLogger(EventoInviaMess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

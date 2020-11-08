/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Ginevra
 * la classe che gestisce l'invio del messaggio nella chat privata
 * @param outVersoServer il DataOutputStream
 * @param m il messaggio ricavato dalla JTextField
 * @param nome il nome del client scelto per comunicare
 * @param messaggio la JtextField contenente il messaggio
 */
public class EventoInviaPriv implements ActionListener 
{
    private DataOutputStream outVersoServer;
    private String m,nome;
    private JTextField messaggio;
    /**
     * il costruttore con parametri
     * @param mess
     * @param o
     * @param n 
     */
    public EventoInviaPriv(JTextField mess,DataOutputStream o, String n )
    {
        nome=n;
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
            outVersoServer.writeBytes("Privato:::"+nome+":::"+m+'\n');
        } catch (IOException ex) {
            Logger.getLogger(EventoInviaMess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

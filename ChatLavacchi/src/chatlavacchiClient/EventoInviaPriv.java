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
 */
public class EventoInviaPriv implements ActionListener 
{
    DataOutputStream outVersoServer;
    String m,nome;
    JTextField messaggio;
    public EventoInviaPriv(JTextField mess,DataOutputStream o, String n )
    {
        nome=n;
        messaggio=mess;
        outVersoServer=o;
    }
    public void actionPerformed(ActionEvent e ) {
        m=messaggio.getText();
        try {
            outVersoServer.writeBytes("Privato:::"+nome+":::"+m+'\n');
        } catch (IOException ex) {
            Logger.getLogger(EventoInviaMess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.Color;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Ginevra
 */
public class EventoInvia implements ActionListener 
{
    private JTextField nome;
    private String n;
    DataOutputStream outVersoServer;
    public EventoInvia(JTextField f,DataOutputStream o )
    {
        nome=f;
        outVersoServer=o;
    }
    public void actionPerformed(ActionEvent e ) {
        n=nome.getText();
        try 
        {
            outVersoServer.writeBytes(n+'\n');
        } catch (IOException ex) 
        {
            Logger.getLogger(EventoInvia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

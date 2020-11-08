/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ginevra
 * 
* questa classe gestisce l'apertura della chat privata con un client scelto
* @param fp l'istanza del frame della chat privata
* @param nome il nome del client con cui si vuole comunicare
* @param b la comboBox dalla quale prendiamo l'elemento scelto
* @param outVersoServer //il DataOutputStream
* @param inDalServer //il BufferedReader
 */
public class EventoChatP implements ActionListener 
{
    FramePrivata fp;
    private String nome;
    private JComboBox b;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    /**
     * costruttore con parametri
     * @param f
     * @param o
     * @param i 
     */
    public EventoChatP(JComboBox f, DataOutputStream o, BufferedReader i)
    {
        b=f;
        outVersoServer=o;
        inDalServer=i;
    }
    /**
     * l'evento
     * @param e 
     */
    public void actionPerformed(ActionEvent e ) {
        nome=(String) b.getSelectedItem();
        fp=new FramePrivata(nome, outVersoServer,inDalServer);
    }
}

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ginevra
 */
public class EventoChatP implements ActionListener 
{
    FramePrivata fp;
    private String nome;
    private JComboBox b;
    DataOutputStream outVersoServer;
    public EventoChatP(JComboBox f, DataOutputStream o)
    {
        b=f;
        outVersoServer=o;
    }
    public void actionPerformed(ActionEvent e ) {
        nome=(String) b.getSelectedItem();
        fp=new FramePrivata(nome, outVersoServer);
    }
}

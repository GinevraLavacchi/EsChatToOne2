/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import javax.swing.*;

/**
 *
 * @author Ginevra
 */
public class FramePrivata extends JFrame
{
    JPanel p;
    String nome;
    JLabel label,benvenuto= new JLabel("BENVENUTO!!");
    JPanel chat;
    JTextField messaggio;
    JButton inviaMess;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    JScrollPane jsp;
    public FramePrivata(String nome,DataOutputStream outVersoServer,BufferedReader inDalServer )
    {
        this.outVersoServer=outVersoServer;
        this.inDalServer=inDalServer;
        this.nome=nome;
        p=new JPanel();
        p.setVisible( true);
        p.setBackground(Color.CYAN);
        this.add(p);
        label=new JLabel("<html> Stai comunicando con:"+nome+"</html>");
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx=0;
        c.gridy=0;
        c.insets =  new Insets(0,0,0,0);
        benvenuto.setFont(new Font("Times new Roman",Font.BOLD, 18));
        benvenuto.setForeground(Color.red);
        p.add(benvenuto,c);
        c.gridy++;
        p.add(label, c);
        c.gridy++;
        this.setVisible(true);
        this.setSize(800, 400);
        chat=new JPanel();//creo la label in cui vado a visualizzare i messaggi
        chat.setPreferredSize(new Dimension(300,190));
        //chat.setLayout(new FlowLayout());
        c.gridy++;
        //chat=rm.getPannello();
        jsp=new JScrollPane(chat);
        jsp.setPreferredSize(new Dimension(320,190));
        p.add(jsp,c);//la aggiungo al pannello
        chat.setVisible(true);
         RicevereMessPriv rmp=new RicevereMessPriv(jsp,chat,inDalServer);
        //jsp.setViewportView();
        Thread t=new Thread(rmp);
        t.start();
         SwingUtilities.updateComponentTreeUI(chat);
        JLabel m=new JLabel("Messaggio-->");
        c.gridy++;
        p.add(m, c);
        messaggio=new JTextField();//creo la JTextField
        messaggio.setPreferredSize(new Dimension(70, 20));
        messaggio.setBounds(50,150,150,20);
        c.gridx++;
        p.add(messaggio, c);//la aggiungo al pannello
        inviaMess=new JButton("Invia");//creo il bottone
        c.gridx++;
        p.add(inviaMess, c);
        EventoInviaPriv ei=new EventoInviaPriv(messaggio, outVersoServer,nome);//creo l'evento per inviare il nome
        inviaMess.addActionListener(ei);
        c.gridy++;
        c.gridx--;
        p.add(inviaMess, c);//aggiungo il bottone al pannello*/
        chat.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}


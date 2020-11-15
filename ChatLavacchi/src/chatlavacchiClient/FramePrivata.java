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
 * la classe del frame della chat privata
 * @param p il JPanel
 * @param nome il nome del client con cui stiamo comunicando
 * @param label la label che dice con chi stai comunicando
 * @param benvenuto contiene "BENVENUTO!!"
 * @param chat il pannello che contiene le label della chat
 * @param messaggio il JTextField contenente il messggio
 * @param inviaMess il JButton che invia il messaggio al server
 * @param outVersoServer il DataOutputStream
 * @param inDalServer il BufferedReader
 * @param jsp lo ScrollPane contenente la chat
 */
public class FramePrivata extends JFrame
{
    private JPanel p;
    private String nome;
    private JLabel label,benvenuto= new JLabel("BENVENUTO!!");
    private JPanel chat;
    private JTextField messaggio;
    private JButton inviaMess;
    private DataOutputStream outVersoServer;
    private BufferedReader inDalServer;
    private JScrollPane jsp;
    /**
     * il costruttore con parametri
     * @param nome
     * @param outVersoServer
     * @param inDalServer 
     */
    public FramePrivata(String nome,DataOutputStream outVersoServer,BufferedReader inDalServer )
    {
        this.outVersoServer=outVersoServer;
        this.inDalServer=inDalServer;
        this.nome=nome;
        ////////////////////////////////
        PannelloSottoPriv psp=new PannelloSottoPriv();
        this.add(psp);
        p=new JPanel();//creo il pannello
        p.setVisible( true);//lo rendo visibile
        p.setOpaque(false);
        psp.add(p);
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx=0;
        c.gridy=0;
        c.insets =  new Insets(0,0,0,0);
        ////////////////////////////////
        benvenuto.setFont(new Font("Times new Roman",Font.BOLD, 18));
        benvenuto.setForeground(Color.red);
        p.add(benvenuto,c);
        ////////////////////////////////
        label=new JLabel("<html> Stai comunicando con:"+nome+"</html>");
        c.gridy++;
        label.setForeground(Color.white);
        p.add(label, c);
        ////////////////////////////////
        c.gridy++;
        chat=new JPanel();//creo la label in cui vado a visualizzare i messaggi
        chat.setPreferredSize(new Dimension(300,190));
        c.gridy++;
        jsp=new JScrollPane(chat);
        jsp.setPreferredSize(new Dimension(320,190));
        p.add(jsp,c);//la aggiungo al pannello
        chat.setVisible(true);
        chat.setBorder(BorderFactory.createLineBorder(Color.black));
        RicevereMessPriv rmp=new RicevereMessPriv(jsp,chat,inDalServer);
        //jsp.setViewportView();
        Thread t=new Thread(rmp);
        t.start();
        SwingUtilities.updateComponentTreeUI(chat);
        ////////////////////////////////
        messaggio=new JTextField("messaggio");//creo la JTextField
        messaggio.setPreferredSize(new Dimension(70, 20));
        messaggio.setBounds(50,150,150,20);
        c.gridy++;
        p.add(messaggio, c);//la aggiungo al pannello
        ////////////////////////////////
        inviaMess=new JButton("Invia");//creo il bottone
        EventoInviaPriv ei=new EventoInviaPriv(messaggio, outVersoServer,nome);//creo l'evento per inviare il nome
        inviaMess.addActionListener(ei);
        c.gridy++;
        p.add(inviaMess, c);//aggiungo il bottone al pannello
        ////////////////////////////////
        this.setVisible(true);
        this.setSize(790, 400);
    }
}


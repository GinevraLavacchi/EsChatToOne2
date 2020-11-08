/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Ginevra
 */
public class FrameGruppo extends JFrame
{
    private JPanel pannello,pannello1,chat;
    private JLabel benvenuto, chatpriv,mess;
    private JTextField nome,messaggio;
    private JButton inviaNome,inviaMess;
    private JComboBox elenco=new JComboBox();
    private String[] momentaneo;
    //private String[] momentaneo={"ciccio","io"};
    private String appo;
    private JScrollPane jsp;
     Socket socket;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    String nomeserver="localhost";
    int portaserver=1234;
    Image img;
    public FrameGruppo() throws IOException
    {
       
        socket=new Socket(nomeserver,portaserver);//creo un socket
        //associo due oggetti al socket per effettuare la lettura e la scrittura
        outVersoServer=new DataOutputStream(socket.getOutputStream());
        inDalServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        /////////////////////////////
        this.setLayout(new GridBagLayout());
        GridBagConstraints x = new GridBagConstraints();
        x.anchor = GridBagConstraints.CENTER;
        x.gridx=0;
        x.gridy=0;
        x.insets =  new Insets(0,0,0,0);
        ///////////////////////////////
        pannello=new JPanel();//creo il pannello
        pannello.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx=0;
        c.gridy=0;
        c.insets =  new Insets(0,0,0,0);
        benvenuto= new JLabel("BENVENUTO!!");
        pannello.add(benvenuto,c);//aggiungo la label al pannello
        benvenuto.setFont(new Font("Times new Roman",Font.BOLD, 18));
        benvenuto.setForeground(Color.red);
        pannello.setVisible(true);//rendo visibile la label
        JLabel n=new JLabel("Nome:");
        c.gridy++;
        pannello.add(n, c);
        n.setLocation(50, 0);
        nome=new JTextField();//creo la JTextField
        nome.setPreferredSize(new Dimension(70, 20));
        nome.setBounds(50,150,150,20);
        c.gridx++;
        pannello.add(nome, c);//la aggiungo al pannello
        inviaNome=new JButton("Invia");//creo il bottone
        EventoInvia ei=new EventoInvia(nome,outVersoServer,elenco);//creo l'evento per inviare il nome
        inviaNome.addActionListener(ei);
        c.gridy++;
        c.gridx--;
        pannello.add(inviaNome, c);//aggiungo il bottone al pannello
        
        chat=new JPanel();//creo la label in cui vado a visualizzare i messaggi
        chat.setPreferredSize(new Dimension(300,190));
        //chat.setLayout(new FlowLayout());
        c.gridy++;
        //chat=rm.getPannello();
        jsp=new JScrollPane(chat);
        jsp.setPreferredSize(new Dimension(350,190));
        pannello.add(jsp,c);//la aggiungo al pannello
        chat.setVisible(true);
         RicevereMess rm=new RicevereMess(jsp,chat,inDalServer,elenco);
        //jsp.setViewportView();
        Thread t=new Thread(rm);
        t.start();
         SwingUtilities.updateComponentTreeUI(chat);
        /////////
        JLabel m=new JLabel("Messaggio-->");
        c.gridy++;
        pannello.add(m, c);
        messaggio=new JTextField();//creo la JTextField
        messaggio.setPreferredSize(new Dimension(70, 20));
        messaggio.setBounds(50,150,150,20);
        c.gridx++;
        pannello.add(messaggio, c);//la aggiungo al pannello
        inviaMess=new JButton("Invia");//creo il bottone
        EventoInviaMess em=new EventoInviaMess(messaggio, outVersoServer);//creo l'evento per inviare il nome
        inviaMess.addActionListener(em);
        c.gridy++;
        c.gridx--;
        pannello.add(inviaMess, c);//aggiungo il bottone al pannello
        chat.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(pannello,x);//aggiungo il pannello al frame
        this.setSize(790, 400);
        ////////////////////////////
        pannello1=new JPanel();//creo il secondo pannello che conterrà gli oggetti per avviare una chat privata
        pannello1.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.anchor = GridBagConstraints.CENTER;
        c1.gridx=0;
        c1.gridy=0;
        c1.insets =  new Insets(0,0,0,0);
        chatpriv=new JLabel("<html>Se vuoi iniziare una chat privata con un partecipante di questo gruppo scegli il suo nome</html>");//creo la label
        chatpriv.setPreferredSize(new Dimension(350,60));
        c1.gridy++;
        pannello1.add(chatpriv, c1);//la aggiungo al pannello
        chatpriv.setVisible(true);//la rendo visibile
        c1.gridy++;
        pannello1.add(elenco, c);
        EventoChatP ec=new EventoChatP(elenco,outVersoServer, inDalServer);
        elenco.addActionListener(ec);
        x.gridx++;
        this.add(pannello1,x);
        pannello.setBackground(Color.getHSBColor(0.9f,0.2f,0.9f));
        pannello1.setBackground(Color.getHSBColor(0.9f,0.2f,0.9f));
        this.setBackground(Color.getHSBColor(0.9f,0.2f,0.9f));
    }
    
}

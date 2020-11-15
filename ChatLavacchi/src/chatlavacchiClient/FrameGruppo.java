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
 * la classe del frame contentente la chat di gruppo
 * @param pannello il pannello contenente il pannello chat
 * @param chat il pannello contente i messaggi
 * @param pannello1 il pannello contenente la JComboBox
 * @param benvenuto la label di benvenuto
 * @param chatpriv la label che dice di scegliere il nome del client con cui si vuole comuncare in privato
 * @param mess la label che ti dice dove inserire il messaggio
 * @param nome il JTextField in cui si inserirà il nome
 * @param messaggio il JTextField in cui si inserirà il messaggio
 * @param inviaNome il bottone per inviare il nome
 * @param inviaMess il bottone per inviare il messaggio
 * @param elenco la JComboBox contenete l'elenco dei nomi
 * @param jsp lo ScrollPane contentente la chat
 * @param socket il socket
 * @param outVersoServer il DataOutputStream
 * @param inDalServer il BufferedReader
 * @param nomeserver il nome del server
 * @param portaserver il numero della porta
 */
public class FrameGruppo extends JFrame
{
    private JPanel pannello,pannello1,chat;
    private JLabel benvenuto, chatpriv,mess;
    private JTextField nome,messaggio;
    private JButton inviaNome,inviaMess;
    private static JComboBox elenco=new JComboBox();
    private JScrollPane jsp;
     Socket socket;
    private DataOutputStream outVersoServer;
    private BufferedReader inDalServer;
    private String nomeserver="localhost";
    private int portaserver=1234;
    /**
     * il costruttore
     * @throws IOException 
     */
    public FrameGruppo() throws IOException
    {
       
        socket=new Socket(nomeserver,portaserver);//creo un socket
        //associo due oggetti al socket per effettuare la lettura e la scrittura
        outVersoServer=new DataOutputStream(socket.getOutputStream());
        inDalServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        /////////////////////////////
        ////////////////////////////////
        PannelloSfondo panSotto=new PannelloSfondo();
        panSotto.setVisible(true);
        panSotto.setLayout(new GridBagLayout());
        GridBagConstraints x = new GridBagConstraints();
        x.anchor = GridBagConstraints.CENTER;
        x.gridx=0;
        x.gridy=0;
        x.insets =  new Insets(0,0,0,0);
        this.getContentPane().add(panSotto);
        ///////////////////////////////
        pannello=new JPanel();//creo il pannello
        pannello.setLayout(new GridBagLayout());
        pannello.setOpaque(false);
        pannello.setVisible(true);//rendo visibile il pannello
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx=0;
        c.gridy=0;
        c.insets =  new Insets(0,0,0,0);
        ////////////////////////////////
        benvenuto= new JLabel("BENVENUTO!!");
        pannello.add(benvenuto,c);//aggiungo la label al pannello
        benvenuto.setFont(new Font("Times new Roman",Font.BOLD, 18));
        benvenuto.setForeground(Color.red);
        ////////////////////////////////
        /*JLabel n=new JLabel("Nome:");
        n.setForeground(Color.WHITE);
        c.gridy++;
        pannello.add(n, c);
        n.setLocation(50, 0);*/
        ////////////////////////////////
        nome=new JTextField("nome");//creo la JTextField
        nome.setPreferredSize(new Dimension(70, 20));
        nome.setBounds(50,150,150,20);
        c.gridy++;
        pannello.add(nome, c);//la aggiungo al pannello
        ////////////////////////////////
        inviaNome=new JButton("Invia");//creo il bottone
        EventoInvia ei=new EventoInvia(nome,outVersoServer,elenco);//creo l'evento per inviare il nome
        inviaNome.addActionListener(ei);
        c.gridy++;
        c.gridx--;
        pannello.add(inviaNome, c);//aggiungo il bottone al pannello
        ////////////////////////////////
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
         chat.setBorder(BorderFactory.createLineBorder(Color.black));
        //jsp.setViewportView();
        Thread t=new Thread(rm);
        t.start();
         SwingUtilities.updateComponentTreeUI(chat);
        ////////////////////////////////
        /*JLabel m=new JLabel("Messaggio-->");
        m.setForeground(Color.WHITE);
        c.gridy++;
        pannello.add(m, c);*/
        ////////////////////////////////
        messaggio=new JTextField("messaggio");//creo la JTextField
        messaggio.setPreferredSize(new Dimension(70, 20));
        messaggio.setBounds(50,150,150,20);
        c.gridy++;
        pannello.add(messaggio, c);//la aggiungo al pannello
        ////////////////////////////////
        inviaMess=new JButton("Invia");//creo il bottone
        EventoInviaMess em=new EventoInviaMess(messaggio, outVersoServer);//creo l'evento per inviare il nome
        inviaMess.addActionListener(em);
        c.gridy++;
        pannello.add(inviaMess, c);//aggiungo il bottone al pannello
        ////////////////////////////////
       panSotto.add(pannello,x);//aggiungo il pannello al frame
        this.setSize(790, 440);
        ////////////////////////////////
        pannello1=new JPanel();//creo il secondo pannello che conterrà gli oggetti per avviare una chat privata
        pannello1.setLayout(new GridBagLayout());
        pannello1.setOpaque(false);
        GridBagConstraints c1 = new GridBagConstraints();
        c1.anchor = GridBagConstraints.CENTER;
        c1.gridx=0;
        c1.gridy=0;
        c1.insets =  new Insets(0,0,0,0);
        ////////////////////////////////
        chatpriv=new JLabel("<html>Se vuoi iniziare una chat privata con un partecipante di questo gruppo scegli il suo nome</html>");//creo la label
        chatpriv.setPreferredSize(new Dimension(350,60));
        chatpriv.setForeground(Color.WHITE);
        c1.gridy++;
        pannello1.add(chatpriv, c1);//la aggiungo al pannello
        chatpriv.setVisible(true);//la rendo visibile
        ////////////////////////////////
        c1.gridy++;
        pannello1.add(elenco, c1);
        EventoChatP ec=new EventoChatP(elenco,outVersoServer, inDalServer);
        JButton Partecipante=new JButton("INVIA");
        Partecipante.addActionListener(ec);
        c1.gridy++;
        pannello1.add(Partecipante, c1);
        ////////////////////////////////
        x.gridx++;
        panSotto.add(pannello1,x);
    }
    
}

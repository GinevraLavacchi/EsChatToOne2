/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
/**
 *
 * @author Ginevra
 * la classe che si occupa di riceve i messaggi
 * @param socket il socket
 * @param input_tastiera il BufferedReader per leggere i messaggi di risposta
 * @param risposta la risposta del sever
 * @param outVersoServer il DataOutputStream
 * @param inDalServer il BufferedReader
 */
class RicevereMess implements Runnable
{
    private Socket socket;
    private BufferedReader input_tastiera;
    private String risposta;
    private DataOutputStream outVersoServer;
    private BufferedReader inDalServer;
    private JPanel chat;
    private JScrollPane jsp;
    private JComboBox elenco;
    private ArrayList<String> elencoNomi;
    private boolean cont=false;
    /**
     * contruttore con parametro
     * @param c 
     */
    public RicevereMess(JScrollPane jsp,JPanel p,BufferedReader i,JComboBox elenco ){
        this.inDalServer=i;
        this.chat=p;
        this.jsp=jsp;
        this.elenco=elenco;
        elencoNomi=new ArrayList<String>();
        
    }
    @Override
    /**
     * override del metodo run dei thread, si occupa di gestire l'uscita
     */
    public void run() {
        for(;;){
            try {
                
                risposta=inDalServer.readLine();
                String[] appoggio=risposta.split(":::");
                /*if(appoggio[1].toUpperCase().equals("ADDIO")){
                    return;
                }*/
                if(appoggio[0].equals("tutti"))//controllo se il messaggio va visualizzato perchè è per tutti
                {
                    String appo=appoggio[1]+appoggio[2]+appoggio[3];//salvo il messaggio
                    System.out.println(appo);
                    String nome=appoggio[2];//salvo il nome
                    System.out.println(nome);
                    //System.out.println("fuori for"+"-->numero elementi"+elenco.getItemCount());
                    
                    if(elencoNomi.isEmpty())//guardo se l'elenco è vuoto
                    {
                        elencoNomi.add(nome);
                        elenco.addItem(nome);
                    }
                    else
                    {
                        elencoNomi.forEach((c) -> //controllo che non sia già presente nell'array
                        {
                            for(int i=0;i<elencoNomi.size();i++)
                            {
                                if(c.equals(elencoNomi.get(i)))
                                {
                                    cont=true;
                                }
                            }
                        });
                        if(cont==false)
                        {
                            elenco.addItem(nome);
                        }
                    }
                    
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>");//creo la label con il messaggio
                    nuovo.setPreferredSize(new Dimension(300,20));
                    nuovo.setAlignmentX(Component.LEFT_ALIGNMENT);
                    nuovo.setBorder(new LineBorder(Color.black, 1, true));
                    nuovo.setVisible(true);
                    Box b= Box.createVerticalBox() ;
                    b.add(nuovo);
                    chat.add(b);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                }
               if(appoggio[0].equals("avviso"))//controllo se fa parte degli avvisi
                {
                    String appo=appoggio[1];
                    System.out.println(appo);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>",SwingConstants.RIGHT);//creo la label
                    nuovo.setBorder(new LineBorder(Color.red, 1, true));
                    nuovo.setPreferredSize(new Dimension(300,20));
                    nuovo.setVisible(true);
                    chat.add(nuovo);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                }
                if(appoggio[0].equals("nome"))//controllo se è l'avviso di connesione
                {
                    String appo=appoggio[1]+appoggio[2];
                    System.out.println(appo);
                    String nome=appoggio[1];
                    System.out.println(nome);
                    elenco.addItem(nome);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>",SwingConstants.RIGHT);//creo la label
                    nuovo.setBorder(new LineBorder(Color.red, 1, true));
                    nuovo.setPreferredSize(new Dimension(300,20));
                    nuovo.setVisible(true);
                    chat.add(nuovo);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                }
                if(appoggio[0].equals("avvisoDisc"))//controllo se è l'avviso di disconnessione
                {
                    String appo=appoggio[1]+appoggio[2];
                    String nome=appoggio[1];
                    System.out.println(appo);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>",SwingConstants.RIGHT);//creo la label
                    elenco.removeItem(nome);
                    nuovo.setBorder(new LineBorder(Color.red, 1, true));
                    nuovo.setPreferredSize(new Dimension(300,20));
                    nuovo.setVisible(true);
                    chat.add(nuovo);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                }
                SwingUtilities.updateComponentTreeUI(chat);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server.");
                System.exit(1);
            }
        }
    }
}

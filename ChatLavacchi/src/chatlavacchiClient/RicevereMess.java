/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;
import java.awt.Color;
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
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
/**
 *
 * @author Ginevra
 */
class RicevereMess implements Runnable
{
    /**
     * @param socket
     * @param input_tastiera
     * @param messaggio
     * @param risposta
     * @param outVersoServer
     * @param inDalServer
     */
    Socket socket;
    BufferedReader input_tastiera;
    String messaggio, risposta;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    private JPanel chat;
    JScrollPane jsp;
    JComboBox elenco;
    ArrayList<String> elencoNomi;
    boolean cont=false;
    /**
     * contruttore con parametro
     * @param c 
     */
    public RicevereMess(JScrollPane jsp,JPanel p,BufferedReader i,JComboBox elenco ){
        //this.input_tastiera=c.input_tastiera;
        /*this.messaggio=c.messaggio;
        this.risposta=c.risposta;*/
        //this.outVersoServer=c.outVersoServer;
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
                if(appoggio[0].equals("tutti"))
                {
                    String appo=appoggio[1]+appoggio[2]+appoggio[3];
                    System.out.println(appo);
                    String nome=appoggio[2];
                    System.out.println(nome);
                    System.out.println("fuori for"+"-->numero elementi"+elenco.getItemCount());
                    
                    if(elencoNomi.isEmpty())
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
                    /*elencoNomi.add(nome);
                    String[] array = new String[elencoNomi.size()];
                    for(int i = 0; i < array.length; i++) {
                        array[i] = elencoNomi.get(i);
                    }
                    //String[] array = elencoNomi.toArray(new String[elencoNomi.size()]);
                    elenco= new JComboBox(array);
                    //elenco.addItem(elencoNomi.toString());
                    */
                    
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>");
                    nuovo.setPreferredSize(new Dimension(300,40));
                    nuovo.setBorder(new LineBorder(Color.black, 2, true));
                    nuovo.setVisible(true);
                    Box b= Box.createVerticalBox() ;
                    b.add(nuovo);
                    
                    chat.add(b);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                    
                }
               if(appoggio[0].equals("avviso"))
                {
                    String appo=appoggio[1];
                    System.out.println(appo);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>");
                    nuovo.setBorder(new LineBorder(Color.red, 2, true));
                    //JTextArea nuovo=new JTextArea("<html>"+risposta+"</html>");
                    nuovo.setPreferredSize(new Dimension(300,40));
                    nuovo.setVisible(true);
                    chat.add(nuovo);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                }
                if(appoggio[0].equals("nome"))
                {
                    String appo=appoggio[1]+appoggio[2];
                    System.out.println(appo);
                    String nome=appoggio[1];
                    System.out.println(nome);
                    elenco.addItem(nome);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>");
                    nuovo.setBorder(new LineBorder(Color.red, 2, true));
                    //JTextArea nuovo=new JTextArea("<html>"+risposta+"</html>");
                    nuovo.setPreferredSize(new Dimension(300,40));
                    nuovo.setVisible(true);
                    chat.add(nuovo);
                    jsp.setViewportView(chat);
                    chat.setPreferredSize(new Dimension(100,chat.getHeight()+40));
                }
                if(appoggio[0].equals("avvisoDisc"))
                {
                    String appo=appoggio[1];
                    System.out.println(appo);
                    JLabel nuovo=new JLabel("<html>"+appo+"</html>");
                    nuovo.setBorder(new LineBorder(Color.red, 2, true));
                    //JTextArea nuovo=new JTextArea("<html>"+risposta+"</html>");
                    nuovo.setPreferredSize(new Dimension(300,40));
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

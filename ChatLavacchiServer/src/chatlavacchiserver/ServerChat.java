/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiserver;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Ginevra
 */
public class ServerChat implements Runnable
{
    /**
     * @param socketclient
     * @param messaggio
     * @param nomeclient
     * @param inDalClient
     * @param outVersoClient
     * @param client
     * @param outVersoClient2
     */
    Socket socketclient=null;
    String messaggio=null;
    String nomeclient=null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ArrayList<Socket> client;
    DataOutputStream outVersoClient2;
    /**
     * costruttore con parametri
     * @param s
     * @param a 
     */
    public ServerChat(Socket s,ArrayList<Socket> a){
        socketclient=s;
        client=a;
        try {
            inDalClient=new BufferedReader(new InputStreamReader(socketclient.getInputStream()));
            outVersoClient=new DataOutputStream(socketclient.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore nell'istanza dei canali di comunicazione.");
            System.exit(0);
        }
    }
    /**
     * override del metodo run
     */
    public void run() {
        try {
            comunicazione();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
    /**
     * gestisce la comunicazione tra i vari client connessi
     * @throws IOException 
     */
    public void comunicazione() throws IOException{
        System.out.println(Thread.currentThread().getName()+"-->"+"In attesa del nome del client.");
        messaggio=inDalClient.readLine();//legge il nome del client
        nomeclient=messaggio;
        System.out.println(nomeclient+" >> connesso.");
        if(client.size()>1)//controllo se ci sono almeno 2 client connessi
        {
            client.forEach((c) -> //invio il mess ad ogni client connesso
            {
                if (!c.equals(this.socketclient)) //per comunicare che si è connesso un altro client, controllo che sia diverso da quelli gia esisenti nell'array
                {
                    try 
                    {
                        outVersoClient2=new DataOutputStream(c.getOutputStream());
                        outVersoClient2.writeBytes(nomeclient+" si e' connesso.\n");
                    }
                    catch (IOException e) 
                    {
                        System.out.println(e.getMessage());
                        System.out.println(Thread.currentThread().getName()+" >> "+"Errore nella comunicazione col partner del client.");
                        System.exit(1);
                    }
                }
            });
        }
        else//se c'è solo un client connesso
        {
            try 
            {
                outVersoClient.writeBytes("Sei l'unico utente attualemente connesso.\n");
            } 
            catch (IOException e) 
            {
                System.out.println(e.getMessage());
                System.out.println("Errore nella comunicazione col partner del client.");
                System.exit(1);
            }
        }
        
        for(;;)
        {
            System.out.println(Thread.currentThread().getName()+" >> "+"In attesa del messaggio da parte del client.");
            messaggio=inDalClient.readLine();
            if(inDalClient==null||messaggio.toUpperCase().equals("ADDIO"))//guardo se un client ha deciso di uscire
            {
                outVersoClient.writeBytes("ADDIO\n");
                if(client.size()>1)//se ci sono almeno 2 client ancora collegati avviso che uno si è disconnesso
                {
                    client.forEach((c) -> 
                    {
                        if (!c.equals(this.socketclient)) 
                        {
                            try {
                                outVersoClient2=new DataOutputStream(c.getOutputStream());
                                outVersoClient2.writeBytes(nomeclient+" si e' disconnesso.\n");
                            }
                            catch (IOException e) {
                                System.out.println(e.getMessage());
                                System.out.println(Thread.currentThread().getName()+": "+"Errore nella comunicazione");
                                System.exit(1);
                            }
                        }
                    });
                }
                break;
            }
            else// se vuole continuare la comunicazione
            {
                if(client.size()>1)//controllo che siano almeno in 2
                {
                    client.forEach((partner) -> //invio il messaggio agli altri client
                    {
                        if(!partner.equals(this.socketclient))//evito di inviarlo a me stesso
                        {
                            try 
                            {
                                outVersoClient2=new DataOutputStream(partner.getOutputStream());
                                outVersoClient2.writeBytes("Da: "+nomeclient+"\nTesto: "+messaggio+'\n');
                            }
                            catch (IOException e) 
                            {
                                try 
                                {
                                    System.out.println(e.getMessage());
                                    outVersoClient.writeBytes("Errore durante la comunicazione col partner");
                                }
                                catch (IOException ex) 
                                {
                                    System.out.println(ex.getMessage());
                                    System.out.println("Errore nella comunicazione col client.");
                                    System.exit(1);
                                }
                            }
                        }
                    });
                }
                else//se è rimasto solo 1 client
                {
                    outVersoClient.writeBytes("si sono tutti disconnessi\n");
                }
            }
        }
        //chiudo la comunicazione
        client.remove(socketclient);
        System.out.println("Comunicazione terminata.");
        outVersoClient.close();
        inDalClient.close();
        socketclient.close();
    }
}

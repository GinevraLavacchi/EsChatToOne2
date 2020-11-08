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
    Contenitore contenitoresocketclient=null;
    String messaggio=null;
    String nomeclient=null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ArrayList<Contenitore> client;
    DataOutputStream outVersoClient2;
    /**
     * costruttore con parametri
     * @param s
     * @param a 
     */
    public ServerChat(Socket s,ArrayList<Contenitore> a){
        contenitoresocketclient=new Contenitore(s);
        client=a;
        try {
            inDalClient=new BufferedReader(new InputStreamReader(contenitoresocketclient.getMySocket().getInputStream()));
            outVersoClient=new DataOutputStream(contenitoresocketclient.getMySocket().getOutputStream());
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
            //comunicazione();
            comunicazione();
            //comunicazioneOneToOne();
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
        boolean comunicazione=false;
        while(!comunicazione)
        {
            outVersoClient.writeBytes("tutti:::"+" devi inserire il tuo nome per primo.\n");
            nomeclient=inDalClient.readLine();//legge il nome del client
            System.out.println(nomeclient+" >> connesso.");
            comunicazione=true;
        }
        System.out.println("PUO INIZIARE");
        outVersoClient.writeBytes("tutti:::"+" puoi iniziare a scrivere.\n");
        
        if(client.size()>1)//controllo se ci sono almeno 2 client connessi
        {
            client.forEach((c) -> //invio il mess ad ogni client connesso
            {
                if (!c.equals(this.contenitoresocketclient.getMySocket())) //per comunicare che si è connesso un altro client, controllo che sia diverso da quelli gia esisenti nell'array
                {
                    try 
                    {
                        outVersoClient2=new DataOutputStream(c.getMySocket().getOutputStream());
                        outVersoClient2.writeBytes("tutti:::"+nomeclient+" si e' connesso.\n");
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
                outVersoClient.writeBytes("tutti:::"+"Sei l'unico utente attualemente connesso.\n");
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
            String[] appoggio=messaggio.split(":::");
            
            if(inDalClient==null||appoggio[1].toUpperCase().equals("ADDIO"))//guardo se un client ha deciso di uscire
            {
                outVersoClient.writeBytes("ADDIO\n");
                if(client.size()>1)//se ci sono almeno 2 client ancora collegati avviso che uno si è disconnesso
                {
                    client.forEach((c) -> 
                    {
                        if (!c.equals(this.contenitoresocketclient.getMySocket())) 
                        {
                            try {
                                outVersoClient2=new DataOutputStream(c.getMySocket().getOutputStream());
                                outVersoClient2.writeBytes("tutti:::"+nomeclient+" si e' disconnesso.\n");
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
                    if(appoggio[0].equals("tutti"))
                    {
                        client.forEach((partner) -> //invio il messaggio agli altri client
                        {
                            if(!partner.equals(this.contenitoresocketclient.getMySocket()))//evito di inviarlo a me stesso
                            {
                                try 
                                {
                                    outVersoClient2=new DataOutputStream(partner.getMySocket().getOutputStream());
                                    outVersoClient2.writeBytes("tutti:::"+"Da: "+nomeclient+"--> Testo: "+appoggio[1]+'\n');
                                }
                                catch (IOException e) 
                                {
                                    try 
                                    {
                                        System.out.println(e.getMessage());
                                        outVersoClient.writeBytes("tutti:::"+"Errore durante la comunicazione col partner");
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
                    else
                    {
                        inviaSingoloMess(appoggio);
                    }
                    
                }
                else//se è rimasto solo 1 client
                {
                    outVersoClient.writeBytes("tutti:::"+"si sono tutti disconnessi\n");
                }
            }
        }
        //chiudo la comunicazione
        client.remove(contenitoresocketclient);
        System.out.println("Comunicazione terminata.");
        outVersoClient.close();
        inDalClient.close();
        contenitoresocketclient.getMySocket().close();
    }
    public void inviaSingoloMess(String[] appo) throws IOException
    {
        client.forEach((partner) -> //invio il messaggio agli altri client
        {
            if(partner.getNomeUtente().equals(appo[0]))
            {
                try 
                {
                    outVersoClient2=new DataOutputStream(partner.getMySocket().getOutputStream());
                    outVersoClient2.writeBytes("Privato::: Da: "+nomeclient+"\nTesto: "+appo[1]+'\n');
                }
                catch (IOException e) 
                {
                    try 
                    {
                        System.out.println(e.getMessage());
                        outVersoClient.writeBytes("Privato::: Da: "+"Errore durante la comunicazione col partner");
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
    
    
    
    
    
    public void comunicazioneOneToOne() throws IOException
    {
        if(client.size()>1)//controllo se ci sono almeno 2 client connessi
        {
            client.forEach((c) -> //invio il mess ad ogni client connesso
            {
                if (!c.equals(this.contenitoresocketclient.getMySocket())) //per comunicare che si è connesso un altro client, controllo che sia diverso da quelli gia esisenti nell'array
                {
                    try 
                    {
                        outVersoClient2=new DataOutputStream(c.getMySocket().getOutputStream());
                        outVersoClient2.writeBytes(nomeclient+" si e' connesso.\n");
                        outVersoClient.writeBytes("con chi vuoi comunicare tra:"+'\n');
                            for(int i=0;i<client.size();i++)
                            {
                                outVersoClient.writeBytes(client.get(i).getNomeUtente()+'\n');
                            }
                            String nomesecondo=inDalClient.readLine();
                            boolean controllo=false;
                            for(int i=0;i<client.size();i++)
                            {
                                if(nomesecondo.equals(client.get(i).getNomeUtente()))
                                {
                                    messaggio=inDalClient.readLine();
                                    outVersoClient2=new DataOutputStream(client.get(i).getMySocket().getOutputStream());
                                    outVersoClient2.writeBytes("Da: "+contenitoresocketclient.getNomeUtente()+"\nTesto: "+messaggio+'\n');
                                    controllo=true;//se l'ha trovato
                                }
                            }
                            if(!controllo)
                            {
                                outVersoClient.writeBytes("Non esiste nessun client con quel nome utente");
                            }
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
        /////////////////////////////////////////////
        /*outVersoClient.writeBytes("con chi vuoi comunicare tra:"+'\n');
        for(int i=0;i<client.size();i++)
        {
            outVersoClient.writeBytes(client.get(i).getNomeUtente()+'\n');
        }
        String nomesecondo=inDalClient.readLine();
        boolean controllo=false;
        for(int i=0;i<client.size();i++)
        {
            if(nomesecondo.equals(client.get(i).getNomeUtente()))
            {
                messaggio=inDalClient.readLine();
                outVersoClient2=new DataOutputStream(client.get(i).getMySocket().getOutputStream());
                outVersoClient2.writeBytes("Da: "+contenitoresocketclient.getNomeUtente()+"\nTesto: "+messaggio+'\n');
                controllo=true;//se l'ha trovato
            }
        }
        if(!controllo)
        {
            outVersoClient.writeBytes("Non esiste nessun client con quel nome utente");
        }*/
    }
}

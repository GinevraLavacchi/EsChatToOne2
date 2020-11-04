/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiserver;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 *
 * @author Ginevra
 */
public class MultiServer 
{
    /**
     * @param serversocket
     * @param client
     */
    ServerSocket serversocket;
    ArrayList<Contenitore> client=new ArrayList();
    /**
     * il metodo start si occupa della connessione dei client e dell'assegnazione ai thread
     */
    public void start()
    {
        try 
        {
            serversocket=new ServerSocket(1234);//creo un server sulla porta 1234
            for(;;)
            {
                System.out.println("Nuovo thread in attesa di un client.");
                Socket clientsocket=serversocket.accept();//rimane in attesa del client
                client.add(new Contenitore(clientsocket));//aggiunge il client all'array
                ServerChat serverthread=new ServerChat(clientsocket,client); //crea un oggetto ServerChat a cui passa il socket e l'array
                Thread t=new Thread(serverthread); //l'oggetto viene assegnato ad un thread
                t.start();//il thread viene fatto partire
            }
        }
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("Errore nell'istanza del server.");
            System.exit(1);
        }
        try {
            serversocket.close();//termina la connessione
        }
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("Errore nella chiusura del server.");
            System.exit(1);
        }
    }
}

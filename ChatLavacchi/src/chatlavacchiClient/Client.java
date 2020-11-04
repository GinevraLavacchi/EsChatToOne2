/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;
import java.io.*;
import java.net.*;
/**
 *
 * @author Ginevra
 */
public class Client 
{
    /**
     *@param nomeserver
     * @param portaserver
     * @param socket;
     * @param iinput_tastiera
     * @param messaggio
     * @param risposta
     * @param outVersoServer
     * @param inDalServer
     * @param im
     * @param rm
     */
    String nomeserver="localhost";
    int portaserver=1234;
    Socket socket;
    BufferedReader input_tastiera;
    String messaggio,risposta;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    InvioMess im;
    RicevereMess rm;
    /**
     *questo metodo si occupa della connessione del client al server
     * @return socket
    */
    public Socket connetti(){
        System.out.println("Client in esecuzione.");
        try {
            input_tastiera=new BufferedReader(new InputStreamReader(System.in));//per l'input da tastiera
            socket=new Socket(nomeserver,portaserver);//creo un socket
            //associo due oggetti al socket per effettuare la lettura e la scrittura
            outVersoServer=new DataOutputStream(socket.getOutputStream());
            inDalServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Inserire nome utente a scelta:");
            messaggio=input_tastiera.readLine();
            outVersoServer.writeBytes(messaggio+'\n');
        }
        catch(UnknownHostException e){
            System.out.println("Host non riconosciuto.");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione.");
            System.exit(1);
        }
        return(socket);
    }
    /**
     * questo metodo si occupa della comunicazione tra client e server creando delle istanze delle classe RicevreMess e InvioMess
     * che poi vengono passate ai thread
     */
    public void comunica(){
        im=new InvioMess(this);
        rm=new RicevereMess(this);
        Thread threadi=new Thread(im);
        Thread threadr=new Thread(rm);
        threadi.start();
        threadr.start();
    }
}

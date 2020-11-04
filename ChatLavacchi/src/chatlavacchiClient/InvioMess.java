/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Ginevra
 */
class InvioMess implements Runnable
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
    String messaggio,risposta;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    /**
     * contruttore con parametro
     * @param c 
     */
    public InvioMess(Client c)
    {
        this.input_tastiera=c.input_tastiera;
        this.messaggio=c.messaggio;
        this.risposta=c.risposta;
        this.outVersoServer=c.outVersoServer;
        this.inDalServer=c.inDalServer;
    }
    /**
     * override del metodo run dei thread, si occupa di gestire l'uscita
     */
    public void run() {
        for(;;){
            try {
                System.out.println("Per chiudere la connessione digitare 'addio'");
                System.out.println("Inserire il messaggio da inviare:");
                messaggio=input_tastiera.readLine();
                outVersoServer.writeBytes(messaggio+'\n');
                if(messaggio.toUpperCase().equals("ADDIO")){
                    System.out.println("Chiusura dell'esecuzione.");
                    return;
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server.");
                System.exit(1);
            }
        }
    }
}

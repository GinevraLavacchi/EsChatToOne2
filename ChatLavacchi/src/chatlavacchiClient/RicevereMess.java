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
    /**
     * contruttore con parametro
     * @param c 
     */
    public RicevereMess(Client c){
        this.input_tastiera=c.input_tastiera;
        this.messaggio=c.messaggio;
        this.risposta=c.risposta;
        this.outVersoServer=c.outVersoServer;
        this.inDalServer=c.inDalServer;
    }
    @Override
    /**
     * override del metodo run dei thread, si occupa di gestire l'uscita
     */
    public void run() {
        for(;;){
            try {
                risposta=inDalServer.readLine();
                if(risposta.toUpperCase().equals("ADDIO")){
                    return;
                }
                System.out.println(risposta);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server.");
                System.exit(1);
            }
        }
    }
}

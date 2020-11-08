/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiserver;

import java.net.Socket;

/**
 *
 * @author Ginevra
 * La classe che contiene il socket e il nome di ogni client
 * @param mySocket il socket
 * @param nomeUtente il nome del client
 */
public class Contenitore {
    private Socket mySocket;
    private String nomeUtente;
    /**
     * il costruttore con parametri
     * @param s
     * @param n 
     */
    public Contenitore(Socket s,String n)
    {
       mySocket=s;
       nomeUtente=n;
    }
    /**
     * costruttore con parametro
     * @param s 
     */
    public Contenitore(Socket s)
    {
        mySocket=s;
    }
    /**
     * il set del nome utente
     * @param n 
     */
    public void setNomeUtente(String n)
    {
        nomeUtente=n;
    }
    /**
     * il get del nome utente
     * @return 
     */
    public String getNomeUtente()
    {
        return nomeUtente;       
    }
    /**
     * il get del socket
     * @return 
     */
    public Socket getMySocket()
    {
        return mySocket;
    }
}

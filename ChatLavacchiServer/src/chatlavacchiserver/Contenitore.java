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
 */
public class Contenitore {
    private Socket mySocket;
    private String nomeUtente;
    public Contenitore(Socket s,String n)
    {
       mySocket=s;
       nomeUtente=n;
    }
    public Contenitore(Socket s)
    {
        mySocket=s;
    }
    public void setNomeUtente(String n)
    {
        nomeUtente=n;
    }
    public String getNomeUtente()
    {
        return nomeUtente;       
    }
    public Socket getMySocket()
    {
        return mySocket;
    }
}

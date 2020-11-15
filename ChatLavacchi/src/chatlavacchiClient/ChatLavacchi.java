/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;

/**
 *
 * @author Ginevra
 */
public class ChatLavacchi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

    /**
     * @param args the command line arguments
     */
       
        FrameGruppo f=new FrameGruppo();
        f.setVisible(true);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //f.setDefaultCloseOperation (FrameGruppo.DO_NOTHING_ON_CLOSE);
            // nessun else  cosi' come scritto prima nn facciamo nulla  }}

    }
}
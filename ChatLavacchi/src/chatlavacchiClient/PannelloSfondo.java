/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ginevra
 * la classe che visualizza lo sfondo della chat di gruppo
 * @param Image l'immagine di sfondo
 */
public class PannelloSfondo extends JPanel{
    Image image;
    /**
     * costruttore
     */
    public PannelloSfondo()
    {
        try
        {
            this.setOpaque(true);
           // icon = new ImageIcon("../img/img1.jpg");
            //img= ImageIO.read(new File("../img/img1.jpg"));
            Toolkit tk = Toolkit.getDefaultToolkit();
            image=ImageIO.read(getClass().getResource("../img/img1.jpg"));
            //image = tk.getImage("../img/img1.jpg"); 
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(image,1); 
            try { mt.waitForID(1); }
            catch (InterruptedException e) {}
            
        }
        catch(Exception e)
        {}
    }
    /**
     * visualizza l'immagine di sfondo
     * @param g 
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlavacchiClient;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Ginevra
 */
public class PannelloSottoPriv extends JPanel{
    Image image;
    public PannelloSottoPriv()
    {
        try
        {
            this.setOpaque(true);
           // icon = new ImageIcon("../img/img1.jpg");
            //img= ImageIO.read(new File("../img/img1.jpg"));
            Toolkit tk = Toolkit.getDefaultToolkit();
            image=ImageIO.read(getClass().getResource("../img/img2.jpg"));
            //image = tk.getImage("../img/img1.jpg"); 
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(image,1); 
            try { mt.waitForID(1); }
            catch (InterruptedException e) {}
            
        }
        catch(Exception e)
        {}
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient2;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author X
 */
public class ChatClient2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Okno Moje = new Okno();
        Moje.setVisible(true);
        Moje.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
    }
}

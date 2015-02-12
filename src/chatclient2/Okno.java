package chatclient2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;


public class Okno extends JFrame{
    private final JButton Wyslij;
    
    //glowne okno wszystkich wiadomosci
    public final JTextArea Tekst;
    
    //okno pojedynczej wiadomosci
    private final JTextArea Tresc;
    public static final int PORT=50007;
    public ChatClient client;
    public JScrollPane sp;
    
    public Okno() throws IOException
    {
        super();
        this.setSize(300, 400);
        this.setLayout(new BorderLayout());
        this.setTitle("ChatClient2");
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        this.Tresc = new JTextArea();
        this.Tresc.setLineWrap(true);
        content.add(Tresc);
        this.Wyslij = new JButton("Wyslij");
        this.Wyslij.addActionListener(new Wyslij());
        content.add(Wyslij);     
        this.add(content,BorderLayout.SOUTH);
        this.Tekst = new JTextArea();
        this.Tekst.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(this.Tekst);
        this.add(jsp,BorderLayout.CENTER);
        this.client = new ChatClient("127.0.0.1",50007,this);    

        
        
    }
    
    class Wyslij implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                   client.send(Tresc.getText());
                   Tresc.setText("");
                }
                catch(Exception el)
                {
                    
                    
                }
            }
            
        }
}
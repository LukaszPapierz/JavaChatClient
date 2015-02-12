/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient2;

/**
 *
 * @author X
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient implements Runnable
{  
   private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   private ChatClientThread client    = null;
   private Okno ok;

   public ChatClient(String serverName, int serverPort, Okno ok)
   {  
      this.ok = ok;
      this.ok.Tekst.append("Trwa łaczenie ...");
      try
      {  socket = new Socket(serverName, serverPort);
         this.ok.Tekst.append("Poloczono: " + socket);
         start();
      }
      catch(UnknownHostException uhe)
      {  
          this.ok.Tekst.append("blad hosta: " + uhe.getMessage()); 
      }
      catch(IOException ioe)
      {  
          this.ok.Tekst.append("ERROR: " + ioe.getMessage()); 
      }
   }
   
   public void run()
   {  
      while (thread != null)
      {  
         try
         {  streamOut.writeUTF(console.readLine());
            streamOut.flush();
         }
         catch(IOException ioe)
         {  
            this.ok.Tekst.append("ERROR: " + ioe.getMessage());
            stop();
         }
      }
   }
   
   public void handle(String msg)
   {  
      if (msg.equals(".koniec"))
      {  
         this.ok.Tekst.append("Zakonczono. ENTER ...");
         stop();
      }
      else
         this.ok.Tekst.append(msg+"\n");
   }
   
   public void send(String send)
   {
       try
         {  streamOut.writeUTF(send);
            streamOut.flush();
         }
         catch(IOException ioe)
         {  
            this.ok.Tekst.append("ERROR: " + ioe.getMessage());
            stop();
         }
   }
   
   public void start() throws IOException
   {  
      console   = new DataInputStream(System.in);
      streamOut = new DataOutputStream(socket.getOutputStream());
      if (thread == null)
      {  
         client = new ChatClientThread(this, socket, this.ok);
         thread = new Thread(this);                   
         thread.start();
      }
   }
   
   public void stop()
   {  if (thread != null)
      {  
         thread.stop();  
         thread = null;
      }
      try
      {  
         if (console   != null)  console.close();
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  
          this.ok.Tekst.append("Error"); }
          client.close();  
          client.stop();
   }
   
}

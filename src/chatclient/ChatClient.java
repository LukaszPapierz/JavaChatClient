/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

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
{  private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   private ChatClientThread client    = null;

   public ChatClient(String serverName, int serverPort)
   {  
      System.out.println("Trwa łaczenie ...");
      try
      {  socket = new Socket(serverName, serverPort);
         System.out.println("Poloczono: " + socket);
         start();
      }
      catch(UnknownHostException uhe)
      {  
          System.out.println("blad hosta: " + uhe.getMessage()); 
      }
      catch(IOException ioe)
      {  
          System.out.println("ERROR: " + ioe.getMessage()); 
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
            System.out.println("ERROR: " + ioe.getMessage());
            stop();
         }
      }
   }
   
   public void handle(String msg)
   {  
      if (msg.equals(".koniec"))
      {  
         System.out.println("Zakonczono. ENTER ...");
         stop();
      }
      else
         System.out.println(msg);
   }
   
   public void start() throws IOException
   {  
      console   = new DataInputStream(System.in);
      streamOut = new DataOutputStream(socket.getOutputStream());
      if (thread == null)
      {  
         client = new ChatClientThread(this, socket);
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
          System.out.println("Error"); }
          client.close();  
          client.stop();
   }
   
   public static void main(String args[])
   {  
       ChatClient client = null;
       client = new ChatClient("127.0.0.1",50007);
        
       try 
       {
            client.start();
       } 
       catch (IOException ex) 
       {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient2;

import java.net.Socket;

/**
 *
 * @author X
 */
import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread
{  
   private Socket           socket   = null;
   private ChatClient       client   = null;
   private DataInputStream  streamIn = null;
   private Okno ok;
   
   public ChatClientThread(ChatClient _client, Socket _socket, Okno _ok)
   {  
      client   = _client;
      socket   = _socket;
      ok = _ok;
      
      open();  
      start();
   }
   
   public void open()
   {  try
      {  
          streamIn  = new DataInputStream(socket.getInputStream());
      }
      catch(IOException ioe)
      {  
         this.ok.Tekst.append("Error: " + ioe);
         client.stop();
      }
   }
   
   public void close()
   {  try
      {  
          if (streamIn != null) streamIn.close();
      }
      catch(IOException ioe)
      {  
          this.ok.Tekst.append("Error: " + ioe);
      }
   }
    
   @Override
   public void run()
   {  while (true)
      {  
          try
         {  
             client.handle(streamIn.readUTF());
         }
         catch(IOException ioe)
         {  
            this.ok.Tekst.append("buuu ktos dostał bana: " + ioe.getMessage());
            client.stop();
         }
      }
   }
}

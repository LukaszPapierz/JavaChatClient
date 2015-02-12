/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread
{  
   private Socket           socket   = null;
   private ChatClient       client   = null;
   private DataInputStream  streamIn = null;

   public ChatClientThread(ChatClient _client, Socket _socket)
   {  client   = _client;
      socket   = _socket;
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
          System.out.println("Error: " + ioe);
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
          System.out.println("Error: " + ioe);
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
            System.out.println("buuu ktos dostał bana: " + ioe.getMessage());
            client.stop();
         }
      }
   }
}

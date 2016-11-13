import java.io.*;
import java.net.*;

class TCPServer
{
   public static void startServer() throws Exception
      {
         String clientSentence;
         ServerSocket welcomeSocket = new ServerSocket(6789);

         while(true)
         {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            JsonParser.parseOffer(clientSentence);
         }
      }
}
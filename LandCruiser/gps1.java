import java.io.*;
import java.net.*;
 
public class gps1 {
   public static void main(String args[]) {
      try {
         InetAddress localhost = InetAddress.getByName("localhost");
         Socket      s         = new Socket(localhost, 9000);
 
         OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
         BufferedReader     in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
 
         System.out.println("Sending message to Python program...");
         out.write("Message to Python program.\n");
         out.flush();
 
	 System.out.println("Getting response from Python program...");
         String answer = in.readLine();
         System.out.println("Got: " + answer);
      } catch (Exception e) {
         System.err.println("Ooops!");
         e.printStackTrace();
      }
   }
}

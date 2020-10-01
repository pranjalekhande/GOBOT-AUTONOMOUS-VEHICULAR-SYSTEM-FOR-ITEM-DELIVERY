import java.io.*;
import java.net.*;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
public class RCServer {
    public static void main(String[] args) throws InterruptedException {
    
        RCServer rcServer = new RCServer();
	int i;
        try(ServerSocket serverSocket = new ServerSocket(4141)) {
            while (true) {
                System.out.println("Listening on port 4141, CRTL-C to quit ");
                Socket socket = serverSocket.accept();
                try ( 	PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
                    System.out.println("Connection accepted.");
                    String in = "";
                    while ((in = input.readLine()) != null) {
                        System.out.println("Received: " + in);
                        i=Integer.parseInt(in);
			if (in.equals("SYN")){ // Synchronize
                            rcServer.write(output, "SYN-ACK"); // Synchronize-Acknowledge
                        }	
                        if (in.equals("FIN")){ // Final
                            rcServer.write(output, "FIN-ACK"); // Final-Acknowledge
                            break;
                        }
			else
			rcServer.udan(i/100,i%100);
                    }
                    System.out.print("Closing socket.\n\n");				
                } catch (IOException e) {
                    e.printStackTrace();	
                } 
            } 
        } catch (IOException e) {
            e.printStackTrace();	
        }
    }

    void write(PrintWriter output, String message) {
        System.out.println("Sending: "+message);
        output.println(message);
    }

	public void udan(int x,int y){	
		final GpioController gpio=GpioFactory.getInstance();
		final GpioPinDigitalOutput ru1=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28);
		final GpioPinDigitalOutput ru2=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27);
		final GpioPinDigitalOutput rd1=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24);
		final GpioPinDigitalOutput rd2=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
		final GpioPinDigitalOutput lu1=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
		final GpioPinDigitalOutput lu2=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
		final GpioPinDigitalOutput ld1=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);
		final GpioPinDigitalOutput ld2=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);		

		if(x>52){
			rd1.high();
			rd2.low();
			ld1.high();
			ld2.low();
			ru1.high();
			ru2.low();
			lu1.high();
			lu2.low();
			}
		if(x<47){
			rd2.high();
			rd1.low();
			ld2.high();
			ld1.low();
			ru2.high();
			ru1.low();
			lu2.high();
			lu1.low();
			}
	}
    
}













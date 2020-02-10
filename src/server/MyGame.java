package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MyGame implements Runnable {

	BufferedReader xSocketIn, ySocketIn;
	PrintWriter xSocketOut, ySocketOut;
	
	public MyGame(BufferedReader xSocIn, BufferedReader ySocIn, PrintWriter xSocOut, PrintWriter ySocOut) {
		
		xSocketIn = xSocIn;
		ySocketIn = ySocIn;
		xSocketOut = xSocOut;
		ySocketOut = ySocOut;
		
	}
	
	@Override
	public void run() {
		
		String xResponse, yResponse;

		while(true) {
			
			try {
				xResponse = xSocketIn.readLine();
				yResponse = ySocketIn.readLine();
				if(xResponse != null)
					ySocketOut.println(xResponse);
				if(yResponse != null)
					xSocketOut.println(yResponse);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

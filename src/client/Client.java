package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	
	private final String EOM = "EOM"; // end of message terminating string

	
	public Client (String serverName, int portNumber) {
		
		try {
			aSocket = new Socket (serverName, portNumber);
			// socket input stream
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			// socket output stream
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);
			// keyboard input stream
			stdIn = new BufferedReader (new InputStreamReader (System.in));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void communicate () {
		String line = "";
		
		System.out.println("Connecting to server...");
		
		// receive all messages from server
		line = receiveMessage();
		while(true) {
			line = receiveMessage();
			if(line.equals(EOM)) {
				break;
			}
			else if(line.equals("getNames")) {
				getPlayerName();
				line = "";
			}
			else if(line.equals("getRowCol")) {
				getRowOrColumn();
				line = "";
			}
			System.out.println(line);
		}
		
		closeSockets();
		System.exit(0);
	}
	
	private String receiveMessage() {
		String response = "";
		try {
			response = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private void getPlayerName() {
		String line = "";
		String response = "";
		
		while(true) {
			response = receiveMessage(); // read responses from the server
			if(response.equals(EOM)) {
				break;
			}
			System.out.println(response);
			line = getStdInput(); // get the user name
			socketOut.println(line); // send back to server
		}
	}
	
	private void closeSockets() {

		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		}catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	private String getStdInput() {
		String line = "";
		try {
			line = stdIn.readLine(); // read a line from the keyboard

		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	private void getRowOrColumn() {
		
		String response = "";
		String line = "";
		
		while(true) {
			
			try {
				response = receiveMessage();
				if(response.equals(EOM)) {
					break;
				}
				System.out.println(response);
				line = stdIn.readLine(); // read a line from the keyboard
				socketOut.println(line); // send back to server
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static void main (String [] args) throws IOException{
		Client aClient = new Client ("localhost", 9090);
		aClient.communicate();
	}

}

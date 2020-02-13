package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server side of the command line tic tac toe game
 * @author Sean Barton
 *
 */
public class Server {

	private Socket aSocket1, aSocket2;
	private ServerSocket serverSocket;
	private PrintWriter socketOut1, socketOut2;
	private BufferedReader socketIn1, socketIn2;
	
	private ExecutorService pool;

	/**
	 * Constructs the server on static TCP port 9090
	 * with 3 threads.
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(9090);
			pool = Executors.newFixedThreadPool(3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives the incomming sockets from up to 2 players on a single
	 * thread.
	 */
	public void runServer() {
		System.out.println("Waiting for first connection...");
		try {
			while(true) {
				// accepting the connection from the first client
				aSocket1 = serverSocket.accept();
				socketIn1 = new BufferedReader(new InputStreamReader(aSocket1.getInputStream()));
				socketOut1 = new PrintWriter((aSocket1.getOutputStream()), true);
				socketOut1.println("Player 1 connected, waiting for player 2...");
				System.out.println("Connected player 1, waiting for other user...");
				
				// accepting the connection from the second client
				aSocket2 = serverSocket.accept();
				socketIn2 = new BufferedReader(new InputStreamReader(aSocket2.getInputStream()));
				socketOut2 = new PrintWriter((aSocket2.getOutputStream()), true);
				socketOut2.println("Player 2 connected...");
				System.out.println("Connected both players.\n");
				
				// starting the game
				Game game = new Game(socketIn1, socketIn2, socketOut1, socketOut2);
				pool.execute(game);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socketIn1.close();
			socketOut1.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Initiates the server side of the game.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Server myServer = new Server();
		System.out.println("Starting server...");
		myServer.runServer();

	}
}

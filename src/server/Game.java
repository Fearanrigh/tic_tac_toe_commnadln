package server;

import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 

/**
 * The main tic-tac-toe game class
 * @author ENSF593/594
 *
 */
public class Game implements Constants, Runnable {

	private Board theBoard;
	private Referee theRef;
	private BufferedReader socketIn1, socketIn2;
	private PrintWriter socketOut1, socketOut2;
	
	private final String EOM = "EOM"; // end of message terminator string
	
	/**
	 * Constructs the game with a new board
	 */
    public Game(BufferedReader socketIn1, BufferedReader socketIn2,
    		PrintWriter socketOut1, PrintWriter socketOut2) {
        theBoard  = new Board();
        this.socketIn1 = socketIn1;
        this.socketIn2 = socketIn2;
        this.socketOut1 = socketOut1;
        this.socketOut2 = socketOut2;
	}
    
    /**
     * Appoints a referee for the game
     * @param r the referee
     * @throws IOException bad input from the player gets passed upwards
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
	
    /**
     * Main entry for the program
     * @param args
     * @throws IOException
     */
	@Override
	public void run() {
		
		Referee theRef;
		Player xPlayer, oPlayer;

		
		////////////// refactor the following to be one method for both inputs
		
		
		// getting the name of player 1
		socketOut1.println("getNames");
		socketOut1.println("Please enter the name of the \'X\' player: ");
		socketOut2.println("Waiting for the \'X\' player...");
		String name = null;
		while(true) {
			try {
				name = socketIn1.readLine();
				if(name.isBlank()) {
					socketOut1.println("Please try again: ");
				}
				else {
					socketOut1.println(EOM);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		xPlayer = new Player(name, LETTER_X, socketIn1, socketOut1);
		xPlayer.setBoard(theBoard);
		
		
		// Getting the name of player 2
		
		socketOut2.println("getNames");
		socketOut2.println("Please enter the name of the \'O\' player: ");
		socketOut1.println("Waiting for the \'O\' player...");
		name = null;
		while(true) {
			try {
				name = socketIn2.readLine();
				if(name.isBlank()) {
					socketOut2.println("Please try again: ");
				}
				else {
					socketOut2.println(EOM);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		///////// end refactor
		
		
		
		oPlayer = new Player(name, LETTER_O, socketIn2, socketOut2);
		oPlayer.setBoard(theBoard);
		
		theRef = new Referee();
		theRef.setBoard(theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
        
        try {
			appointReferee(theRef);
		} catch (IOException e) {
			e.printStackTrace();
		}
        socketOut1.println(EOM);
        socketOut2.println(EOM);
	}
}

package server;
import java.io.*;

/**
 * The player makes moves on the board
 * @author Sean Barton
 *
 */
public class Player {

	private String name;
	private Board board;
	private Player opponent;
	private char mark;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	private final String EOM = "EOM";
	
	/**
	 * Constructs the player given a name and the assigned mark
	 * @param name the name of the player
	 * @param mark the player's mark ('X' or 'O')
	 */
	public Player(String name, char mark, BufferedReader socketIn, PrintWriter socketOut) {
		this.name = name;
		this.mark = mark;
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	/**
	 * Determines if the game has been won, lost or tied. This method displays
	 * the board and any updates and transfers play back an forth between players.
	 * @throws IOException
	 */
	public void play() throws IOException {
		System.out.println();
		String showBoard = board.display();
		writeMessage(showBoard);
		opponent.writeMessage("Player " + mark + "'s turn...");
		if (!(board.xWins() || board.oWins() || board.isFull())) {
			this.makeMove();
		}
		else {
			writeMessage("\nTHE GAME IS OVER: ");
			opponent.writeMessage("\nTHE GAME IS OVER: ");
			if (board.isFull()){
				String tied = "The game is tied!";
				writeMessage(tied);
				opponent.writeMessage(tied);
			}
			else {
				if (board.xWins() && this.mark == 'X') {
					printWinner(name);
				}
				else if (board.oWins() && this.mark == 'O') {
					printWinner(name);
				}
				else {
					printWinner(opponent.name);
				}
			}
			writeMessage("Game Ended ...");
			opponent.writeMessage("Game Ended ...");
			return;

		}
		
		System.out.println();
		showBoard = board.display();
		writeMessage(showBoard);
		opponent.play();
	}
	
	/**
	 * Allows a player to make a move, choosing from the available cells.
	 * @throws IOException
	 */
	public void makeMove() throws IOException {
		int row = 0;
		int col = 0;
		boolean alreadyMarked = true;
		do {			
			row = getRowOrColInput(name, "row", mark);
			col = getRowOrColInput(name, "column", mark);	
			alreadyMarked = (board.getMark(row, col) == 'X' || board.getMark(row, col) == 'O');
			if (alreadyMarked) {
				writeMessage("\nSorry, that square is already marked, please try again ...");
			}
		} while (alreadyMarked);
		
		board.addMark(row, col, mark);

	}
	
	/**
	 * Sets the opponent player for this player
	 * @param thePlayer the opponent
	 */
	public void setOpponent(Player thePlayer) {
		this.opponent = thePlayer;
	}
	
	/**
	 * Sets the board being played on
	 * @param theBoard the board being played
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}
	
	/**
	 * Reads incomming messages from the server. 
	 * A convenience method.
	 * @return
	 */
	public String readMessage() {
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public void writeMessage(String message) {
		socketOut.println(message);
	}

/////////////////
// HELPER METHODS
	/*
	 * Prints out the winner's name
	 * @param pName the winner's name
	 */
	private void printWinner(String pName) {
//		System.out.println(pName + " is the winner!");
		writeMessage(pName + " is the winner!");
		opponent.writeMessage(pName + " is the winner!");
	}
	
	/*
	 * Gets the row or column from the player
	 * @param name name of the player
	 * @param mark the player's mark ('X' or 'O')
	 * @return the row or column
	 * @throws IOException
	 */
	private int getRowOrColInput(String name, String rowCol, char mark) {
		writeMessage("getRowCol");
		writeMessage(name + ", what " + rowCol + " should your next " + mark + " be placed in? ");
		String in = "";
		int out = 0;
		while (true) {
			in = readMessage();
			if(in.isBlank()) {
				writeMessage("Please try again: ");
			}
			else if(!isParsableInt(in)) {
				writeMessage("Must be an integer of 0, 1 or 2");
			}
			else {
				out = Integer.parseInt(in);
				if(out < 0 || out >2) {
					writeMessage("Must be an integer of 0, 1 or 2");
				}
				else {
					writeMessage(EOM);
					break;
				}
			}
		}
		return out;
	}
	
	/**
	 * Determines if the received string is an integer or not.
	 * @param parsableStringInteger
	 * @return
	 */
	private boolean isParsableInt(String parsableStringInteger) {
		boolean isInteger = true;
		try {
			Integer.parseInt(parsableStringInteger);
		} catch (NumberFormatException e) {
			isInteger = false;
		}
		return isInteger;
	}
}

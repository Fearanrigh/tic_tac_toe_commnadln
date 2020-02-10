package server;
import java.io.IOException;

/**
 * The referee sets up the board and the players and starts the game running
 * @author Sean Barton
 *
 */
public class Referee {
	
	private Player xPlayer;
	private Player oPlayer;
	private Board board;
	
	private final String EOM = "EOM";
	
	/**
	 * Constructs nothing
	 */
	public Referee() {
	}
	
	/**
	 * Begins the game
	 * @throws IOException if the player enters a wrong input
	 */
	public void runTheGame() throws IOException {
		oPlayer.setOpponent(xPlayer);
		xPlayer.setOpponent(oPlayer);
		String refMessage = "\nReferee started the game...";
		xPlayer.writeMessage(refMessage);
		oPlayer.writeMessage(refMessage);
		xPlayer.play();
	}
	
	/**
	 * Stores the board.
	 * @param board the board object
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Stores the 'o' player.
	 * @param oPlayer the 'o' player object
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	/**
	 * Stores the 'x' player
	 * @param xPlayer the 'x' player object
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}

}

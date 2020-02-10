package server;


//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 

/**
 * Creates and displays a tic-tac-toe board.
 * @author ENSF593/594
 *
 */
public class Board implements Constants {
	private char theBoard[][];
	private int markCount;

	/**
	 * Constructs a board, and clears it for play
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Retrieves the mark type, x or o, for a given row and column
	 * @param row the row of the board
	 * @param col the column of the board
	 * @return the mark, either x or o
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * Determines if the board is full of marks
	 * @return true if the board is full, false otherwise
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Checks if x is the winner
	 * @return true if x wins otherwise false
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks if o is the winner
	 * @return true if o wins otherwise false
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Creates the board display
	 */
	public String display() {
		String str = "\n";
		str += displayColumnHeaders();
		str += addHyphens();
		for (int row = 0; row < 3; row++) {
			str += addSpaces();
			str += "    row " + row + ' ';
			for (int col = 0; col < 3; col++)
				str += "|  " + getMark(row, col) + "  ";
			str += "|";
			str += "\n";
			str += addSpaces();
			str += addHyphens();
		}
		return str;
	}

	/**
	 * Add a mark the board given the row, column and mark to make (either x or o)
	 * @param row row of the board
	 * @param col column of the board
	 * @param mark type of mark
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Clears the board of marks
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

/////////////////
// HELPER METHODS
	
	/*
	 * Logic to determine if a winner has occurred
	 * @param mark the mark to check
	 * @return zero if no winner, 1 if there is a winner
	 */
	private int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/*
	 * Displays the column header for the board
	 */
	private String displayColumnHeaders() {
		String str = "";
		str += "          ";
		for (int j = 0; j < 3; j++)
			str += "|col " + j;
		str += "\n";
		return str;
	}

	/*
	 * Displays the horizontal lines
	 */
	private String addHyphens() {
		String str = "";
		str += "          ";
		for (int j = 0; j < 3; j++)
			str += "+-----";
		str += "+";
		str += "\n";
		return str;
	}

	/*
	 * Displays the vertical lines
	 */
	private String addSpaces() {
		String str = "";
		str += "          ";
		for (int j = 0; j < 3; j++)
			str += "|     ";
		str += "|";
		str += "\n";
		return str;
	}
}

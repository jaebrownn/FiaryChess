public class Piece {
	private boolean free;
	private int row;
	private int col;
	private char pieceChar;
	private boolean black;

	public Piece(int row, int col, char c, boolean black, boolean free) {
		this.free = free;
		this.row = row;
		this.col = col;
		pieceChar = c;
		this.black = black;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public void setPieceChar(char pieceChar) {
		this.pieceChar = pieceChar;
	}

	public char getPieceChar() {
		return pieceChar;
	}
	
	public boolean isFree() {
		return free;
	}

	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = true;
		if (dstCol == srcCol && dstRow == srcRow) {
//			System.out.println("same cell");
			valid = false;
		} else if (dstCol < 0 || dstCol > 10 || srcCol < 0 || srcCol > 10 || dstRow < 0 || dstRow > 10 || srcRow < 0
				|| srcRow > 10) {
//			System.out.println("invalid entires");
			valid = false;
		}
		return valid;
	}
	
	public boolean validCapture(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = true;
		return valid;
	}

	public String toString(){
		String bl = "white";
		if (black) bl = "black ";
		return bl + pieceChar + " @ " + row + col + " free: " + free;
	}
}

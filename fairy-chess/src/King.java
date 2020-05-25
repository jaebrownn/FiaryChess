
public class King extends Piece {
	private boolean moved;
	public King(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		moved = false;
		// TODO Auto-generated constructor stub
	}
	
	public void setMoved() {
		moved = true;
	}
	
	public boolean getMoved() {
		return moved;
	}

	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		
		if (Math.abs(dstRow-srcRow)<=1 && Math.abs(dstCol-srcCol)<=1) {
			valid = true;
		}

		return valid;
	}
}

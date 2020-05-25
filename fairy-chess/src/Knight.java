
public class Knight extends Piece {

	public Knight(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}
	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		
		if ((Math.abs(dstRow-srcRow)==1 && Math.abs(dstCol-srcCol)==2) || (Math.abs(dstRow-srcRow)==2 && Math.abs(dstCol-srcCol)==1)) {
			valid = true;
		}

		return valid;
	}

}

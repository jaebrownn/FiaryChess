
public class Elephant extends Rook{

	public Elephant(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}

	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			if (super.isBlack()) {
				if (dstRow<5) {
					valid=true;
				}
			} else {
				if (dstRow>=5) {
					valid=true;
				}
			}
		}
		
		return valid;
	}
}

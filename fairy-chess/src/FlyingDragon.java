
public class FlyingDragon extends Bishop{

	public FlyingDragon(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}
	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			if (Math.abs(dstRow-srcRow)<=2) {
				valid=true;
			}
		}
		
		return valid;
	}

}

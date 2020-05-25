public class DrunkenSoldier extends Piece{

	public DrunkenSoldier(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
	}
	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		int k = 1;
		
		if (super.isBlack() == false) {
			k = -1;
		}
		
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			if (dstCol == srcCol) {
				if (dstRow == srcRow + (1 * k)) {
					valid = true;
				}
			} else if (dstRow == srcRow) {
				if (dstCol == srcCol+1 || dstCol == srcCol-1) {
					valid = true;
				}
			}
		}
		return valid;
	}
	
	public boolean validMoveAlt(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = true;
		int changeX = Math.abs(srcCol - dstCol);
		int changeY = Math.abs(srcRow - dstRow);
		
		// check y direction consistent
		if (changeY != 0) {
			int yDir = (dstRow - srcRow) / Math.abs(dstRow - srcRow);
			if (this.isBlack()) {
				if (yDir < 0) {
					valid = false;
				}
			} else {
				if (yDir > 0) {
					valid = false;
				}
			}
		}
		
		if (valid) {
			if (changeX == 0) {
				// is it moving up or down
				if (changeY == 2) {
					valid = false;
				} else if (changeY != 1) {
					valid = false;
				}
			} else if (changeX == 1) {
				// checks if it is a valid capture
				if (changeY > 1) {
					valid = false;
				}
			} else {
				valid = false;
			}
		}
		
		return valid;
	}
	
	public boolean validCaptureAlt(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		int k = 1;
//		System.out.println("Capturing from:"+srcRow+srcCol+" to "+dstRow+dstCol);

		if (super.isBlack() == false) {
			k = -1;
		}

		if (srcRow < 9 && srcRow > 0 && srcCol >= 0 && srcCol <= 9) {
			if (srcCol == 0) {
				if (dstCol == srcCol + 1 && dstRow == srcRow + (1 * k)) {
					valid = true;
				}
			} else if ((srcCol == 9)) {
				if (dstCol == srcCol - 1 && dstRow == srcRow + (1 * k)) {
					valid = true;
				}
			} else {
				if ((dstCol == srcCol + 1 || dstCol == srcCol - 1) && dstRow == srcRow + (1 * k)) {
					valid = true;
				}
			}
		}
		
		return valid;
	}
	public boolean validCapture(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = true;
		int changeX = Math.abs(srcCol - dstCol);
		int changeY = Math.abs(srcRow - dstRow);
		if (changeY == 0) {
			valid = false;
		}
		else {
			int yDir = (dstRow - srcRow) / Math.abs(dstRow - srcRow);
			if (this.isBlack()) {
				if (yDir < 0) {
					valid = false;
				}
			} else {
				if (yDir > 0) {
					valid = false;
				}
			}
			
			if (!(changeX == 1 && changeY == 1)){
				valid = false;
			}
		}
		
		return valid;
	}
}

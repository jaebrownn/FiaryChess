
public class Bishop extends Piece {

	public Bishop(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}
	
public boolean validMoveAlt(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = true;
		int changeX = Math.abs(srcCol - dstCol);
		int changeY = Math.abs(srcRow - dstRow);
		
		if (changeX != changeY) { // checks that the destination itself is valid
			valid = false;
		}
		if (changeX == 0) { // ensures the piece does move
			valid = false;
		}
		
		if (valid) {
			int xDir = (dstCol - srcCol) / Math.abs(dstCol - srcCol);
			int yDir = (dstRow - srcRow) / Math.abs(dstRow - srcRow);

			for (int x = dstCol + xDir, y = dstRow + yDir; x != dstCol && y != dstRow; x += xDir, y += yDir) {
				// checks that there are no pieces between the source and destination
				if (!board.getPiece(y, x).isFree()) {
					valid = false;
					break;
				}
			}
		}

		return valid;
	}

	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		int colUp = srcCol + 1;
		int colDown = srcCol - 1;
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			for (int i = srcRow + 1; i < 10; i++) {
				if (colUp > 9) {
					break;
				}

				if (!(board.getPiece(i, colUp).isFree()) && !(dstCol == colUp && dstRow == i)) {
					break;
				} else if (dstCol == colUp && dstRow == i) {
					valid = true;
					break;
				}
				colUp++;
			}
			
			if (!valid) {
				for (int i = srcRow + 1; i < 10; i++) {
					if (colDown < 0) {
						break;
					}
					if (!(board.getPiece(i, colDown).isFree()) && !(dstCol == colDown && dstRow == i)) {
						break;
					} else if (dstCol == colDown && dstRow == i) {
						valid = true;
						break;
					}
					colDown--;
				}
			}

			if (!valid) {
				colUp = srcCol + 1;
				colDown = srcCol - 1;
				for (int i = srcRow - 1; i >= 0; i--) {
					if (colUp > 9) {
						break;
					}
					if (!(board.getPiece(i, colUp).isFree()) && !(dstCol == colUp && dstRow == i)) {
						break;
					} else if (dstCol == colUp && dstRow == i) {
						valid = true;
						break;
					}
					colUp++;
				}
			}

			if (!valid) {
				for (int i = srcRow - 1; i >= 0; i--) {
					if (colDown < 0) {
						break;
					}
					if (!(board.getPiece(i, colDown).isFree()) && !(dstCol == colDown && dstRow == i)) {
						break;
					} else if (dstCol == colDown && dstRow == i) {
						valid = true;
						break;
					}
					colDown--;
				}
			}
		}

		return valid;
	}

}

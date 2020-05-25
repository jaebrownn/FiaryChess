
public class Rook extends Piece{
	private boolean moved;
	
	public Rook(int row, int col, char c, boolean black, boolean free) {
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
		int colUp = srcCol + 1;
		int colDown = srcCol - 1;
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			for (int i = srcRow + 1; i < 10; i++) {
				if (!(board.getPiece(i, srcCol).isFree()) && !(dstRow == i && dstCol == srcCol)) {
					break;
				} else {
					if (dstRow == i && dstCol == srcCol) {
						valid = true;
						break;
					}
				}
			}
			
			if (!valid) {
				for (int i = srcRow - 1; i >= 0; i--) {
					if (!(board.getPiece(i, srcCol).isFree()) && !(dstRow == i && dstCol == srcCol)) {
						break;
					} else {
						if (dstRow == i && dstCol == srcCol) {
							valid = true;
							break;
						}
					}
				}
			}

			if (!valid) {
				for (int i = srcCol + 1; i < 10; i++) {
					if (!(board.getPiece(srcRow, i).isFree()) && !(dstCol == i && dstRow == srcRow)) {
						break;
					} else {
						if (dstCol == i && dstRow == srcRow) {
							valid = true;
							break;
						}
					}
				}
			}
			
			if (!valid) {
				for (int i = srcCol - 1; i >= 0; i--) {
					if (!(board.getPiece(srcRow, i).isFree()) && !(dstCol == i && dstRow == srcRow)) {
						break;
					} else {
						if (dstCol == i && dstRow == srcRow) {
							valid = true;
							break;
						}
					}
				}
			}
			
		}

		return valid;
	}

	public boolean validMoveAlt(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = true;

		int changeX = Math.abs(srcCol - dstCol);
		int changeY = Math.abs(srcRow - dstRow);
		
		int xDir = (dstCol - srcCol) / Math.abs(dstCol - srcCol);// 1 = right, -1 = left
		int yDir = (dstRow - srcRow) / Math.abs(dstRow - srcRow);// 1 = down, -1 = up
		
		if (changeX == 0 && changeY == 0) { // ensures the piece does move
			valid = false;
		}
		if ((changeX != 0 && changeY == 0) || (changeY != 0 && changeX == 0)) { // checks that the destination itself is valid
			valid = false;
		}
		
		
		if (valid) { // if it is still valid, checks that there are no pieces from source to destination
			if (changeY == 0) {
				if (xDir == 1) {
					for (int j = srcCol + 1; j < dstCol; j++) {
						if (!board.getPiece(srcRow, j).isFree()) {
							valid = false;
							break;
						}
					}
				} else if (xDir == -1) {
					for (int j = srcCol - 1; j > dstCol; j--) {
						if (!board.getPiece(srcRow, j).isFree()) {
							valid = false;
							break;
						}
					}
				}
			} else if (changeX == 0) {
				if (yDir == 1) {
					for (int i = srcRow + 1; i < dstRow; i++) {
						if (!board.getPiece(i, srcCol).isFree()) {
							valid = false;
							break;
						}
					}
				} else if (yDir == -1) {
					for (int i = srcRow - 1; i > dstRow; i--) {
						if (!board.getPiece(i, srcCol).isFree()) {
							valid = false;
							break;
						}
					}
				}
			}
	}

		return valid;
	}
}

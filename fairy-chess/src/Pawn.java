public class Pawn extends Piece {
	private boolean moved;
	
	public Pawn(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		moved = false;
	}
	
	public void setMoved(){
		moved = true;
	}
	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		int k = 1;
		
		if (super.isBlack() == false) {
			k = -1;
		}
		
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			if (dstCol == srcCol) {
				if (moved == false) {
					if ((dstRow == srcRow + (2 * k) && board.getPiece(srcRow + (1 * k), srcCol).isFree()) || dstRow == srcRow + (1 * k)) {
						valid = true;
					}
				} else {
					if (dstRow == srcRow + (1 * k)) {
						valid = true;
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

		// check y direction consistent
		
		//check that it doesn't go in a base rank
		if (dstRow != 0 || dstRow != 9) {
			int yDir = 0;
			if (changeY != 0) {
				yDir = (dstRow - srcRow) / Math.abs(dstRow - srcRow); // 1 = down, -1 = up
				if (this.isBlack()) {
					if (yDir < 0) {
						valid = false;
					}
				} else {
					if (yDir > 0) {
						valid = false;
					}
				}

				if (valid) {
					if (changeX == 0) {
						// is it moving up or down
						if (changeY == 2) {
							if (board.getPiece(srcRow + yDir, srcCol).isFree()) {
								if (this.isBlack()) {
									if (srcRow != 1) {
										valid = false;
									}
								} else {
									if (srcRow != 8) {
										valid = false;
									}
								}
							} else {
								valid = false;
							}
						} else if (changeY != 1) {
							valid = false;
						}
					} else if (changeX == 1) {
						// checks if it is a valid capture
						if (changeY != 1) {
							valid = false;
						}
					} else {
						valid = false;
					}
				}
			} else {
				valid = false;
			}
		} else {
			valid = false;
		}

		return valid;
	}

	public boolean validCaptureAlt(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		int k = 1;

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

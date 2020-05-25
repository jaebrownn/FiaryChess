
public class RookBefore extends Piece{

	public RookBefore(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}

	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		int colUp = srcCol + 1;
		int colDown = srcCol - 1;
//		System.out.println("destination: " + dstRow + dstCol);
		if (super.validMove(srcRow, srcCol, dstRow, dstCol, board)) {
			for (int i = srcRow + 1; i < 10; i++) {
				if (!(board.getPiece(i, srcCol).isFree()) && !(dstRow == i && dstCol == srcCol)) {
//					System.out.println("blocking piece at " + i + srcCol);
					break;
				} else {
//					System.out.println(i + "" + srcCol);
					if (dstRow == i && dstCol == srcCol) {
//						System.out.println("Valid move");
						valid = true;
						break;
					}
				}
			}
			if (!valid) {
				for (int i = srcRow - 1; i >= 0; i--) {
					if (!(board.getPiece(i, srcCol).isFree()) && !(dstRow == i && dstCol == srcCol)) {
//						System.out.println("blocking piece at " + i + srcCol);
						break;
					} else {
//						System.out.println(i + "" + srcCol);
						if (dstRow == i && dstCol == srcCol) {
//							System.out.println("Valid move");
							valid = true;
							break;
						}
					}
				}
			}

//			System.out.println("valid: " + valid);

			if (!valid) {
				for (int i = srcCol + 1; i < 10; i++) {
					if (!(board.getPiece(srcRow, i).isFree()) && !(dstCol == i && dstRow == srcRow)) {
//						System.out.println("blocking piece at " + srcRow + i);
						break;
					} else {
//						System.out.println(srcRow + "" + i + " & " + dstRow + dstCol);
						if (dstCol == i && dstRow == srcRow) {
//							System.out.println("Valid move");
							valid = true;
							break;
						}
					}
				}
			}
			if (!valid) {
				for (int i = srcCol - 1; i >= 0; i--) {
					if (!(board.getPiece(srcRow, i).isFree()) && !(dstCol == i && dstRow == srcRow)) {
//						System.out.println("blocking piece at " + srcRow + i);
						break;
					} else {
//						System.out.println(srcRow + "" + i);
						if (dstCol == i && dstRow == srcRow) {
//							System.out.println("Valid move");
							valid = true;
							break;
						}
					}
				}
			}
			
		}

		return valid;
	}

	public void setMoved() {
		// TODO Auto-generated method stub
		
	}
}

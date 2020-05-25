
public class Rook2 extends Piece{

	public Rook2(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}

	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
	
		int diffX = Math.abs(srcCol - dstCol);
		int diffY = Math.abs(srcRow - dstRow);
		
		if (diffX == 0 && diffY == 0) {
			return false;
		}
		
		if (diffY == 0) {
			for (int x = srcCol+1; x < dstCol; x++) {
				if (!board.getPiece(srcRow, x).isFree()) {
					return false;
				}
			}
			for (int x = srcCol-1; x > dstCol; x--) {
				if (!board.getPiece(srcRow, x).isFree()) {
					return false;
				}
			}
		} else if (diffX == 0) {
			
		} else {
			return false;
		}
		
		

		return true;
	}
	
	public boolean validMoveBishop(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		
		int diffX = Math.abs(srcCol - dstCol);
		int diffY = Math.abs(srcRow - dstRow);
		
		if (diffX != diffY) {
			return false;
		}
		if (diffX == 0) {
			return false;
		}
		
		
		int dirX = (dstCol - srcCol) / Math.abs(dstCol - srcCol);
		int dirY = (dstRow - srcRow) / Math.abs(dstRow - srcRow);
		
		for (int x = dstCol + dirX, y = dstRow + dirY; x != dstCol && y != dstRow; x += dirX, y += dirY) {
			if (!board.getPiece(y, x).isFree()) {
				return false;
			}
		}

		return true;
	}
}

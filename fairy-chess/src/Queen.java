
public class Queen extends Piece {

	public Queen(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}
	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		boolean validBishop=false;
		boolean validRook=false;
		
		Bishop tempBishop = new Bishop(srcRow, srcCol, '%', board.getPiece(srcRow, srcCol).isBlack(), board.getPiece(srcRow, srcCol).isFree());
		validBishop=tempBishop.validMove(srcRow, srcCol, dstRow, dstCol, board);
		
		Rook tempRook=new Rook(srcRow, srcCol, '%', board.getPiece(srcRow, srcCol).isBlack(), board.getPiece(srcRow, srcCol).isFree());
		validRook=tempRook.validMove(srcRow, srcCol, dstRow, dstCol, board);
		
		if (validBishop || validRook) {
			valid=true;
		}
		
		return valid;
	}

}

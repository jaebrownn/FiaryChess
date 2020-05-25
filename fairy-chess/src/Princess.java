
public class Princess extends Piece {

	public Princess(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}
	
	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		boolean validBishop=false;
		boolean validKnight=false;
		
		Bishop tempBishop = new Bishop(srcRow, srcCol, '%', board.getPiece(srcRow, srcCol).isBlack(), board.getPiece(srcRow, srcCol).isFree());
		validBishop=tempBishop.validMove(srcRow, srcCol, dstRow, dstCol, board);
		
		Knight tempKnight=new Knight(srcRow, srcCol, '%', board.getPiece(srcRow, srcCol).isBlack(), board.getPiece(srcRow, srcCol).isFree());
		validKnight=tempKnight.validMove(srcRow, srcCol, dstRow, dstCol, board);
		
		if (validBishop || validKnight) {
			valid=true;
		}
		
		return valid;
	}

}

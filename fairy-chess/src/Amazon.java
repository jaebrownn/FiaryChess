
public class Amazon extends Piece{

	public Amazon(int row, int col, char c, boolean black, boolean free) {
		super(row, col, c, black, free);
		// TODO Auto-generated constructor stub
	}

	public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol, Board board) {
		boolean valid = false;
		boolean validQueen=false;
		boolean validKnight=false;
		
		Queen tempQueen = new Queen(srcRow, srcCol, '%', board.getPiece(srcRow, srcCol).isBlack(), board.getPiece(srcRow, srcCol).isFree());
		validQueen=tempQueen.validMove(srcRow, srcCol, dstRow, dstCol, board);
		
		Knight tempKnight=new Knight(srcRow, srcCol, '%', board.getPiece(srcRow, srcCol).isBlack(), board.getPiece(srcRow, srcCol).isFree());
		validKnight=tempKnight.validMove(srcRow, srcCol, dstRow, dstCol, board);
		
		if (validQueen || validKnight) {
			valid=true;
		}
		
		return valid;
	}
}

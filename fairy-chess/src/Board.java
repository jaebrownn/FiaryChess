import java.io.File;

public class Board {
	private Piece[][] board = new Piece[10][10];
	private boolean[][] available = new boolean[10][10];

	public Board() {

	}

	public void setPiece(Piece piece, int row, int col) {
		board[row][col] = piece;
	}

	public Piece getPiece(int row, int col) {
		return board[row][col];
	}

	public void print() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (j < 9) {
					System.out.print(board[i][j].getPieceChar() + " ");
				} else {
					System.out.print(board[i][j].getPieceChar());
				}
			}
			System.out.println();
		}
	}

	public String getIcon(int row, int col) {
		String fileName = "Pictures/JohnPablok Cburnett Chess set/PNGs/With Shadow/1x/";
		char pieceChar = (board[row][col]).getPieceChar();
		if (Character.isLowerCase(pieceChar)) {
			fileName = fileName + "b_";
		} else {
			fileName = fileName + "w_";
		}

		String pieceName = "";
		switch ((pieceChar + "").toLowerCase().charAt(0)) {
		case 'p':
			pieceName = "pawn";
			break;
		case 'd':
			pieceName = "dsoldier";
			break;
		case 'r':
			pieceName = "rook";
			break;
		case 'n':
			pieceName = "knight";
			break;
		case 'k':
			pieceName = "king";
			break;
		case 'q':
			pieceName = "queen";
			break;
		case 'b':
			pieceName = "bishop";
			break;
		case 'a':
			pieceName = "amazon";
			break;
		case 'e':
			pieceName = "elephant";
			break;
		case 'w':
			pieceName = "princess";
			break;
		case 'f':
			pieceName = "dragon";
			break;
		}

		fileName = fileName + pieceName + "_1x.png";
		return fileName;
	}
	
	public String getIcon(char pieceChar) {
		String fileName = "Pictures/JohnPablok Cburnett Chess set/PNGs/With Shadow/1x/b_";

		String pieceName = "";
		switch ((pieceChar + "").toLowerCase().charAt(0)) {
		case 'p':
			pieceName = "pawn";
			break;
		case 'd':
			pieceName = "dsoldier";
			break;
		case 'r':
			pieceName = "rook";
			break;
		case 'n':
			pieceName = "knight";
			break;
		case 'k':
			pieceName = "king";
			break;
		case 'q':
			pieceName = "queen";
			break;
		case 'b':
			pieceName = "bishop";
			break;
		case 'a':
			pieceName = "amazon";
			break;
		case 'e':
			pieceName = "elephant";
			break;
		case 'w':
			pieceName = "princess";
			break;
		case 'f':
			pieceName = "dragon";
			break;
		}

		fileName = fileName + pieceName + "_1x.png";
		return fileName;
	}

	public boolean isFree(int row, int col) {
		boolean free;
		return board[row][col].isFree();
	}

	public Piece[][] getBoard() {
		return board;
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	public boolean[][] getAvailable() {
		return available;
	}

	public void setAvailable(boolean[][] available) {
		this.available = available;
	}

}

package Backups;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MoveValidationChecksBackup {
	private static final char files[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
	private static Board board;
	private static final String filesString = "abcdefghij";
	private static final int ranks[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	private static boolean whiteCheck = false;
	private static boolean blackCheck = false;
	private static boolean player; //black is true
	private static char nextPlayer = ' ';
	private static String castlingOpps = "";
	private static int halfmoveClock = 0;
	private static int moveCounter = 0;
	private static boolean checkMate = false;
	boolean check;

	public static void initializeBoard(File moveFile) {
		char[][] boardPlacement = BoardValidationChecks.getBoardPlacement();
		nextPlayer = BoardValidationChecks.getNextPlayer().charAt(0);
		if (nextPlayer == 'b') player = true;
		castlingOpps = BoardValidationChecks.getCastlingOpps();
		halfmoveClock = Integer.parseInt(BoardValidationChecks.getHalfmoveClock());
		moveCounter =  Integer.parseInt(BoardValidationChecks.getMoveCounter());
		
		board = new Board();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boolean free = (boardPlacement[i][j]=='.');

				
				if (boardPlacement[i][j] == 'p' || boardPlacement[i][j] == 'P') {
					Pawn current = new Pawn(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'd' || boardPlacement[i][j] == 'D'){
					DrunkenSoldier current = new DrunkenSoldier(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'b' || boardPlacement[i][j] == 'B') {
					Bishop current = new Bishop(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'r' || boardPlacement[i][j] == 'R') {
					Rook current = new Rook(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'n' || boardPlacement[i][j] == 'N') {
					Knight current = new Knight(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'k' || boardPlacement[i][j] == 'K') {
					King current = new King(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'q' || boardPlacement[i][j] == 'Q') {
					Queen current = new Queen(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'e' || boardPlacement[i][j] == 'E') {
					Elephant current = new Elephant(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'f' || boardPlacement[i][j] == 'F') {
					FlyingDragon current = new FlyingDragon(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'w' || boardPlacement[i][j] == 'W') {
					Princess current = new Princess(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'a' || boardPlacement[i][j] == 'A') {
					Amazon current = new Amazon(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else {
					Piece current = new Piece(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]), free);;
					board.setPiece(current, i, j);
				}
			}
		}
		
		board.print();

		// play game from move file
		String src = "";
		String dst = "";
		Scanner scAllMoves = null;
		try {
			scAllMoves = new Scanner(moveFile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		int countLines = 0;
		while (scAllMoves.hasNextLine()) {
			countLines++;
			String currentMove = scAllMoves.nextLine();
			Scanner scMove;
			boolean error = false;
			
			if (currentMove.startsWith("%")) {
				continue;
			}
			
			if (currentMove.contains("0-0")) {
				// no pieces in between 
				// check castling rules
				/*
				 1.4.7 Castling
					Castling is a move that can be performed by a King and one of the two Rooks,
					given that neither the chosen Rook nor the King have moved since the start
					of the game. Castling is either queenside, where the King moves a distance of
					three squares to the left, or kingside, where the King moves a distance of three
					squares to the right. The move is completed by the Rook then jumping over
					the King, landing adjacent to the King. Castling may only be done if the King
					has never moved, the Rook involved has never moved, the squares between the
					King and the Rook involved are unoccupied, the King is not in check, and the
					King does not cross over or end on a square in check. Figure 2 illustrates the
					castling opportunities.
					
					kings always in col f and rooks always in last columns
				 */
				if (player)
				
				continue;
			} else {
				// get the move
				// e2-g10=R, e2xg6, e2-g4+
				String[] arr = currentMove.split("[/-]|[/+]|[x]|[/=]");
				src = arr[0];
				int srcCol = arr[0].charAt(0) - 'a';
				int srcRow = 10 - Integer.parseInt(arr[0].substring(1));
				
				dst = arr[1];
				int dstCol = arr[1].charAt(0) - 'a';
				int dstRow = 10 - Integer.parseInt(arr[1].substring(1));
				System.out.println();
				System.out.println(src + " = "+srcRow+srcCol);
				System.out.println(dst + " = "+dstRow+dstCol);
				System.out.println();
				
				Piece source = board.getPiece(srcRow, srcCol);
				Piece destination = board.getPiece(dstRow, dstCol);
				
				// check if source piece is legal
				boolean illegalMove = false;
				boolean illegalCapture = false;
				
				if (board.getPiece(srcRow, srcCol).isFree()) {
					// error - no source piece (i.e. it is empty)
					System.out.println("_______________________No source piece____________________________");
					error = true;
					if (currentMove.contains("x")) {
						illegalCapture=true;
					} else if (currentMove.contains("-")) {
						illegalMove=true;
					}
				} else {
					// is this piece mine (i.e. source piece is black for black player)
					error = !checkCorrectPlayer(board.getPiece(srcRow, srcCol));
					if (error) {
					System.out.println("_______________________Incorrect player____________________________");
					}
				}
				
				//returns error if there is a problem with the source piece
				if (error) {
					if (currentMove.contains("x")) {
						illegalCapture=true;
						MoveValidationErrors.illegalCapture(countLines);
					} else if (currentMove.contains("-")) {
						illegalMove=true;
						MoveValidationErrors.illegalMove(countLines);
					}
				}
				
				
				// checks destination slots on board
				if (currentMove.contains("x")) { // capture, should move to a slot that is not free
					if (board.getPiece(dstRow, dstCol).isFree()) {
						// illegal capture
						System.out.println("_______________________Capture cell is free____________________________");
						MoveValidationErrors.illegalCapture(countLines);
					} else if (board.getPiece(dstRow, dstCol).isBlack() == player) { // capture, moving to slot that has friendly piece
						System.out.println("_______________________Capture cell has friendly piece____________________________");
						MoveValidationErrors.illegalCapture(countLines);
					}
				} else { // move, should move to a slot that is free
					if (!board.getPiece(dstRow, dstCol).isFree()) {
						// illegal move
						System.out.println("_______________________Move destination cell is not free____________________________");
						MoveValidationErrors.illegalMove(countLines);
					}
				}
				
				
				// check that the move is valid
				if (!board.getPiece(srcRow, srcCol).validMove(srcRow, srcCol, dstRow, dstCol, board)) {
					if (currentMove.contains("x")) { // capture
						// illegal capture
						System.out.println("_______________________Capture destination cell is not valid____________________________");
						MoveValidationErrors.illegalCapture(countLines);
					} else { // move
						// illegal move
						System.out.println("_______________________Move destination cell is not valid____________________________");
						MoveValidationErrors.illegalMove(countLines);
					}
				}
				
				// make the move
				Piece moved = source;
				moved.setRow(dstRow);
				moved.setCol(dstCol);
//				

				Piece fill = new Piece(srcRow, srcCol, '.', false, true);
				board.setPiece(moved, dstRow, dstCol);
				board.setPiece(fill, srcRow, srcCol);
				
				// promotion 
				if (currentMove.contains("=")) { // g9-g10=Q+
					char promoteTo = arr[2].charAt(0);
					// TODO:
					
				}
			}		
			
			// check what happens as a result of the move
			boolean whiteInCheckBefore = whiteCheck;
			boolean blackInCheckBefore = blackCheck;
			checkPlayerInCheck();
			
			// are we in check?
			if (player) {
				if (blackCheck) {
					//error
					error = true;
				}
			} else {
				if (whiteCheck) {
					//error
					error = true;
				}
			}
			
			// is opponent in check if we claimed they would be?
			if (currentMove.contains("+")) {
				if (player) {
					if (!whiteCheck) {
						//error
						error = true;
						MoveValidationErrors.illegalCheck(countLines);
					}
				} else {
					if (!blackCheck) {
						//error
						error = true;
						MoveValidationErrors.illegalCheck(countLines);
					}
				}
			} else {
				if (player && blackCheck && blackInCheckBefore == false) {
					System.out.println("____________________________________________________black player and black check created");
					error = true;
//					MoveValidationErrors.illegalMove(countLines);
				} else if (!player && whiteCheck && whiteInCheckBefore == false) {
					System.out.println("____________________________________________________white player and white check created");
					error = true;
//					MoveValidationErrors.illegalMove(countLines);
				} else if (blackInCheckBefore == true && blackCheck == true) {
					System.out.println("____________________________________________________black did not move out of check");
					error = true;
//					MoveValidationErrors.illegalMove(countLines);
				} else if (whiteInCheckBefore == true && whiteCheck == true) {
					System.out.println("____________________________________________________white did not move out of check");
					error = true;
//					MoveValidationErrors.illegalMove(countLines);
				} else if ((blackCheck && blackInCheckBefore == false) || (whiteCheck && whiteInCheckBefore == false)) {
					System.out.println("____________________________________________________+ was not included to indicate check");
					error = true;
//					MoveValidationErrors.illegalMove(countLines);
				}
			}
			
			if (error) {
				if (currentMove.contains("x")) {
//					illegalCapture=true;
					MoveValidationErrors.illegalCapture(countLines);
				} else if (currentMove.contains("-")) {
//					illegalMove=true;
					MoveValidationErrors.illegalMove(countLines);
				}
			}
			
			
			
			if (currentMove.contains("+")) { // check
				scMove = new Scanner(currentMove).useDelimiter("-");
				src = scMove.next();
				dst = scMove.next();
				int srcCol = filesString.indexOf(src.charAt(0));
				int srcRow = 10 - (Integer.parseInt(src.substring(1)));
				System.out.println(src + " = "+srcRow+srcCol);
				int dstCol = filesString.indexOf(dst.charAt(0));
				int dstRow = 10 - (Integer.parseInt(dst.substring(1, dst.length()-1)));
				System.out.println(dst + " = "+dstRow+dstCol);
				Piece source = board.getPiece(srcRow, srcCol);
				Piece destination = board.getPiece(dstRow, dstCol);
				
				checkValidMoveNormal(source, destination, countLines);
				
				Piece moved = source;
				moved.setRow(dstRow);
				moved.setCol(dstCol);
				
				boolean whiteInCheckBefore = whiteCheck;
				boolean blackInCheckBefore = blackCheck;
				
				checkPlayerInCheck();
				
				System.out.println("White in check before: "+whiteInCheckBefore);
				System.out.println("White in check now: "+whiteCheck);
				System.out.println("Black in check before: "+blackInCheckBefore);
				System.out.println("Black in check now: "+blackCheck);
				System.out.println("black: "+player+" in check: black "+blackCheck+" white "+whiteCheck);
				if (player && blackCheck && blackInCheckBefore == false) {
					System.out.println("____________________________________________________black player and black check created");
					MoveValidationErrors.illegalMove(countLines);
				} else if (!player && whiteCheck && whiteInCheckBefore == false) {
					System.out.println("____________________________________________________white player and white check created");
					MoveValidationErrors.illegalMove(countLines);
				} else if (blackInCheckBefore == true && blackCheck == true) {
					System.out.println("____________________________________________________black did not move out of check");
					MoveValidationErrors.illegalMove(countLines);
				} else if (whiteInCheckBefore == true && whiteCheck == true) {
					System.out.println("____________________________________________________white did not move out of check");
					MoveValidationErrors.illegalMove(countLines);
				} 
			} else if (currentMove.contains("x")) { // capture
				scMove = new Scanner(currentMove).useDelimiter("x");
				src = scMove.next();
				dst = scMove.next();
				int srcCol = filesString.indexOf(src.charAt(0));
				int srcRow = 10 - (Integer.parseInt(src.substring(1)));
				System.out.println(src + " = "+srcRow+srcCol);
				int dstCol = filesString.indexOf(dst.charAt(0));
				int dstRow = 10 - (Integer.parseInt(dst.substring(1)));
				System.out.println(dst + " = "+dstRow+dstCol);
				Piece source = board.getPiece(srcRow, srcCol);
				Piece destination = board.getPiece(dstRow, dstCol);
				
				//checks that the move is valid
				checkValidCapture(source, destination, countLines);
				
				if (checkMate) {
					MoveValidationErrors.illegalCapture(countLines);
				}
				
				Piece moved = source;
				moved.setRow(dstRow);
				moved.setCol(dstCol);
				
				if (moved instanceof Pawn){
					((Pawn) moved).setMoved();
				}
				
				if (player){
					moveCounter++;
				}
				if (checkMate) {
					MoveValidationErrors.illegalCapture(countLines);
				}
				
				Piece fill = new Piece(srcRow, srcCol, '.', false, true);
				board.setPiece(moved, dstRow, dstCol);
				board.setPiece(fill, srcRow, srcCol);
				
				boolean whiteInCheckBefore = whiteCheck;
				boolean blackInCheckBefore = blackCheck;
				
				checkPlayerInCheck();
				
				System.out.println("White in check before: "+whiteInCheckBefore);
				System.out.println("White in check now: "+whiteCheck);
				System.out.println("Black in check before: "+blackInCheckBefore);
				System.out.println("Black in check now: "+blackCheck);
				System.out.println("black: "+player+" in check: black "+blackCheck+" white "+whiteCheck);
				if (player && blackCheck && blackInCheckBefore == false) {
					System.out.println("____________________________________________________black player and black check created");
					MoveValidationErrors.illegalMove(countLines);
				} else if (!player && whiteCheck && whiteInCheckBefore == false) {
					System.out.println("____________________________________________________white player and white check created");
					MoveValidationErrors.illegalMove(countLines);
				} else if (blackInCheckBefore == true && blackCheck == true) {
					System.out.println("____________________________________________________black did not move out of check");
					MoveValidationErrors.illegalMove(countLines);
				} else if (whiteInCheckBefore == true && whiteCheck == true) {
					System.out.println("____________________________________________________white did not move out of check");
					MoveValidationErrors.illegalMove(countLines);
				} else if ((blackCheck && blackInCheckBefore == false) || (whiteCheck && whiteInCheckBefore == false)) {
					System.out.println("____________________________________________________+ was not included to indicate check");
					MoveValidationErrors.illegalMove(countLines);
				}
				//do not increment half-move clock
			} else if (currentMove.contains("=")) { // promotion

			} else if (currentMove.contains("0-0")) { // castling
				
			} else { // normal move
				scMove = new Scanner(currentMove).useDelimiter("-");
				src = scMove.next();
				dst = scMove.next();
				int srcCol = filesString.indexOf(src.charAt(0));
				int srcRow = 10 - (Integer.parseInt(src.substring(1)));
				System.out.println(src + " = "+srcRow+srcCol);
				int dstCol = filesString.indexOf(dst.charAt(0));
				int dstRow = 10 - (Integer.parseInt(dst.substring(1)));
				System.out.println(dst + " = "+dstRow+dstCol);
				Piece source = board.getPiece(srcRow, srcCol);
				Piece destination = board.getPiece(dstRow, dstCol);
				
				//checks that the move is valid
				checkValidMoveNormal(source, destination, countLines);
				
				//performs the movement of the piece;
				Piece moved = source;
				moved.setRow(dstRow);
				moved.setCol(dstCol);
				
				if (moved instanceof Pawn){
					((Pawn) moved).setMoved();
				}
				
				if (!(source instanceof Pawn || source instanceof DrunkenSoldier)){
					halfmoveClock++;
				}
				
				if (player){
					moveCounter++;
				}
				if (checkMate) {
					MoveValidationErrors.illegalMove(countLines);
				}
				
				Piece fill = new Piece(srcRow, srcCol, '.', false, true);
				board.setPiece(moved, dstRow, dstCol);
				board.setPiece(fill, srcRow, srcCol);
				
				boolean whiteInCheckBefore = whiteCheck;
				boolean blackInCheckBefore = blackCheck;
				
				checkPlayerInCheck();
				
				System.out.println("White in check before: "+whiteInCheckBefore);
				System.out.println("White in check now: "+whiteCheck);
				System.out.println("Black in check before: "+blackInCheckBefore);
				System.out.println("Black in check now: "+blackCheck);
				System.out.println("black: "+player+" in check: black "+blackCheck+" white "+whiteCheck);
				if (player && blackCheck && blackInCheckBefore == false) {
					System.out.println("____________________________________________________black player and black check created");
					MoveValidationErrors.illegalMove(countLines);
				} else if (!player && whiteCheck && whiteInCheckBefore == false) {
					System.out.println("____________________________________________________white player and white check created");
					MoveValidationErrors.illegalMove(countLines);
				} else if (blackInCheckBefore == true && blackCheck == true) {
					System.out.println("____________________________________________________black did not move out of check");
					MoveValidationErrors.illegalMove(countLines);
				} else if (whiteInCheckBefore == true && whiteCheck == true) {
					System.out.println("____________________________________________________white did not move out of check");
					MoveValidationErrors.illegalMove(countLines);
				} else if ((blackCheck && blackInCheckBefore == false) || (whiteCheck && whiteInCheckBefore == false)) {
					System.out.println("____________________________________________________+ was not included to indicate check");
					MoveValidationErrors.illegalMove(countLines);
				}
			}
			board.print();
			player=!player;
		}
		
	}
	
	/*
	public static boolean isWhite(char piece) {
		return Character.isUpperCase(piece);
	}
	
	public static boolean isOccupied(Piece p) {
		return p.isFree();
	}
	*/

	private static void checkValidMoveNormal(Piece source, Piece destination, int line) {
		if (!checkCorrectPlayer(source)) {
			MoveValidationErrors.illegalMove(line);
		}
		checkDestinationOccupiedForNormal(source, destination, line);
		if (!checkValidDestination(source, destination, line)) {
			MoveValidationErrors.illegalMove(line);
		}
		checkHalfMoveClock(source, line);
	}

	private static boolean checkCorrectPlayer(Piece source) {
		boolean valid = true;
		System.out.println("Current player black: "+player);
		System.out.println("Current piece being moved is black: "+source.isBlack());
		System.out.println("Correct player: "+(player == source.isBlack()));
		if (player != source.isBlack()) {
			valid = false;
		}
		return valid;
	}
	
	private static boolean checkValidDestination(Piece source, Piece destination, int line) {
		System.out.print("source "+source.getRow());
		System.out.println(source.getCol());
		System.out.print("destination "+destination.getRow());
		System.out.println(destination.getCol());
		boolean valid = source.validMove(source.getRow(), source.getCol(), destination.getRow(), destination.getCol(), board);
//		if (!valid) {
//			System.out.println("error: illegal destination");
//			MoveValidationErrors.illegalMove(line);
//		}
		return valid;
	}

	private static void checkDestinationOccupiedForNormal(Piece source, Piece destination, int line) {
		if (destination.isFree() == false) {
			System.out.println("error: move position is occupied");
			MoveValidationErrors.illegalMove(line);
		}
	}
	
	// sets wether black or white is in check after a move
	private static void checkPlayerInCheck() {
		System.out.println();
		System.out.println("########################### Board right before checking for check ###############################");
		System.out.println();
		board.print();
		System.out.println();
		boolean whiteInCheckBefore = whiteCheck;
		boolean blackInCheckBefore = blackCheck;
		Piece whiteKing = null;
		Piece blackKing = null;
		Piece king = null;
		String check = "";
		blackCheck = false;
		whiteCheck = false;
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board.getPiece(i, j).getPieceChar() == 'k') {
					blackKing = board.getPiece(i, j);
				}
				if (board.getPiece(i, j).getPieceChar() == 'K') {
					whiteKing = board.getPiece(i, j);
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boolean checkPossible;
				boolean posPlayer = board.getPiece(i, j).isBlack();
				
				if (posPlayer) {
					king = whiteKing;
				} else {
					king = blackKing;
				}

				if (board.getPiece(i, j) instanceof Pawn || board.getPiece(i, j) instanceof DrunkenSoldier) {
					checkPossible = board.getPiece(i, j).validCapture(i, j, king.getRow(), king.getCol(), board);
					System.out.println("Checking D or P @ "+i+j);
				} else {
					checkPossible = board.getPiece(i, j).validMove(i, j, king.getRow(), king.getCol(), board);
				}
				if (!posPlayer && !(board.getPiece(i, j).isFree() || board.getPiece(i, j) instanceof King) && checkPossible) {
					System.out.println("the piece " + board.getPiece(i, j).getPieceChar() + " can attack the king at position " + king.getRow() + king.getCol());
					if (posPlayer) {
						System.out.println("************************ white is in check *****************************");
						whiteCheck = true;
					} else {
						System.out.println("************************ black is in check *****************************");
						blackCheck = true;
					}
					// set when white is in check
					break;
				}
			}
			}
	}
	
//	private static boolean checkKingRemovedFromCheck(Piece destination){
//		boolean removed = true;
//		Board temp = board;
//		return removed;
//	}
	
	private static void checkHalfMoveClock(Piece source, int line){
		if (halfmoveClock==50){
			if (!(source instanceof Pawn || source instanceof DrunkenSoldier)){
				System.out.println("error: half move clock and move not allowed");
				MoveValidationErrors.illegalMove(line);
			}
		}
	}
	
	private static void checkValidCapture(Piece source, Piece destination, int line) {
		if (!checkCorrectPlayer(source)) {
			MoveValidationErrors.illegalCapture(line);
		}
		checkDestinationHasOpponentPiece(destination, line);
		if (!checkValidCaptureDestination(source, destination, line)) {
			MoveValidationErrors.illegalCapture(line);
		}
		
	}
	
	private static void checkDestinationHasOpponentPiece(Piece destination, int line) {
		if (destination.isBlack() == player) {
			System.out.println("error: move position is occupied");
			MoveValidationErrors.illegalCapture(line);
		}
	}
	
	private static boolean checkValidCaptureDestination(Piece source, Piece destination, int line) {
		System.out.print("source "+source.getRow());
		System.out.println(source.getCol());
		System.out.print("destination "+destination.getRow());
		System.out.println(destination.getCol());
		boolean valid;
		if (source instanceof Pawn || source instanceof DrunkenSoldier) {
			valid = source.validCapture(source.getRow(), source.getCol(), destination.getRow(), destination.getCol(),board);
		} else {
			valid = source.validMove(source.getRow(), source.getCol(), destination.getRow(), destination.getCol(),board);
		}
//		if (!valid) {
//			System.out.println("error: illegal destination");
//			MoveValidationErrors.illegalMove(line);
//		}
		return valid;
	}
	
	private static boolean checkIssuesForCheck(boolean checkMove){
		boolean valid = true;
		boolean whiteInCheckBefore = whiteCheck;
		boolean blackInCheckBefore = blackCheck;
		
		checkPlayerInCheck();
		
		System.out.println("White in check before: "+whiteInCheckBefore);
		System.out.println("White in check now: "+whiteCheck);
		System.out.println("Black in check before: "+blackInCheckBefore);
		System.out.println("Black in check now: "+blackCheck);
		System.out.println("black: "+player+" in check: black "+blackCheck+" white "+whiteCheck);
		if (player && blackCheck && blackInCheckBefore == false) {
			System.out.println("____________________________________________________black player and black check created");
			valid = false;
//			MoveValidationErrors.illegalMove(countLines);
		} else if (!player && whiteCheck && whiteInCheckBefore == false) {
			System.out.println("____________________________________________________white player and white check created");
			valid = false;
//			MoveValidationErrors.illegalMove(countLines);
		} else if (blackInCheckBefore == true && blackCheck == true) {
			System.out.println("____________________________________________________black did not move out of check");
			valid = false;
//			MoveValidationErrors.illegalMove(countLines);
		} else if (whiteInCheckBefore == true && whiteCheck == true) {
			System.out.println("____________________________________________________white did not move out of check");
			valid = false;
//			MoveValidationErrors.illegalMove(countLines);
		} else if ((blackCheck && blackInCheckBefore == false) || (whiteCheck && whiteInCheckBefore == false)) {
			System.out.println("____________________________________________________+ was not included to indicate check");
//			MoveValidationErrors.illegalMove(countLines);
		}
		
		return valid;
	}
}

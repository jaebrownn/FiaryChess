import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MoveValidationChecks {
	private static Board board;
	private static boolean whiteCheck = false;
	private static boolean blackCheck = false;
	private static boolean player; // black is true
	private static char nextPlayer = ' ';
	private static String castlingOpps = "";
	private static char[] newCastlingOpps = new char[4];
	private static int halfmoveClock = 0;
	private static int moveCounter = 0;
	private static boolean checkMate = false;
	boolean check;
	private static int[] pieceNums;
	private static final char pieceChars[] = { 'k', 'r', 'q', 'n', 'b', 'd', 'f', 'e', 'p', 'a', 'w' };
	private static final String pieceIndexes = new String(pieceChars);
	private static String pieces;

	public static void initializeBoard(File moveFile) {
		
		char[][] boardPlacement = BoardInitialization.getBoardPlacement();
		nextPlayer = BoardInitialization.getNextPlayer().charAt(0);
		if (nextPlayer == 'b') { player = true; }
		castlingOpps = BoardInitialization.getCastlingOpps();
		halfmoveClock = Integer.parseInt(BoardInitialization.getHalfmoveClock());
		moveCounter = Integer.parseInt(BoardInitialization.getMoveCounter());
		pieceNums = BoardInitialization.getPieceNums();
		pieces = BoardInitialization.getPiecesStr();

		for (int i = 0; i < 4; i++) {
			newCastlingOpps[i] = castlingOpps.charAt(i);
		}

		board = new Board();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boolean free = (boardPlacement[i][j] == '.');

				if (boardPlacement[i][j] == 'p' || boardPlacement[i][j] == 'P') {
					Pawn current = new Pawn(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'd' || boardPlacement[i][j] == 'D') {
					DrunkenSoldier current = new DrunkenSoldier(i, j, boardPlacement[i][j],
							Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'b' || boardPlacement[i][j] == 'B') {
					Bishop current = new Bishop(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'r' || boardPlacement[i][j] == 'R') {
					Rook current = new Rook(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'n' || boardPlacement[i][j] == 'N') {
					Knight current = new Knight(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'k' || boardPlacement[i][j] == 'K') {
					King current = new King(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'q' || boardPlacement[i][j] == 'Q') {
					Queen current = new Queen(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'e' || boardPlacement[i][j] == 'E') {
					Elephant current = new Elephant(i, j, boardPlacement[i][j],
							Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'f' || boardPlacement[i][j] == 'F') {
					FlyingDragon current = new FlyingDragon(i, j, boardPlacement[i][j],
							Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'w' || boardPlacement[i][j] == 'W') {
					Princess current = new Princess(i, j, boardPlacement[i][j],
							Character.isLowerCase(boardPlacement[i][j]), free);
					board.setPiece(current, i, j);
				} else if (boardPlacement[i][j] == 'a' || boardPlacement[i][j] == 'A') {
					Amazon current = new Amazon(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					board.setPiece(current, i, j);
				} else {
					Piece current = new Piece(i, j, boardPlacement[i][j], Character.isLowerCase(boardPlacement[i][j]),
							free);
					;
					board.setPiece(current, i, j);
				}
			}
		}

		// play game from move file
		String src = "";
		String dst = "";
		Scanner scAllMoves = null;
		try {
			scAllMoves = new Scanner(moveFile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Move file does not exist");
		}

		int countLines = 0;
		
		
		
		
		while (scAllMoves.hasNextLine() && !checkMate) {
			
			countLines++;
			String currentMove = scAllMoves.nextLine();
			Scanner scMove;
			boolean error = false;

			if (currentMove.startsWith("%")) {
				continue;
			}
			
/*************************************************************************************checks the castling moves*************************************************************************************************/
			if (currentMove.contains("0-0")) {
				boolean validCastle = true;
				boolean queenside = currentMove.contains("0-0-0");
				Piece kingCast = null;
				Piece rookCast = null;

				if (halfmoveClock != 50) {
					if (player) {
						if (board.getPiece(0, 5) instanceof King) {
							if (!((King) board.getPiece(0, 5)).getMoved()) {
								if (queenside) {
									if (board.getPiece(0, 0) instanceof Rook && newCastlingOpps[0] == '+') {
										if (!((Rook) board.getPiece(0, 0)).getMoved()) {
											for (int i = 1; i < 5; i++) {
												if (!(board.getPiece(0, i).isFree())) {
													validCastle = false;
												}
											}
											if (validCastle) {
												kingCast = board.getPiece(0, 5);
												rookCast = board.getPiece(0, 0);
												newCastlingOpps[0] = '-';
											}
										} else {
											validCastle = false;
										}
									} else {
										validCastle = false;
									}
								} else {
									if (board.getPiece(0, 9) instanceof Rook && newCastlingOpps[1] == '+') {
										if (!((Rook) board.getPiece(0, 9)).getMoved()) {
											for (int i = 6; i < 9; i++) {
												if (!(board.getPiece(0, i).isFree())) {
													validCastle = false;
												}
											}
											if (validCastle) {
												kingCast = board.getPiece(0, 5);
												rookCast = board.getPiece(0, 9);
												newCastlingOpps[1] = '-';
											}
										} else {
											validCastle = false;
										}
									} else {
										validCastle = false;
									}
								}
							} else {
								validCastle = false;
							}
						} else {
							validCastle = false;
						}
					} else {
						if (board.getPiece(9, 5) instanceof King) {
							if (!((King) board.getPiece(9, 5)).getMoved()) {
								if (queenside) {
									if (board.getPiece(9, 0) instanceof Rook && newCastlingOpps[2] == '+') {
										if (!((Rook) board.getPiece(9, 0)).getMoved()) {
											for (int i = 1; i < 5; i++) {
												if (!(board.getPiece(9, i).isFree())) {
													validCastle = false;
													break;
												}
											}
											if (validCastle) {
												kingCast = board.getPiece(9, 5);
												rookCast = board.getPiece(9, 0);
												newCastlingOpps[2] = '-';
											}
										} else {
											validCastle = false;
										}
									} else {
										validCastle = false;
									}
								} else {
									if (board.getPiece(9, 9) instanceof Rook && newCastlingOpps[3] == '+') {
										if (!((Rook) board.getPiece(9, 9)).getMoved()) {
											for (int i = 6; i < 9; i++) {
												if (!(board.getPiece(9, i).isFree())) {
													validCastle = false;
													break;
												}
											}
											if (validCastle) {
												kingCast = board.getPiece(9, 5);
												rookCast = board.getPiece(9, 9);
												newCastlingOpps[3] = '-';
											}
										} else {
											validCastle = false;
										}
									} else {
										validCastle = false;
									}
								}
							} else {
								validCastle = false;
							}
						} else {
							validCastle = false;
						}
					}
				} else {
					validCastle = false;
				}

				if (validCastle) {
					halfmoveClock++;
					if (queenside) {
						Piece moved1 = kingCast;
						moved1.setCol(2);

						Piece moved2 = rookCast;
						moved2.setCol(3);

						board.setPiece(moved1, moved1.getRow(), 2);
						board.setPiece(moved2, moved2.getRow(), 3);

						Piece fill1 = new Piece(moved1.getRow(), 5, '.', false, true);
						Piece fill2 = new Piece(moved2.getRow(), 0, '.', false, true);

						board.setPiece(fill1, fill1.getRow(), 5);
						board.setPiece(fill2, fill2.getRow(), 0);
					} else {
						Piece moved1 = kingCast;
						moved1.setCol(8);

						Piece moved2 = rookCast;
						moved2.setCol(7);

						board.setPiece(moved1, moved1.getRow(), 8);
						board.setPiece(moved2, moved2.getRow(), 7);

						Piece fill1 = new Piece(moved1.getRow(), 5, '.', false, true);
						Piece fill2 = new Piece(moved2.getRow(), 9, '.', false, true);

						board.setPiece(fill1, fill1.getRow(), 5);
						board.setPiece(fill2, fill2.getRow(), 9);
					}
				} else {
					MoveValidationErrors.illegalCastlingMove(countLines);
				}
				
/*************************************************************************************checks a move or capture*************************************************************************************************/
			} else {
				String[] arr = currentMove.split("[/-]|[/+]|[x]|[/=]");
				src = arr[0];
				int srcCol = arr[0].charAt(0) - 'a';
				int srcRow = 10 - Integer.parseInt(arr[0].substring(1));

				dst = arr[1];
				int dstCol = arr[1].charAt(0) - 'a';
				int dstRow = 10 - Integer.parseInt(arr[1].substring(1));
				Piece source = board.getPiece(srcRow, srcCol);
				Piece destination = board.getPiece(dstRow, dstCol);

				// check if source piece is legal
				boolean illegalMove = false;
				boolean illegalCapture = false;

				if (board.getPiece(srcRow, srcCol).isFree()) {
					error = true;
				} else {
					// is this piece mine (i.e. source piece is black for black player)
					error = !checkCorrectPlayer(board.getPiece(srcRow, srcCol));
					if (error) {
					}
				}

				// returns error if there is a problem with the source piece
				if (error) {
					if (currentMove.contains("x")) {
						MoveValidationErrors.illegalCapture(countLines);
					} else if (currentMove.contains("-")) {
						MoveValidationErrors.illegalMove(countLines);
					}
				} else {
					checkHalfMoveClock(source, countLines);
				}

				// checks destination slots on board
				if (currentMove.contains("x")) { // capture, should move to a slot that is not free
					if (board.getPiece(dstRow, dstCol).isFree() || board.getPiece(dstRow, dstCol).isBlack() == player) {
						// illegal capture
						MoveValidationErrors.illegalCapture(countLines);
					}
				} else { // move, should move to a slot that is free
					if (!board.getPiece(dstRow, dstCol).isFree()) {
						// illegal move
						MoveValidationErrors.illegalMove(countLines);
					}
				}

				// check that the move is valid
				if (!board.getPiece(srcRow, srcCol).validMove(srcRow, srcCol, dstRow, dstCol, board)) {
					if (currentMove.contains("x")) { // capture
						// illegal capture
						MoveValidationErrors.illegalCapture(countLines);
					} else { // move
						// illegal move
						MoveValidationErrors.illegalMove(countLines);
					}
				}

				boolean promotion = false;
				char promoteTo = ' ';
				// promotion
				if (currentMove.contains("=")) { // g9-g10=Q+
					promoteTo = arr[2].charAt(0);
					promotion = true;
					if (!(player == Character.isLowerCase(promoteTo))) {
						MoveValidationErrors.illegalPromotion(countLines);
					}

					if (!(pieceNums[pieceIndexes.indexOf((promoteTo+"").toLowerCase().charAt(0))] == 0 || promoteTo == 'E' || promoteTo == 'e')) {
						if (player) {
							if (dstRow != 9) {
								MoveValidationErrors.illegalPromotion(countLines);
							}
						} else {
							if (dstRow != 0) {
								MoveValidationErrors.illegalPromotion(countLines);
							}
						}
					} else {
						MoveValidationErrors.illegalPromotion(countLines);
					}

				}

				// make the move

				Piece moved = null;
				if (promotion) {
					if (promoteTo == 'p' || promoteTo == 'P') {
						moved = new Pawn(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'd' || promoteTo == 'D') {
						moved = new DrunkenSoldier(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'b' || promoteTo == 'B') {
						moved = new Bishop(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'r' || promoteTo == 'R') {
						moved = new Rook(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'n' || promoteTo == 'N') {
						moved = new Knight(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'k' || promoteTo == 'K') {
						moved = new King(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'a' || promoteTo == 'A') {
						moved = new Amazon(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'q' || promoteTo == 'Q') {
						moved = new Queen(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'w' || promoteTo == 'W') {
						moved = new Princess(dstRow, dstCol, promoteTo, player, false);
					} else if (promoteTo == 'f' || promoteTo == 'F') {
						moved = new FlyingDragon(dstRow, dstCol, promoteTo, player, false);
					}
				} else {
					moved = source;
					moved.setRow(dstRow);
					moved.setCol(dstCol);
				}

				if (moved instanceof Pawn) {
					((Pawn) moved).setMoved();
				} else if (moved instanceof King) {
					((King) moved).setMoved();
				} else if (moved instanceof Rook) {
					((Rook) moved).setMoved();
				}

				Piece fill = new Piece(srcRow, srcCol, '.', false, true);
				board.setPiece(moved, dstRow, dstCol);
				board.setPiece(fill, srcRow, srcCol);

				if (!currentMove.contains("x") && !(source instanceof Pawn || source instanceof DrunkenSoldier)) {
					halfmoveClock++;
				} else {
					halfmoveClock = 0;
				}
			}

			// check what happens as a result of the move
			boolean whiteInCheckBefore = whiteCheck;
			boolean blackInCheckBefore = blackCheck;
			checkPlayerInCheck();

			// are we in check?
			if (player) {
				if (blackCheck) {
					// error
					error = true;
				}
			} else {
				if (whiteCheck) {
					// error
					error = true;
				}
			}

			// is opponent in check if we claimed they would be?
			if (currentMove.contains("+")) {
				if (player) {
					if (!whiteCheck) {
						// error
						error = true;
						MoveValidationErrors.illegalCheck(countLines);
					}
				} else {
					if (!blackCheck) {
						// error
						error = true;
						MoveValidationErrors.illegalCheck(countLines);
					}
				}
			} else {
				if (player && blackCheck && blackInCheckBefore == false) {
					error = true;
				} else if (!player && whiteCheck && whiteInCheckBefore == false) {
					error = true;
				} else if (blackInCheckBefore == true && blackCheck == true) {
					error = true;
				} else if (whiteInCheckBefore == true && whiteCheck == true) {
					error = true;
				} else if ((blackCheck && blackInCheckBefore == false) || (whiteCheck && whiteInCheckBefore == false)) {
					error = true;
				}
			}

			if (error) {
				if (currentMove.contains("x")) {
					MoveValidationErrors.illegalCapture(countLines);
				} else if (currentMove.contains("-")) {
					MoveValidationErrors.illegalMove(countLines);
				}
			}

			if (player) {
				moveCounter++;
			}
			if (checkMate) {
//				System.out.println("Game over!");
			}

			player = !player;
		}

		boolean validCastle = true;
		if (board.getPiece(0, 5) instanceof King) {
			if (!((King) board.getPiece(0, 5)).getMoved()) {
				if (board.getPiece(0, 0) instanceof Rook && castlingOpps.charAt(0) == '+') {
					if (!((Rook) board.getPiece(0, 0)).getMoved()) {
						for (int i = 1; i < 5; i++) {
							if (!(board.getPiece(0, i).isFree())) {
								validCastle = false;
							}
						}
						if (validCastle) {
							newCastlingOpps[0] = '+';
						}
					} else {
						newCastlingOpps[0] = '-';
						validCastle = false;
					}
				} else {
					newCastlingOpps[0] = '-';
					validCastle = false;
				}
				if (board.getPiece(0, 9) instanceof Rook && castlingOpps.charAt(1) == '+') {
					if (!((Rook) board.getPiece(0, 9)).getMoved()) {
						for (int i = 6; i < 9; i++) {
							if (!(board.getPiece(0, i).isFree())) {
								validCastle = false;
							}
						}
						if (validCastle) {
							newCastlingOpps[1] = '+';
						}
					} else {
						newCastlingOpps[1] = '-';
						validCastle = false;
					}
				} else {
					newCastlingOpps[1] = '-';
					validCastle = false;
				}
			} else {
				newCastlingOpps[0] = '-';
				newCastlingOpps[1] = '-';
				validCastle = false;
			}
		} else {
			validCastle = false;
			newCastlingOpps[0] = '-';
			newCastlingOpps[1] = '-';
		}
		
		if (board.getPiece(9, 5) instanceof King) {
			if (!((King) board.getPiece(9, 5)).getMoved()) {
				if (board.getPiece(9, 0) instanceof Rook && castlingOpps.charAt(2) == '+') {
					if (!((Rook) board.getPiece(9, 0)).getMoved()) {
						for (int i = 1; i < 5; i++) {
							if (!(board.getPiece(9, i).isFree())) {
								validCastle = false;
								break;
							}
						}
						if (validCastle) {
							newCastlingOpps[2] = '+';
						}
					} else {
						validCastle = false;
						newCastlingOpps[2] = '-';
					}
				} else {
					validCastle = false;
					newCastlingOpps[2] = '-';
				}
				if (board.getPiece(9, 9) instanceof Rook && castlingOpps.charAt(3) == '+') {
					if (!((Rook) board.getPiece(9, 9)).getMoved()) {
						for (int i = 6; i < 9; i++) {
							if (!(board.getPiece(9, i).isFree())) {
								validCastle = false;
								break;
							}
						}
						if (validCastle) {
							newCastlingOpps[3] = '+';
						}
					} else {
						validCastle = false;
						newCastlingOpps[3] = '-';
					}
				} else {
					validCastle = false;
					newCastlingOpps[3] = '-';
				}
			} else {
				validCastle = false;
				newCastlingOpps[2] = '-';
				newCastlingOpps[3] = '-';
			}
		} else {
			validCastle = false;
			newCastlingOpps[2] = '-';
			newCastlingOpps[3] = '-';
		}

		String statusPl = "w", statusCastOpps = "", statusHalfMvClck = halfmoveClock + "",
				statusMvCntr = moveCounter + "", status;

		if (player) {
			statusPl = "b";
		}

		for (int i = 0; i < 4; i++) {
			statusCastOpps = statusCastOpps + newCastlingOpps[i];
		}
		
		status = statusPl+":"+statusCastOpps+":"+statusHalfMvClck+":"+statusMvCntr;
		

		System.out.println(pieces+"-----");
		board.print();
		System.out.println("-----");
		System.out.print(status);

	}



	private static boolean checkCorrectPlayer(Piece source) {
		boolean valid = true;
		if (player != source.isBlack()) {
			valid = false;
		}
		return valid;
	}

	// sets whether black or white is in check after a move
	private static void checkPlayerInCheck() {
		Piece whiteKing = null;
		Piece blackKing = null;
		Piece king = null;
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
				} else {
					checkPossible = board.getPiece(i, j).validMove(i, j, king.getRow(), king.getCol(), board);
				}
				if (!(board.getPiece(i, j).isFree() || board.getPiece(i, j) instanceof King)
						&& checkPossible) {
					if (posPlayer) {
						whiteCheck = true;
					} else {
						blackCheck = true;
					}
					break;
				}
			}
		}
	}


	private static void checkHalfMoveClock(Piece source, int line) {
		if (halfmoveClock == 50) {
			if (!(source instanceof Pawn || source instanceof DrunkenSoldier)) {
				MoveValidationErrors.illegalMove(line);
			}
		}
	}

}

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;


public class MoveValidationChecksGUIOld {
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
	private static char files[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
	private static int ranks[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	private static Stack<Piece> capturedStack = new Stack<Piece>();
	private static Stack<String> moveStack = new Stack<String>();
	private static boolean moveFileInput;
//	private static Stack undoMoves;

	public static void initializeBoard() {
		
		char[][] boardPlacement = BoardInitialization.getBoardPlacement();
		nextPlayer = BoardInitialization.getNextPlayer().charAt(0);
		if (nextPlayer == 'b') { player = true; }
		castlingOpps = BoardInitialization.getCastlingOpps();
		halfmoveClock = Integer.parseInt(BoardInitialization.getHalfmoveClock());
		moveCounter = Integer.parseInt(BoardInitialization.getMoveCounter());
		pieceNums = BoardInitialization.getPieceNums();
		pieces = BoardInitialization.getPiecesStr();
		Scanner scAllMoves = null;
		File moveFile = null;
		

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
		
		int dialogButton = JOptionPane.YES_NO_OPTION;
	    JOptionPane.showConfirmDialog (null, "Would you like to read the game from a move file?","Move file input",dialogButton);
	    
	    if (dialogButton == JOptionPane.YES_OPTION) {
	    	moveFileInput = true;
	    } else {
	    	moveFileInput = false;
	    }
	    
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(1350, 900); 
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.setXscale(-1, 17);
		StdDraw.setYscale(0, 12);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 48);
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.setFont(font);
		StdDraw.text(4.6, 11.2, "Fairy Chess");
		StdDraw.picture(7.5, 11.2, "Pictures/Chess-Game-grey.png", 1, 1);
		Font font2=new Font(Font.MONOSPACED, Font.BOLD, 20);
		for (int i = 1; i <= 10; i++) {
			StdDraw.setFont(font2);
			StdDraw.text(i, 0.2, ""+(char)(96+i));
			StdDraw.text(0.2, i, ""+i);
		}

		
		printGUI();
		StdDraw.show();

		if (moveFileInput) {

			String src = "";
			String dst = "";
			boolean valid = false;
			
			while (!valid) {
				String moveFileName = JOptionPane.showInputDialog("Enter the path of the move file.");
				try {
					moveFile = new File(moveFileName);
					scAllMoves = new Scanner(moveFile);
					valid = true;
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Illegal move file. Please provide a legal move file.");
					valid = false;
				}
			}
			
			
			
			while (scAllMoves.hasNextLine() && !checkMate) {
				String currentMove = scAllMoves.nextLine();
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledRectangle(14, 11, 2.85, 0.5);
				
				if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
				
				Scanner scMove;
				boolean error = false;

				
	/*************************************************************************************checks the castling moves*************************************************************************************************/
				if (currentMove.startsWith("%")) {
					continue;
				}
				
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
						JOptionPane.showMessageDialog(null, "Illegal castling attempt!");
						System.exit(0);
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
							JOptionPane.showMessageDialog(null, "Illegal capture!");
							System.exit(0);
						} else if (currentMove.contains("-")) {
							JOptionPane.showMessageDialog(null, "Illegal move!");
							System.exit(0);
						}
					} else {
						checkHalfMoveClock(source);
					}

					// checks destination slots on board
					if (currentMove.contains("x")) { // capture, should move to a slot that is not free
						if (board.getPiece(dstRow, dstCol).isFree() || board.getPiece(dstRow, dstCol).isBlack() == player) {
							// illegal capture
							JOptionPane.showMessageDialog(null, "Illegal capture!");
							System.exit(0);
						}
					} else { // move, should move to a slot that is free
						if (!board.getPiece(dstRow, dstCol).isFree()) {
							// illegal move
							JOptionPane.showMessageDialog(null, "Illegal move!");
							System.exit(0);
						}
					}

					// check that the move is valid
					if (!board.getPiece(srcRow, srcCol).validMove(srcRow, srcCol, dstRow, dstCol, board)) {
						if (currentMove.contains("x")) { // capture
							// illegal capture
							JOptionPane.showMessageDialog(null, "Illegal capture!");
							System.exit(0);
						} else { // move
							// illegal move
							JOptionPane.showMessageDialog(null, "Illegal move!");
							System.exit(0);
						}
					}

					boolean promotion = false;
					char promoteTo = ' ';
					// promotion
					if (currentMove.contains("=")) { // g9-g10=Q+
						promoteTo = arr[2].charAt(0);
						promotion = true;
						if (!(player == Character.isLowerCase(promoteTo))) {
							JOptionPane.showMessageDialog(null, "Illegal promotion!");
							System.exit(0);
						}

						if (!(pieceNums[pieceIndexes.indexOf((promoteTo+"").toLowerCase().charAt(0))] == 0 || promoteTo == 'E' || promoteTo == 'e')) {
							if (player) {
								if (dstRow != 9) {
									JOptionPane.showMessageDialog(null, "Illegal promotion!");
									System.exit(0);
								}
							} else {
								if (dstRow != 0) {
									JOptionPane.showMessageDialog(null, "Illegal promotion!");
									System.exit(0);
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Illegal promotion!");
							System.exit(0);
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
				checkPlayerInCheckMoveFile();

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
							JOptionPane.showMessageDialog(null, "Illegal check!");
							System.exit(0);
						}
					} else {
						if (!blackCheck) {
							// error
							error = true;
							JOptionPane.showMessageDialog(null, "Illegal check!");
							System.exit(0);
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
						JOptionPane.showMessageDialog(null, "Illegal capture!");
						System.exit(0);
					} else if (currentMove.contains("-")) {
						JOptionPane.showMessageDialog(null, "Illegal move!");
						System.exit(0);
					}
				}

				if (player) {
					moveCounter++;
				}
				if (checkMate) {
//					System.out.println("Game over!");
				}

				player = !player;
			}
				printGUI();
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
		} else {
		
		// play game from move file

		while (!checkMate) {
			String src = "";
			String dst = "";
			printGUI();
				// code to get the source and destination location
				int countClicks = 0;
				while (countClicks < 2) {

					if (StdDraw.isMousePressed()) {
						if (StdDraw.isMousePressed() && countClicks == 0) {
							src = getPieceFromClick("source");
							System.out.println("source: " + src);
							countClicks++;
						} else if (StdDraw.isMousePressed() && countClicks == 1) {
							dst = getPieceFromClick("destination");
							System.out.println("dest: " + dst);
							countClicks++;
						}

						if (src.equals("invalid")) {
							countClicks = 0;
						} else if (dst.equals("invalid")) {
							countClicks = 1;
						}
					}
				}

				boolean invalid = true;

				while (invalid) {
					if ('a' > (int) (src.charAt(0)) || 'j' < (int) (src.charAt(0))) {

					} else if ('a' > (int) (dst.charAt(0)) || 'j' < (int) (dst.charAt(0))) {

					} else if (1 > Integer.parseInt(src.substring(1)) || 10 < Integer.parseInt(src.substring(1))) {

					} else if (1 > Integer.parseInt(dst.substring(1)) || 10 < Integer.parseInt(dst.substring(1))) {

					} else {
						invalid = false;
					}
				}

			String currentMove = "";
			boolean error = false;
			Board temp = new Board();
			Piece moved = null;
			Piece fill = null;
			int srcRow, srcCol;
			int dstRow, dstCol;
			srcRow = dstRow = srcCol = dstCol = -1;
			
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
					JOptionPane.showMessageDialog(null, "Illegal castling opportunity");
					continue;
				}
				
/*************************************************************************************checks a move or capture*************************************************************************************************/
			} else {
				srcCol = src.charAt(0) - 'a';
				srcRow = 10 - Integer.parseInt(src.substring(1));

				dstCol = dst.charAt(0) - 'a';
				dstRow = 10 - Integer.parseInt(dst.substring(1));
				Piece source = board.getPiece(srcRow, srcCol);
				Piece destination = board.getPiece(dstRow, dstCol);
				
				if (destination.isFree()) {
					currentMove=src+"-"+dst;
				} else {
					currentMove=src+"x"+dst;
				}

				if (source instanceof Pawn || source instanceof DrunkenSoldier) {
					// alternative for if: board.getPiece(dstRow, dstCol) instanceof Pawn || board.getPiece(dstRow, dstCol) instanceof DrunkenSoldier && (dstRow == 9 || dstRow == 0)
					boolean promoting = false;
					if (player) {
						if (dstRow == 0) {
							currentMove = currentMove + "=";
							promoting = true;
						}
					} else {
						if (dstRow == 9) {
							currentMove = currentMove + "=";
							promoting = true;
						}
					}
					if (promoting) {
						String promotion = JOptionPane.showInputDialog("Please enter the character of the piece you'd like to promote to: ");
					}
				}

				// check if source piece is legal
				boolean illegalMove = false;
				boolean illegalCapture = false;

				if (board.getPiece(srcRow, srcCol).isFree()) {
					JOptionPane.showMessageDialog(null, "Please select a piece!");
					continue;
				} else {
					// is this piece mine (i.e. source piece is black for black player)
					error = !checkCorrectPlayer(board.getPiece(srcRow, srcCol));
					if (error) {
						JOptionPane.showMessageDialog(null, "Please select a piece of the correct colour!");
						continue;
					}
				}

				if (!checkHalfMoveClock(source)) {
					continue;
				}

				// checks destination slots on board
				
//				boolean captureAttempt = false;;
//				if (!board.getPiece(dstRow, dstCol).isFree()) {
//					captureAttempt = true;
//				}
				
				
				
				if (currentMove.contains("x")) { // capture, should move to a slot that is not free
					if (board.getPiece(dstRow, dstCol).isFree() || board.getPiece(dstRow, dstCol).isBlack() == player) {
						// illegal capture
						JOptionPane.showMessageDialog(null, "Illegal capture! Destination cell must have opponent players piece.");
						continue;
					}
				} else { // move, should move to a slot that is free
					if (!board.getPiece(dstRow, dstCol).isFree()) {
						// illegal move
						JOptionPane.showMessageDialog(null, "Illegal move! Destination cell must be empty.");
						continue;
					}
				}

				// check that the move is valid
				if (!board.getPiece(srcRow, srcCol).validMove(srcRow, srcCol, dstRow, dstCol, board)) {
					if (currentMove.contains("x")) { // capture
						// illegal capture
						if (!((board.getPiece(srcRow, srcCol) instanceof Pawn && ((Pawn)(board.getPiece(srcRow, srcCol))).validCapture(srcRow, srcCol, dstRow, dstCol, board)) || (board.getPiece(srcRow, srcCol) instanceof DrunkenSoldier && ((DrunkenSoldier)(board.getPiece(srcRow, srcCol))).validCapture(srcRow, srcCol, dstRow, dstCol, board)))) {
							JOptionPane.showMessageDialog(null, "Illegal capture! This piece cannot move to the desired cell.");
							continue;
						} 
						
//						else if (board.getPiece(srcRow, srcCol) instanceof DrunkenSoldier && ((DrunkenSoldier)(board.getPiece(srcRow, srcCol))).validCapture(srcRow, srcCol, dstRow, dstCol, board)) {
//							
//						} 
						
//						else {
//						JOptionPane.showMessageDialog(null, "Illegal capture! This piece cannot move to the desired cell.");
//						continue;
//						}
//						
						
					} else { // move
						// illegal move
						JOptionPane.showMessageDialog(null, "Illegal move! This piece cannot move to the desired cell.");
						continue;
					}
				}

				boolean promotion = false;
				char promoteTo = ' ';
				// promotion
				if (currentMove.contains("=")) {
					promotion = true;
					promoteTo = currentMove.charAt(currentMove.length()-1);
				}
					
				String officerIndexes = "krqnbfeaw";
				if (promotion) { // g9-g10=Q+
					
				if (!(officerIndexes.indexOf((promoteTo+"").toLowerCase().charAt(0))>=0)) {
					JOptionPane.showMessageDialog(null, "Illegal promotion!");
					continue;
				} else if ((pieceNums[pieceIndexes.indexOf((promoteTo+"").toLowerCase().charAt(0))] == 0 || promoteTo == 'E' || promoteTo == 'e')) {
						JOptionPane.showMessageDialog(null, "Illegal promotion!");
						continue;
					}
				}

				// make the move

				moved = null;
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

				fill = new Piece(srcRow, srcCol, '.', false, true);
				
				for (int i=0; i<10; i++) {
					for (int j=0; j<10; j++) {
						temp.setPiece(board.getPiece(i, j), i, j);
					}
				}
				
				temp.setPiece(moved, dstRow, dstCol);
				temp.setPiece(fill, srcRow, srcCol);

				if (!currentMove.contains("x") && !(source instanceof Pawn || source instanceof DrunkenSoldier)) {
					halfmoveClock++;
				} else {
					halfmoveClock = 0;
				}
			}

			// check what happens as a result of the move
			boolean whiteInCheckBefore = whiteCheck;
			boolean blackInCheckBefore = blackCheck;
			checkPlayerInCheck(temp);
			
			if (blackCheck) {
				JOptionPane.showMessageDialog(null, "Black is in check!");
			} else if (whiteCheck) {
				JOptionPane.showMessageDialog(null, "White is in check!");
			}

			boolean check = false;
			// are we in check?
//			if (blackCheck || whiteCheck) {
//				check = true;
//			}
//			
//			if (player) {
//				if (blackCheck) {
//					// error
//					JOptionPane.showMessageDialog(null, "Cannot place yourself in check!");
//					continue;
//				}
//			} else {
//				if (whiteCheck) {
//					// error
//					JOptionPane.showMessageDialog(null, "Cannot place yourself in check!");
//					continue;
//				}
//			}
			

			// is opponent in check if we claimed they would be?

			if (player && blackCheck && blackInCheckBefore == false) {
				JOptionPane.showMessageDialog(null, "Cannot place yourself in check!");
				continue;
			} else if (!player && whiteCheck && whiteInCheckBefore == false) {
				JOptionPane.showMessageDialog(null, "Cannot place yourself in check!");
				continue;
			} else if (blackInCheckBefore == true && blackCheck == true) {
				JOptionPane.showMessageDialog(null, "You must remove yourself from check!");
				continue;
			} else if (whiteInCheckBefore == true && whiteCheck == true) {
				JOptionPane.showMessageDialog(null, "You must remove yourself from check!");
				continue;
			} 
//			else if ((blackCheck && blackInCheckBefore == false) || (whiteCheck && whiteInCheckBefore == false)) {
//				error = true;
//			}

			if (player) {
				moveCounter++;
			}
			if (checkMate) {
//				System.out.println("Game over!");
			}

			board.setPiece(moved, dstRow, dstCol);
			board.setPiece(fill, srcRow, srcCol);
			
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

			String statusCastOpps = "";

			for (int i = 0; i < 4; i++) {
				statusCastOpps = statusCastOpps + newCastlingOpps[i];
			}
			
			castlingOpps = statusCastOpps;
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
		}
	}
	
	private static void printGUI() {
		boolean colour = false;
		for (int i = 1; i <= 10; i++) {
			colour = !colour;
			for (int j = 1; j <= 10; j++) {
				if (colour) {
					StdDraw.setPenColor(StdDraw.DARK_GRAY);
				} else {
					StdDraw.setPenColor(StdDraw.WHITE);
				}

				if (board.isFree(10 - j, i - 1)) {
					StdDraw.filledSquare(i, j, 0.5);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.square(i, j, 0.5);
				} else {
					StdDraw.filledSquare(i, j, 0.5);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.square(i, j, 0.5);
					StdDraw.picture(i, j, board.getIcon(10 - j, i - 1), 1, 1);
				}
				colour = !colour;
			}
		}
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(14, 10, 2, 0.5);
		StdDraw.setPenColor(StdDraw.BLACK);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 40);
		StdDraw.setFont(font);
		String curPlayer = "";
		if (player) {
			curPlayer = "Black";
		} else {
			curPlayer = "White";
		}
		
		StdDraw.text(14, 10, curPlayer+"'s turn");
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(14, 8, 2.25, 0.75);
		StdDraw.filledRectangle(14, 5, 2.25, 1.25);
		StdDraw.filledRectangle(14, 2, 2.25, 0.75);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		Font font2 = new Font(Font.MONOSPACED, Font.BOLD, 18);
		StdDraw.setFont(font2);
		
		StdDraw.text(14, 11, "Click here to eneter text for a move");
		StdDraw.text(14, 9, "Half move clock:");
		StdDraw.text(14, 6.5, "Castling opportunities:");
		StdDraw.text(14, 3, "Move counter:");
		
		StdDraw.setFont(font);
		StdDraw.setPenColor();
		StdDraw.text(14, 8, halfmoveClock+"");
		StdDraw.text(14, 5, castlingOpps);
		StdDraw.text(14, 2, moveCounter+"");
		
		StdDraw.rectangle(14, 11, 2.85, 0.5);
		StdDraw.rectangle(14, 8, 2.25, 0.75);
		StdDraw.rectangle(14, 5, 2.25, 1.25);
		StdDraw.rectangle(14, 2, 2.25, 0.75);
	
		StdDraw.show();
		StdDraw.pause(300);
	}

	private static boolean checkCorrectPlayer(Piece source) {
		boolean valid = true;
		if (player != source.isBlack()) {
			valid = false;
		}
		return valid;
	}

	// sets whether black or white is in check after a move
	private static void checkPlayerInCheck(Board temp) {
		Piece whiteKing = null;
		Piece blackKing = null;
		Piece king = null;
		blackCheck = false;
		whiteCheck = false;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (temp.getPiece(i, j).getPieceChar() == 'k') {
					blackKing = temp.getPiece(i, j);
				}
				if (temp.getPiece(i, j).getPieceChar() == 'K') {
					whiteKing = temp.getPiece(i, j);
				}
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boolean checkPossible;
				boolean posPlayer = temp.getPiece(i, j).isBlack();

				if (posPlayer) {
					king = whiteKing;
				} else {
					king = blackKing;
				}

				if (temp.getPiece(i, j) instanceof Pawn || temp.getPiece(i, j) instanceof DrunkenSoldier) {
					checkPossible = temp.getPiece(i, j).validCapture(i, j, king.getRow(), king.getCol(), temp);
				} else {
					checkPossible = temp.getPiece(i, j).validMove(i, j, king.getRow(), king.getCol(), temp);
				}
				if (!(temp.getPiece(i, j).isFree() || temp.getPiece(i, j) instanceof King)
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
	
	private static void checkPlayerInCheckMoveFile() {
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

	private static boolean checkHalfMoveClock(Piece source) {
		boolean valid = true;
		if (halfmoveClock == 50) {
			if (!(source instanceof Pawn || source instanceof DrunkenSoldier)) {
				JOptionPane.showMessageDialog(null, "Half move clock has reached 50!");
				valid = false;
			}
		}
		return valid;
	}

	public static String getPieceFromClick(String srcdst) {
		String piece = "";
		int x = (int) Math.round(StdDraw.mouseX());
		int y = (int) Math.round(StdDraw.mouseY());
		while (true) {
			if (StdDraw.isMousePressed()) {
				if ((x > 1 && x < 10) && (y < 10 && y > 1)) {
					System.out.println("x: " + x + " y: " + y);

					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.square(x, y, 0.5);

					char file = files[x - 1];
					System.out.println(file);
					piece = file + "" + (y) + "";
					StdDraw.show();
					StdDraw.pause(300);
					break;
				} else if ((x > 11 && x < 17) && (y > 10 && y < 12)) {
					StdDraw.setPenColor(StdDraw.GREEN);
					piece = JOptionPane.showInputDialog("Enter the  " + srcdst + " coordinates:");
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.square(piece.charAt(0) - 46 + 1, Integer.parseInt(piece.substring(1)), 0.5);
					break;
				} else {
					piece = "invalid";
					break;
				}
			}
		}
		return piece;
	}

}

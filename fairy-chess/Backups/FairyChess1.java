package Backups;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import BoardValidationErrors;

public class FairyChess1 {
	private static int pieceNums[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final char pieceChars[] = { 'k', 'r', 'q', 'n', 'b', 'd', 'f', 'e', 'p', 'a', 'w' };
	private static final String pieceIndexes = new String(pieceChars);
	private static final char officerChars[] = { 'k', 'r', 'q', 'n', 'b', 'f', 'e', 'a', 'w' };
	private static final String officerIndexesBlack = "krqnbfeaw";
	private static final String officerIndexesWhite = officerIndexesBlack.toUpperCase();
	private static char boardPlacement[][] = new char[10][10];
	private static String nextPlayer;
	private static String castlingOpps = "";
	private static String halfmoveClock = "";
	private static String moveCounter = "";
	private static int countLines = 0;
	private static int pawnAndDrunkenLine;
	private static int firstSectionBreakLine;
	private static int statusBarLine = 0;
	private static int countLinesOfficers;
	private static int countPawnsAndDrunkenSoldiers = 0;
	private static int countOfficers = 0;
	private static char files[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
	private static int ranks[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	private static int countPawnsWhiteOnBoard = 0, countDSoldiersWhiteOnBoard = 0;
	private static int countPawnsBlackOnBoard = 0, countDSoldiersBlackOnBoard = 0;
	
	//static BoardValidationChecks check = new BoardValidationChecks();

	public static void main(String[] args) {
		String pieces = "";
		String board = "";
		String status = "";
		// Get the board file's name, and initialize a File object to represent it
		if (args.length < 1) {
			throw new IllegalArgumentException("Provide a file name as first argument");
		}

		String boardFilename = args[0];
		File boardFile = new File(boardFilename);

		// Initialize the Scanner
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile).useDelimiter("-----");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		// initialize and validate board
		pieces = boardScanner.next();
		initializePieces(pieces);
		board = boardScanner.next();
	    initializeBoard(board);
		status = boardScanner.next();
		initializeStatusline(status);
		
		boardScanner.close();
	}

	private static void initializePieces(String pieces) {
		Scanner scPieces = new Scanner(pieces);
		while (scPieces.hasNextLine()) {
			countLines++;
			String line = scPieces.nextLine();
			Scanner scLine = new Scanner(line).useDelimiter(":");
			char pieceChar = scLine.next().charAt(0);
			if (pieceChar != '%' && pieceChar != '-') {
				String number = scLine.next();
				if (pieceIndexes.indexOf(pieceChar) > -1) {
					int numPieces = 0;
					try {
						numPieces = Integer.parseInt(number);
					} catch (Exception numberFormatExcpetionException) {
						BoardValidationErrors.illegalPieceAllocation(countLines);
					}
					pieceNums[pieceIndexes.indexOf(pieceChar)] = numPieces;

					if (pieceChar == 'p' || pieceChar == 'd') {
						checkSumPawnAndDrunkenSoldiers(numPieces);
						pawnAndDrunkenLine = countLines;
					} else {
						checkSumOfficers(numPieces);
						countLinesOfficers = countLines;
					}
				} else {
					BoardValidationErrors.illegalPieceAllocation(countLines);
				}
			} else if (pieceChar == '-') {
				BoardValidationErrors.illegalPieceAllocation(countLines);
			}
		}
		scPieces.close();
		countLines++;
		firstSectionBreakLine = countLines;

		performPieceAllocationValidations();

//		for (int i = 0; i < 11; i++) {
//			System.out.print(pieceChars[i] + "\t");
//		}
//		System.out.println();
//		for (int i = 0; i < 11; i++) {
//			System.out.print(pieceNums[i] + "\t");
//		}
//		System.out.println();
//		System.out.println();
	}

	private static void initializeBoard(String board) {
//		System.out.println(board);	
		Scanner scBoard = new Scanner(board);
		int row = 0, col = 0;
		scBoard.nextLine();
		while (scBoard.hasNextLine()) {
			if (row >= 10) {
				BoardValidationErrors.illegalBoardDimension();
			}
			countLines++;
			String rowText = scBoard.nextLine();
			if (rowText.charAt(0) != '%') {
				Scanner scRow = new Scanner(rowText);
				while (scRow.hasNext()) {
					if (col>=10) {
						BoardValidationErrors.illegalBoardDimension();
					}
					boardPlacement[row][col] = scRow.next().charAt(0);
					col++;
				}
				if (col != 10) {
					BoardValidationErrors.illegalBoardDimension();
				}
				col = 0;
				scRow.close();
				row++;
			} 
		}
		if (row != 10) {
			BoardValidationErrors.illegalBoardDimension();
		}
		countLines++;
		scBoard.close();
		
		performBoardValidationChecks();

//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				System.out.print(boardPlacement[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();

	}

	private static void initializeStatusline(String status) {
		countLines++;
		statusBarLine = countLines;
		Scanner scStatus = new Scanner(status).useDelimiter(":");
		//scStatus.nextLine();
		String line = scStatus.nextLine();
		//NBNBNBNBNBNNBNBNBNNBNBNBNBNNBNBNBNNNNNNNNNNNNNNNNBNBNBNBNNBNBNBNNBNBNB - come up with more general solution
		int i =0;
		while (scStatus.hasNextLine() && i<3) {
			line = scStatus.nextLine();
			if (line.charAt(0)=='w' || line.charAt(0)=='b') {
				break;
			}
		}
		Scanner scLine = new Scanner(line).useDelimiter(":");
		nextPlayer = scLine.next();
		castlingOpps = scLine.next();
		halfmoveClock = scLine.next();
		moveCounter = scLine.next();
		scStatus.close();
		performStatusLineChecks();
//		System.out.print(nextPlayer + ":");
//		System.out.print(castlingOpps + ":");
//		System.out.print(halfmoveClock + ":");
//		System.out.println(moveCounter);
	}

	// piece allocation checks below
	private static void performPieceAllocationValidations() {
		checkZeroFewPawns();
		checkRooksKingsAndZeroOfficers();
	}

	private static void checkSumPawnAndDrunkenSoldiers(int numPieces) {
		countPawnsAndDrunkenSoldiers += numPieces;
		if (countPawnsAndDrunkenSoldiers > 10) {
			BoardValidationErrors.illegalPieceAllocation(countLines);
		}
	}

	private static void checkZeroFewPawns() {
		if (countPawnsAndDrunkenSoldiers == 0) {
			BoardValidationErrors.illegalPieceAllocation(firstSectionBreakLine);
		} else if (countPawnsAndDrunkenSoldiers != 10) {
			BoardValidationErrors.illegalPieceAllocation(pawnAndDrunkenLine);
		}
	}

	private static void checkSumOfficers(int numPieces) {
		countOfficers += numPieces;
		if (countOfficers > 10) {
			BoardValidationErrors.illegalPieceAllocation(countLines);
		}
	}

	private static void checkRooksKingsAndZeroOfficers() {
		boolean rookKingZeroError = false;
		int errorLine = 0;
		if (pieceNums[pieceIndexes.indexOf('k')] != 1 || pieceNums[pieceIndexes.indexOf('r')] < 2
				|| countOfficers == 0) {
			rookKingZeroError = true;
			errorLine = firstSectionBreakLine;
			BoardValidationErrors.illegalPieceAllocation(errorLine);
		} else if (countOfficers != 10) {
			BoardValidationErrors.illegalPieceAllocation(countLinesOfficers);
		}
	}

	// board validation checks below
	private static void performBoardValidationChecks() {
		checkIllegalPieces();
		checkExceedingPawnAllocation();
		checkExceedingOfficerAllocation();
		checkIllegealPawnPosition();
		checkIllegalElephantPosition();
	}

	private static void checkIllegalPieces() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (boardPlacement[i][j] != '.') {
					int indexOfChar = pieceIndexes.indexOf((boardPlacement[i][j] + "").toLowerCase().charAt(0));
					if (indexOfChar >= 0) {
						if (pieceNums[indexOfChar] == 0) {
							BoardValidationErrors.illegalPiece(files[j], ranks[i]);
						}
					} else {
						BoardValidationErrors.illegalPiece(files[j], ranks[i]);
					}

				}
			}
		}
	}

	private static void checkExceedingPawnAllocation() {
		int numPawns = pieceNums[pieceIndexes.indexOf('p')];
		int numDrunkenSoldiers = pieceNums[pieceIndexes.indexOf('d')];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (boardPlacement[i][j] == 'p') {
					countPawnsBlackOnBoard++;
				} else if (boardPlacement[i][j] == 'd') {
					countDSoldiersBlackOnBoard++;
				} else if (boardPlacement[i][j] == 'P') {
					countPawnsWhiteOnBoard++;
				} else if (boardPlacement[i][j] == 'D') {
					countDSoldiersWhiteOnBoard++;
				}

				if (countPawnsWhiteOnBoard > numPawns || countDSoldiersWhiteOnBoard > numDrunkenSoldiers
						|| countPawnsBlackOnBoard > numPawns || countDSoldiersBlackOnBoard > numDrunkenSoldiers) {
					BoardValidationErrors.pawnAllocationExceeded(files[j], ranks[i]);
				}
			}
		}
	}

	private static void checkExceedingOfficerAllocation() {
		int numPawnsWhiteOnBoard = countPawnsWhiteOnBoard + countDSoldiersWhiteOnBoard;
		int numPawnsBlackOnBoard = countPawnsBlackOnBoard + countDSoldiersBlackOnBoard;
		int maxExtraWhite = 10 - numPawnsWhiteOnBoard;
		int maxExtraBlack = 10 - numPawnsBlackOnBoard;
		int maxOfficerNumsWhite[] = new int[9];
		int maxOfficerNumsBlack[] = new int[9];
		int countOfficersWhiteOnBoard[] = new int[9];
		int countOfficersBlackOnBoard[] = new int[9];

		//calculates the maximum number of officers allowed based on pawns which are missing/promoted
		for (int i = 0; i < 9; i++) {
			maxOfficerNumsWhite[i] = pieceNums[pieceIndexes.indexOf(officerChars[i])] + maxExtraWhite;
			maxOfficerNumsBlack[i] = pieceNums[pieceIndexes.indexOf(officerChars[i])] + maxExtraBlack;
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// checks the character being checked on the board is an officer
				if (officerIndexesBlack.indexOf((boardPlacement[i][j] + "").toLowerCase().charAt(0)) > -1) {
					boolean white = Character.isUpperCase(boardPlacement[i][j]);
					// checks if it is a white officer
					if (white) {
						int indexWhite = officerIndexesWhite.indexOf(boardPlacement[i][j]);
						//increments the number of the officer found on the board
						countOfficersWhiteOnBoard[indexWhite]++;
						//checks if the number of that officer is more than the number allocated (i.e. a pawn was promoted)
						if (countOfficersWhiteOnBoard[indexWhite] > pieceNums[pieceIndexes
								.indexOf((boardPlacement[i][j] + "").toLowerCase().charAt(0))]) {
							//decreases the maxNumber of officers allowed due to one of the pawns being used by another officer
							for (int k = 0; k < 9; k++) {
								if (indexWhite != k && maxOfficerNumsWhite[k] != pieceNums[pieceIndexes
										.indexOf((boardPlacement[i][j] + "").toLowerCase().charAt(0))]) {
									maxOfficerNumsWhite[k]--;
								}
							}
						}
						if (countOfficersWhiteOnBoard[indexWhite] > maxOfficerNumsWhite[indexWhite]) {
							BoardValidationErrors.officerAllocationExceeded(files[j], ranks[i]);
						}
					}
					// checks if it is a black officer
					else {
						int indexBlack = officerIndexesBlack.indexOf(boardPlacement[i][j]);
						countOfficersBlackOnBoard[indexBlack]++;
						if (countOfficersBlackOnBoard[indexBlack] > pieceNums[pieceIndexes
								.indexOf(boardPlacement[i][j])]) {
							for (int k = 0; k < 9; k++) {
								if (indexBlack != k && maxOfficerNumsWhite[k] != pieceNums[pieceIndexes
										.indexOf(boardPlacement[i][j])]) {
									maxOfficerNumsBlack[k]--;
								}
							}
						}
						if (countOfficersBlackOnBoard[indexBlack] > maxOfficerNumsBlack[indexBlack]) {
							BoardValidationErrors.officerAllocationExceeded(files[j], ranks[i]);
						}
					}
				}
			}
		}
	}

	private static void checkIllegealPawnPosition() {
		for (int i = 0; i < 10; i++) {
			if ((boardPlacement[0][i] + "").toLowerCase().charAt(0) == 'p' || (boardPlacement[0][i] + "").toLowerCase().charAt(0) == 'd') {
				BoardValidationErrors.illegalPawnPosition(files[i], ranks[0]);
			} else if ((boardPlacement[9][i] + "").toLowerCase().charAt(0) == 'p'
					|| (boardPlacement[9][i] + "").toLowerCase().charAt(0) == 'd') {
				BoardValidationErrors.illegalPawnPosition(files[i], ranks[9]);
			}
		}
	}

	private static void checkIllegalElephantPosition() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				if (boardPlacement[i][j] == 'E') {
					BoardValidationErrors.illegalElephantPosition(files[j], ranks[i]);
				}
			}
		}
		for (int i = 5; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (boardPlacement[i][j] == 'e') {
					BoardValidationErrors.illegalElephantPosition(files[j], ranks[i]);
				}
			}
		}
	}
	
	private static void performStatusLineChecks() {
		checkIllegalNextPlayer();
		checkIllegalCastlingOpportunities();
		checkIllegalHalfmoveClock();
		checkIllegalMoveCounter();
	}
	
	private static void checkIllegalNextPlayer() {
		if ((nextPlayer+"").length() > 1) {
			BoardValidationErrors.illegalNextPlayerMarker(statusBarLine);
		}
		char next = (nextPlayer+"").toUpperCase().charAt(0);
		if (next != 'W' && next != 'B') {
			BoardValidationErrors.illegalNextPlayerMarker(statusBarLine);
		}
	}
	
//	The second set of characters
//	represent the available castling opportunities, in the order Black Queenside (left), Black Kingside(right), White Queenside, White Kingside, with a + indicating
//	that the maneuver is still available, while a - indicates that it is no longer available.
	private static void checkIllegalCastlingOpportunities() {
		String castlingOppsToBoards = "";
		if (boardPlacement[0][5]=='k' && boardPlacement[0][0]=='r') {
			castlingOppsToBoards=castlingOppsToBoards+"+";
		}
		else {
			castlingOppsToBoards=castlingOppsToBoards+"-";
		}
		
		if (boardPlacement[0][5]=='k' && boardPlacement[0][9]=='r') {
			castlingOppsToBoards=castlingOppsToBoards+"+";
		}
		else {
			castlingOppsToBoards=castlingOppsToBoards+"-";
		}
		
		if (boardPlacement[9][5]=='K' && boardPlacement[9][0]=='R') {
			castlingOppsToBoards=castlingOppsToBoards+"+";
		}
		else {
			castlingOppsToBoards=castlingOppsToBoards+"-";
		}
		
		if (boardPlacement[9][5]=='K' && boardPlacement[9][9]=='R') {
			castlingOppsToBoards=castlingOppsToBoards+"+";
		}
		else {
			castlingOppsToBoards=castlingOppsToBoards+"-";
		}
		
		for (int i=0; i<4; i++) {
			if (castlingOpps.charAt(i)!=castlingOppsToBoards.charAt(i)) {
				BoardValidationErrors.illegalCastlingOpportunity(statusBarLine, i);
			}
		}
	}
	
	private static void checkIllegalHalfmoveClock() {
		int halfMove = -1;
		try {
			halfMove = Integer.parseInt(halfmoveClock);
		} catch (Exception NumberFormatException) {
			BoardValidationErrors.illegalHalfmoveClock(statusBarLine);
		}
		
		if (halfMove < 0 || halfMove > 50) {
			BoardValidationErrors.illegalHalfmoveClock(statusBarLine);
		}
	}
	
	private static void checkIllegalMoveCounter() {
		int moves = -1;
		try {
			moves = Integer.parseInt(moveCounter.charAt(0) + "");
		} catch (Exception NumberFormatException) {
			BoardValidationErrors.illegalMoveCounter(statusBarLine);
		}
		
		if (moves < 0) {
			BoardValidationErrors.illegalMoveCounter(statusBarLine);
		}
	}
	

}

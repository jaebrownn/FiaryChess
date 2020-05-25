import java.util.Scanner;
import java.lang.String;

public class BoardInitialization {
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
	private static int firstSectionBreakLine;
	private static int statusBarLine = 0;
	private static int countPawnsAndDrunkenSoldiers = 0;
	private static int countOfficers = 0;
	private static char files[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
	private static int ranks[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	private static int countPawnsWhiteOnBoard = 0, countDSoldiersWhiteOnBoard = 0;
	private static int countPawnsBlackOnBoard = 0, countDSoldiersBlackOnBoard = 0;
	private static String piecesStr = "";

	public static void initializePieces(String pieces) {
		Scanner scPieces = new Scanner(pieces);
		while (scPieces.hasNextLine()) {
			countLines++;
			String line = scPieces.nextLine();
			Scanner scLine = new Scanner(line).useDelimiter(":");
			char pieceChar = scLine.next().charAt(0);
			if (pieceChar == '%') {
				continue;
			} else {
				piecesStr=piecesStr+line+"\n";
				int numPieces = scLine.nextInt();
				pieceNums[pieceIndexes.indexOf(pieceChar)] = numPieces;
			}
			scLine.close();
		}
		scPieces.close();
		countLines++;
		firstSectionBreakLine = countLines;
	}

	public static void initializeBoard(String board) {
		Scanner scBoard = new Scanner(board);
		int row = 0, col = 0;
		while (scBoard.hasNextLine()) {
				countLines++;
				String rowText = scBoard.nextLine();
				if (!(rowText.isEmpty())) {
					Scanner scRow = new Scanner(rowText);
					if (rowText.charAt(0) == '%') {
						continue;
					}
					while (scRow.hasNext()) {
						boardPlacement[row][col] = scRow.next().charAt(0);
						col++;
					}
					col = 0;
					scRow.close();
					row++;
				}
		}
		countLines++;
		scBoard.close();
	}

	public static void initializeStatusline(String status) {
		countLines++;
		Scanner scStatus = new Scanner(status).useDelimiter(":");
		while (scStatus.hasNextLine()) {
			String line = scStatus.nextLine();
			if (line.isBlank()) {
				continue;
			} else if (line.charAt(0) == '%') {
				continue;
			}
			Scanner scLine = new Scanner(line).useDelimiter(":");
			nextPlayer = scLine.next();
			castlingOpps = scLine.next();
			halfmoveClock = scLine.next();
			moveCounter = scLine.next();
		}
		scStatus.close();
	}

	public static int[] getPieceNums() {
		return pieceNums;
	}
	
	public static String getPiecesStr() {
		return piecesStr;
	}
	
	public static char[][] getBoardPlacement() {
		return boardPlacement;
	}

	public static String getNextPlayer() {
		return nextPlayer;
	}

	public static String getCastlingOpps() {
		return castlingOpps;
	}

	public static String getHalfmoveClock() {
		return halfmoveClock;
	}

	public static String getMoveCounter() {
		return moveCounter;
	}
}

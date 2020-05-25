import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FairyChess {
	

	public static void main(String[] args) {
		String pieces = "";
		String sBoard = "";
		String status = "";
		// Get the board file's name, and initialize a File object to represent it
		if (args.length < 1) {
			throw new IllegalArgumentException("Provide a Board file name as first argument and Move file name as second argument");
		}

		String boardFilename = args[0];
		File boardFile = new File(boardFilename);
//		String moveFilename = args[1];
//		File moveFile = new File(moveFilename);

		// Initialize the Scanner
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile).useDelimiter("-----");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		// initialize and validate board (validation ignored for the purpose of the second hand in)
		
		pieces = boardScanner.next();
		BoardInitialization.initializePieces(pieces);
		sBoard = boardScanner.next();
		BoardInitialization.initializeBoard(sBoard);
		status = boardScanner.next();
		BoardInitialization.initializeStatusline(status);
		
		
		boardScanner.close();
		
		MoveValidationChecksGUI.initializeBoard();
	}
}
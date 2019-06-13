import java.util.Random;
import java.util.Scanner;

public class MS {
	private CellField field;
	private int rows;
	private int columns;
	private int mines;
	private String minesString = "";
	boolean finish = false;

	public MS(int rows, int columns, int mines) {
		if (rows <= 0 || columns <= 0) {
			rows = columns = 5;
		}

		this.rows = rows;
		this.columns = columns;

		// if the number of mines is more than the available spaces
		// it is set to half the number of spaces minus 1
		if (mines > (rows * columns)) {
			mines = (((rows * columns) / 2) - 1);
			// temp solution
			minesString = "The number of mines you entered is too high, it has been changed to " + mines + ". ";
		}

		this.mines = mines;
		field = new CellField(rows, columns);
		fillField();
	}

	public void fillField() {
		fillMines(); // 9 is a bomb, 0 is a empty cell
		fillEmptyCells();
	}

	/**
	 * Fill the field array with mines
	 */
	private void fillMines() {
		int fillNum = mines;
		while (fillNum > 0) {
//			generate 2 random numbers for the row and column the bomb will be placed in
			int randRow = getRandom(rows);
			int randCol = getRandom(columns);
			if (field.getCell(randRow, randCol) == null || !field.getCell(randRow, randCol).isBomb()) {
				field.setCell(randRow, randCol, new Cell(9));

//				this for loop surrounds the bomb with cells which value represents 
//				the number of bombs that are adjacent to that cell
				for (int i = randRow - 1; i <= randRow + 1; i++) {
					for (int j = randCol - 1; j <= randCol + 1; j++) {
						try {
							if (field.getCell(i, j) == null) {
								field.setCell(i, j, new Cell(1));
							} else if (field.getCell(i, j).getValue() < 8) {
								field.getCell(i, j).increment();
							}
						} catch (ArrayIndexOutOfBoundsException exception) {
						}
					}
				}
				fillNum--;
			}
		}
	}

	/**
	 * Return a random number in a certain range
	 */
	private int getRandom(int range) {
		Random rand = new Random();
		return rand.nextInt(range);
	}

	/**
	 * Fill in all the cells that are left to be filled with a cell of value 0
	 */
	private void fillEmptyCells() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (field.getCell(i, j) == null) {
					field.setCell(i, j, new Cell(0));
				}
			}
		}
	}

	public String toString(boolean showAll) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("  ");
		for (int a = 0; a < columns; a++) {
			sb.append("- " + (a + 1) + " -");
		}
		for (int i = 0; i < rows; i++) {
			sb.append("\n");
			sb.append(i + 1 + " ");
			for (int j = 0; j < columns; j++) {
				sb.append(field.getCell(i, j).toString(showAll));
			}
		}
		return sb.toString();
	}

	public void play() {
		int row = -1;
		int column = -1;
		boolean flagged = false;
		boolean userInput = false;

		while (!finish) {
			
			// display board and message
			System.out.println(this.toString(false));
			System.out.println("\n" + minesString
					+ "Dig a square in the format x,y,'d' For example, the top left corner will be 1,1,'d' and the bottom right will be "
					+ rows + "," + columns + ",d" + "\n"
					+ "Flag a square in the format x,y,'f'. For example, to flag the top left corner, you would input 1,1,f");
			
			// get user input
			while (!userInput) {
				// reset variables
				String cell = "";
				String[] cellData = null; 
				row = column = -1; 
				flagged = false;
				
				Scanner scanner = new Scanner(System.in); 
				cell = scanner.nextLine().trim();
				cellData = cell.split(",");
				
			}
			
			userInput = false;
			
			
			// dig/flag cell and check if player has won or lost
			if (flagged) {
				field.getCell(row, column).setFlagged(true);
				checkWin();
			} else if (field.getCell(row, column).isBomb()) {
				System.out.println("BOOM, You lose");
				finish = true;
				System.out.println(this.toString(true));
			} else if (field.getCell(row, column).isZero()) {
				field.getCell(row, column).setVisible();
				field.isZeroAdjacent(row, column);
				checkWin();
			} else {
				field.getCell(row, column).setVisible();
				checkWin();
			}
			
		}

		
	}

	private void checkWin() {
		int m = mines;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (field.getCell(i, j).isBomb() && field.getCell(i, j).isFlagged())
					m--;
				if (m == 0) {
					finish = true;
					System.out.println("You Win!");
					System.out.println(this.toString(true));
					System.exit(0);
				}
			}
		}
	}

	private boolean isValidNum(String numString, int max) {
		try {
			int number = Integer.parseInt(numString);
			return (number >= 0 || number <= max);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		MS game = new MS(5, 5, 5);
		game.play();
	}

	/*
	 * if (isInt(cellData[0].trim()) && isInt(cellData[1].trim())) { row =
	 * (Integer.parseInt(cellData[1].trim()) - 1); column =
	 * (Integer.parseInt(cellData[0].trim()) - 1);
	 * 
	 * if (row < 0 || column < 0) { row = -1; column = -1; cellData = null;
	 * System.out.
	 * println("Error, you cannot input negative integers, please try again"); }
	 * 
	 * } else { row = -1; column = -1; cellData = null;
	 * System.out.println("Error, please try again"); }
	 * 
	 * if (cellData.length == 3) { if (cellData[2].trim().toLowerCase().equals("f"))
	 * { flagged = true; } else {
	 * System.out.println("Error, wrong character, please try again"); row = -1;
	 * column = -1; cellData = null; } }
	 */

	/*
	 * if (!false) {
	 * 
	 * System.out.println(this.toString(false)); //
	 * System.out.println(this.toString(true)); 
	 * System.out.println("\n" +
	 * minesString +
	 * "Dig a square in the format x,y,'d' For example, the top left corner will be 1,1,'d' and the bottom right will be "
	 * + rows + "," + columns + ",d" + "\n" +
	 * "Flag a square in the format x,y,'f'. For example, to flag the top left corner, you would input 1,1,f"
	 * );
	 * 
	 * // input validation while (row == -1 && column == -1) { String cell = "";
	 * String[] cellData = null; row = column = -1; flagged = false;
	 * 
	 * Scanner scanner = new Scanner(System.in); cell = scanner.nextLine().trim();
	 * cellData = cell.split(",");
	 * 
	 * if ()
	 * 
	 * } }
	 */

}

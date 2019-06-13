

public class CellField {
	private int rows;
	private int columns;
	private Cell[][] field;

	public CellField(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		field = new Cell[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public Cell[][] getField() {
		return field;
	}

	public Cell getCell(int row, int column) {
		return field[row][column];
	}

	public void setCell(int row, int column, Cell cell) {
		field[row][column] = cell;
	}

	public void setField(Cell[][] field) {
		this.field = field;
	}

	/**
	 * A recursive algorithm that checks if adjacent cells are equal to 0 and if so
	 * then it will make that cell visible
	 * 
	 * @param column
	 * @param row
	 */
	public void isZeroAdjacent(int row, int column) {
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				try {
					// remove the case where you look at the parameters already given as we know
					// that cell is a 0 and visible
					if (!(i == row && j == column)) {
						if (this.getCell(i, j).isZero() && !this.getCell(i, j).isVisible()) {
							this.getCell(i, j).setVisible();
							this.isZeroAdjacent(i, j);
						} else if (this.getCell(i, j).getValue() <= 8 && !this.getCell(i, j).isVisible()) {
							this.getCell(i, j).setVisible();
						}
					}
				} catch (ArrayIndexOutOfBoundsException exception) {
				}
			}
		}
	}
}

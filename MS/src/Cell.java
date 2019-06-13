
public class Cell {
	private int value;
	private boolean visible;
	private boolean flagged;
	
	public Cell(int value) {
		this.value = value;		
		visible = false;
		flagged = false;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible() {
		visible = true;
	}
	
	public void increment() {
		this.value = this.value + 1;
	}
	
	public String toString(boolean showAll) {
		if (this.visible || showAll) {
			return "- " + getValue() + " -";
		} else if (isFlagged()) {
			return "- " + "F" + " -";
		} else {
			return "- " + "X" + " -";
		}
	}
	
	public boolean isBomb() {
		return this.getValue() == 9;
	}
	
	public boolean isZero() {
		return this.getValue() == 0;
	}
}

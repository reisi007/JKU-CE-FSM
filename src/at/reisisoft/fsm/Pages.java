package at.reisisoft.fsm;

public enum Pages {
	DEFAULT("Start"), QUERY("Result"), CONFIRM("Book"), BOOK(
			"Booking confirmation");
	private String string;

	private Pages(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

}

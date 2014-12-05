package at.reisisoft.fsm;

public enum Pages {
	DEFAULT("Start"), QUERY("Result"), CONFIRM_BOOKING("Book"), BOOK(
			"Booking confirmation"), CONFIRM_STORNO("Storno"), STORNO(
			"Storno confirmation");
	private String string;

	private Pages(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

}

package at.reisisoft.fsm;

/**
 * All available pages. USe {@link #toString()} is used to get the URL of the
 * page
 * 
 * @author Florian
 *
 */
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

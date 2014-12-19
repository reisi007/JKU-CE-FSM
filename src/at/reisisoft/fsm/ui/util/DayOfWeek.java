package at.reisisoft.fsm.ui.util;

public enum DayOfWeek {
	Mo(1, "Monday"), Tue(2, "Tuesday"), We(3, "Wednesday"), Thr(4, "Thursday"), Fr(
			5, "Friday"), Sa(6, "Saturday"), Su(7, "Sunday");

	private final int day;
	private final String name;
	private static final DayOfWeek[] values = values();

	private DayOfWeek(int day, String name) {
		this.day = day;
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

	public static DayOfWeek fromInt(int day) {
		for (DayOfWeek w : values) {
			if (w.getDay() == day) {
				return w;
			}
		}
		return null;
	}

	public int getDay() {
		return day;
	}

	public String getName() {
		return name;
	}

}

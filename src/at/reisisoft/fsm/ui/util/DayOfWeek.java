package at.reisisoft.fsm.ui.util;

import java.util.Calendar;

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

	/**
	 * 
	 * @param day
	 *            Day in range from 1 to 7
	 * @return
	 */
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

	public static DayOfWeek fromCalendar(Calendar c) {
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return fromInt(1);
		case Calendar.TUESDAY:
			return fromInt(2);
		case Calendar.WEDNESDAY:
			return fromInt(3);
		case Calendar.THURSDAY:
			return fromInt(4);
		case Calendar.FRIDAY:
			return fromInt(5);
		case Calendar.SATURDAY:
			return fromInt(6);
		default:
			return fromInt(7);
		}
	}

}

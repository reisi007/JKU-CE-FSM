package at.reisisoft.fsm.ui.util;

import java.text.SimpleDateFormat;
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
		try {
			SimpleDateFormat format = new SimpleDateFormat("u");
			String result = format.format(c.getTime());
			return fromInt(Integer.parseInt(result));
		} catch (Exception e) {
			return null;
		}
	}
}
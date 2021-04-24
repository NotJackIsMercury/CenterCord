package centerview.centercord;

import java.lang.reflect.Array;

public class Utility {
	@SuppressWarnings("unchecked")
	public static <T> T[] arrayOfNone(Class<T> type) {
		return (T[]) Array.newInstance(type, 0);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] arrayOfOne(T t) {
		final Object array = Array.newInstance(t.getClass(), 1);
		Array.set(array, 0, t);
		return (T[]) array;
	}

	public static String upFirst(String s) {
		switch (Integer.compare(s.length(), 1)) {
		case +1:
			final char c = s.charAt(0);
			return Character.toUpperCase(c) + s.substring(1);
		case 0:
			return String.valueOf(Character.toUpperCase(s.charAt(0)));
		default:
			return s;
		}
	}

	@SafeVarargs
	public static <T> T[] varArgs(T... ts) {
		return ts;
	}
}

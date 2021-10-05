package olbertz;

/*
 * OLBERTZ Mit dieser Klasse moechte ich mir einfach einige gaengige Fehler ersparen. Wie leicht
 * vertauscht man || und && oder vergisst ein !, wenn man eine Negierung moechte? So schreibe ich
 * die Methoden einmal, jage sie durch ein paar Unit-Tests, weiss dann, dass sie auf jeden Fall 
 * funktionieren und keine Fehler enthalten und benutze die im gesamten Programm - und in allen
 * anderen Programmen auch. So baut man sich einen Werkzeugkasten auf, der einem in allen Programmen
 * weiterhilft. 
 */
public final class StringUtils {
	private StringUtils() {
		
	}
	
	public static boolean isEmptyString(final String toCheck) {
        if (toCheck == null || toCheck.trim().length() == 0)
        {
            return true;
        } else {
        	return false;
        }
	}
	
	public static boolean isNotEmptyString(final String toCheck) {
		return !isEmptyString(toCheck);
	}
	
	public static boolean areStringsEqual(final String string1, final String string2) {
		if (string1.equals(string2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean areStringsUnequal(final String string1, final String string2) {
		if (!string1.equals(string2)) {
			return true;
		} else {
			return false;
		}
	}
}

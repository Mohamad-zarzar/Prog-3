package olbertz;

/*
 * OLBERTZ So koennte ein einfacher Validator aussehen. Ich habe das in der Vorlesung schon beschrieben, dass ich
 * meistens zwei Methoden anbiete: eine, die einen boolean zurueckgibt, und eine, die eine Exception wirft. Ich
 * moechte vielleicht nicht immer eine Exception haben und diese beiden Varianten habe ich in einer Zeit von 0,nix
 * zur Verfuegung gestellt. Aber ich habe die Flexibilitaet, die ich vielleicht spaeter mal brauche. Wie wir in der
 * Vorlesung gesehen haben, sind Exceptions ja u.U. ein teurer Spass, den man nicht immer braucht. 
 */
public final class NotenValidator {
	private final static double NOTE_MIN = 1.0;
	private final static double NOTE_MAX = 4.0;
	private final static double DURCHGEFALLEN = 5.0;
	private final static double NICHT_VERGEBEN = 0;
	
	private static final String UNGUELTIGE_NOTE = "Die Note soll zwischen 1.0 und 4.0 oder 5.0 sein oder geben Sie 0 an !";
	
	private NotenValidator() {
	}
	
	public boolean isNoteValid(int note) {
		if ((note < NOTE_MIN || note > NOTE_MAX) && note != DURCHGEFALLEN && note != NICHT_VERGEBEN) {
			return false;
		} else {
			return true;
		}
	}
	
	public void checkNote(int note) {
		if (isNoteValid(note)) {
			throw new IllegalArgumentException(UNGUELTIGE_NOTE);
		}
	}
}
